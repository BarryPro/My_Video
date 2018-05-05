package com.belong.service.impl;

import com.belong.config.HiddenConfig;
import com.belong.service.IWeChatListenerService;
import com.belong.util.Email;
import com.belong.util.Util;
import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.jms.Topic;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Service
public class WeChatListenerServiceImpl {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger logger = LoggerFactory.getLogger(WeChatListenerServiceImpl.class);
    /**
     * 可以写个网页接口，判断Online文件是否存在即可知道微信是否在线
     * 经过测试直接关闭控制台后ShutdownHook不会运行，所以该情况下Online文件不会自动删除
     */
    private static final File ONLINE_FILE = new File("Online");
    private static final Thread SHUTDOWN_HANDLER = new Thread(() -> {
        if (ONLINE_FILE.exists())
            //方法检验结果
            ONLINE_FILE.delete();
    });
    private static final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36";

    private long lastCheckQRCodeTime;
    private String uuid;
    private String loginUrl;
    private String domainName;
    private String pushDomainName;
    private String skey;
    private String wxsid;
    private String wxuin;
    private String pass_ticket;
    private String syncKey;
    private JSONObject syncKeyJson;
    private String content;
    private String lastID = "";
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;

    @Getter
    private IWeChatListenerService listener = new IWeChatListenerService()  {
        private byte[] jpgData;
        private int code;
        private String payMessage;

        @Override
        public void onLoadingQRCode() {
            logger.info("WeChat.WeChatListener onLoadingQRCode 正在获取登录二维码..");
        }

        @Override
        public byte[] getJpgDate() {
            return jpgData;
        }

        @Override
        public int getLoginCode() {
            return code;
        }

        @Override
        public void onReceivedQRCode(byte[] jpgData) {
            logger.info("WeChat.WeChatListener onReceivedQRCode 请重新用手机微信扫码登录");
            if (jpgData != null) {
                this.jpgData = jpgData;
            }
        }

        @Override
        public void onQRCodeScanned(byte[] jpgData) {
            logger.info("WeChat.WeChatListener onQRCodeScanned 扫码成功，请在手机微信中点击登录");
        }

        @Override
        public void onLoginResult(boolean loginSucceed) {
            if (loginSucceed) {
                logger.info("WeChat.WeChatListener onLoginResult 登录成功");
                this.code = 1;
                // 登录成功后标识用户在线
                try {
                    if (!ONLINE_FILE.createNewFile()) {
                        logger.error("WeChat.WeChatListener onLoginResult 创建Online文件失败");
                    }
                } catch (IOException e) {
                    logger.error("WeChat.WeChatListener onLoginResult 创建Online文件失败", e);
                    this.code = 0;
                }
            } else {
                logger.info("WeChat.WeChatListener onLoginResult 登录失败");
                this.code = 0;
                // 删除在线文件
                ONLINE_FILE.delete();
            }
        }

        @Override
        public void onReceivedMoney(String money, String mark, String id) {
            if (lastID.equals(id)) {
                return;
            }
            lastID = id;
            // 设置可以显示收款信息
            payMessage = "二维码收款："+money+"元,备注：" + ( mark.isEmpty()?"无": mark);
            jmsMessagingTemplate.convertAndSend(topic, payMessage);
            logger.info("my_play.pay_mq.topic product {} lastID {}", payMessage,lastID);
        }

        @Override
        public void onDropped(long onlineTime) {
            // 删除在线文件
            ONLINE_FILE.delete();
            if (onlineTime > 5000) {
                try {
                    if (Email.sendEmail(HiddenConfig.EMAIL_QQ, "微信离线通知", "服务器的微信已经离线啦，快去重新登录吧！"))
                        logger.info("WeChat.WeChatListener onDropped 微信已离线，发送通知邮件成功");
                    else
                        logger.error("WeChat.WeChatListener onDropped 微信已离线，发送通知邮件失败");
                } catch (Exception e) {
                    logger.error("WeChat.WeChatListener onDropped 微信已离线，发送通知邮件失败", e);
                }
            } else {
                logger.info("请尝试重新登录");
                login();
            }
        }
    };

    public IWeChatListenerService getListener(){
        return listener;
    }

    private CookieJar cookieJar = new CookieJar() {
        HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(@Nullable HttpUrl url, @Nullable List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(@Nullable HttpUrl url) {
            List<Cookie> cookies = new ArrayList<>();
            String host = url.host();
            // 没有对path进行匹配
            for (Map.Entry<String, List<Cookie>> entry : cookieStore.entrySet()) {
                if (host.endsWith(entry.getKey())) {
                    cookies.addAll(entry.getValue());
                }
            }
            return cookies;
        }
    };

    static {
        System.setProperty("jsse.enableSNIExtension", "false");
    }

    public byte[] loginEntry() {
        logger.info("loginEntry 微信支付监听 V1.1");
        Runtime.getRuntime().addShutdownHook(SHUTDOWN_HANDLER);
        if (ONLINE_FILE.exists()) {
            //方法检验结果
            ONLINE_FILE.delete();
        }
        login();
        return listener.getJpgDate();
    }

    public int getLoginCode(){
        return listener.getLoginCode();
    }

    private OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .cookieJar(cookieJar).build();
    // 禁止重定向
    private OkHttpClient client2 = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .cookieJar(cookieJar).followRedirects(false).build();

    private byte[] getLoginQRCode() {
        String url = "https://wx2.qq.com/?&lang=zh_CN";
        Request request = new Request.Builder().url(url)
                .addHeader("accept", "*/*")
                .addHeader("connection", "Keep-Alive")
                .addHeader("user-agent", UA)
                .build();
        Response response = null;
        String str = null;
        byte[] data = null;
        try {
            response = client.newCall(request).execute();
            response.close();

            url = "https://login.wx2.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx2.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=" + System.currentTimeMillis();
            request = new Request.Builder().url(url)
                    .addHeader("accept", "*/*")
                    .addHeader("connection", "Keep-Alive")
                    .addHeader("user-agent", UA)
                    .build();

            response = client.newCall(request).execute();
            checkStatusCode(response);
            str = response.body().string();
            response.close();
            uuid = Util.getStringMiddle(str, "window.QRLogin.uuid = \"", "\";");

            url = "https://login.weixin.qq.com/qrcode/" + uuid;
            request = new Request.Builder().url(url)
                    .addHeader("accept", "*/*")
                    .addHeader("connection", "Keep-Alive")
                    .addHeader("user-agent", UA)
                    .build();
            response = client.newCall(request).execute();
            checkStatusCode(response);
            data = response.body().bytes();
            response.close();
        } catch (IOException e) {
            logger.error("WeChatListenerServiceImpl getLoginQRCode", e);
        }
        return data;
    }

    private boolean checkQRCode() {
        if (lastCheckQRCodeTime == -1)
            lastCheckQRCodeTime = System.currentTimeMillis();
        else
            lastCheckQRCodeTime++;
        int r = (int) (System.currentTimeMillis() & 0xFFFFFFF);
        String url = "https://login.wx2.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=" + uuid + "&tip=0&r=-" + r + "&_=" + lastCheckQRCodeTime;
        Request request = new Request.Builder().url(url)
                .addHeader("accept", "*/*")
                .addHeader("connection", "Keep-Alive")
                .addHeader("user-agent", UA)
                .addHeader("host", "login.wx2.qq.com")
                .addHeader("referer", "https://wx2.qq.com/?&lang=zh_CN")
                .build();
        Response response = null;
        String str = null;
        try {
            response = client.newCall(request).execute();
            checkStatusCode(response);
            str = response.body().string();
            String code = Util.getStringMiddle(str, "code=", ";");
            switch (code) {
                case "408":
                    break;
                case "201":
                    String base64 = Util.getStringMiddle(str, "base64,", "'");
                    listener.onQRCodeScanned(new BASE64Decoder().decodeBuffer(base64));
                    break;
                case "200":
                    loginUrl = Util.getStringMiddle(str, "redirect_uri=\"", "\"");
                    domainName = Util.getStringMiddle(loginUrl, "//", "/");
                    pushDomainName = "webpush." + domainName;
                    return true;
                default:
                    logger.debug("checkQRCode: unknown code - " + str);
                    return false;
            }
        } catch (IOException e) {
            logger.error("checkQRCode", e);
        }
        return false;
    }

    private boolean checkIsLogged() {
        Request request = new Request.Builder().url(loginUrl)
                .addHeader("accept", "*/*")
                .addHeader("connection", "Keep-Alive")
                .addHeader("user-agent", UA)
                .build();
        Response response = null;
        String str = null;
        try {
            response = client2.newCall(request).execute();
            str = response.body().string();
            logger.info("checkIsLogged response.body {}",str);
        } catch (IOException e) {
            logger.error("checkIsLogged request{}",request, e);
        }

        response.close();
        if (Util.getStringMiddle(str, "<ret>", "</ret>").equals("0")) {
            skey = Util.getStringMiddle(str, "<skey>", "</skey>");
            wxsid = Util.getStringMiddle(str, "<wxsid>", "</wxsid>");
            wxuin = Util.getStringMiddle(str, "<wxuin>", "</wxuin>");
            pass_ticket = Util.getStringMiddle(str, "<pass_ticket>", "</pass_ticket>");
            loadSyncKey();
            return true;
        }
        return false;
    }

    public void login() {
        lastCheckQRCodeTime = -1;
        listener.onLoadingQRCode();
        byte[] data = getLoginQRCode();
        listener.onReceivedQRCode(data);
        new Thread(() -> {
            while (true) {
                if (checkQRCode())
                    break;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    logger.error("InterruptedException", e);
                }
            }
            boolean logged = checkIsLogged();
            listener.onLoginResult(logged);
            if (logged) {
                long time = System.currentTimeMillis();
                w:
                while (true) {
                    for (int i = 0; i < 5; i++) {
                        try {
                            if (syncCheck() < 1000)
                                continue w;
                        } catch (Throwable e) {
                            logger.debug("SyncCheck", e);
                            continue w;
                        }
                    }
                    // 如果10次都得到错误的返回码，break
                    break;
                }
                listener.onDropped(System.currentTimeMillis() - time);
            }
        }).start();
    }

    private void loadSyncKey() {
        String postData = "{\"BaseRequest\":{\"Uin\":\"" + wxuin
                + "\",\"Sid\":\"" + wxsid + "\",\"Skey\":\"" + skey
                + "\",\"DeviceID\":\"" + Util.get15RandomText() + "\"}}";
        int r = (int) (System.currentTimeMillis() & 0xFFFFFFF);
        String url = "https://" + domainName + "/cgi-bin/mmwebwx-bin/webwxinit?r=-" + r + "&lang=zh_CN&pass_ticket=" + pass_ticket;
        Request request = new Request.Builder().url(url)
                .method("POST", RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), postData))
                .addHeader("accept", "*/*")
                .addHeader("connection", "Keep-Alive")
                .addHeader("user-agent", UA)
                .addHeader("host", "wx.qq.com")
                .addHeader("content-type", "application/json;charset=UTF-8")
                .addHeader("referer", "https://" + domainName + "/")
                .build();

        Response response = null;
        JSONObject jsonObject = null;
        try {
            response = client.newCall(request).execute();
            jsonObject = JSONObject.fromObject(response.body().string());
            logger.info("loadSyncKey {}",jsonObject);
        } catch (IOException e) {
            logger.error("loadSyncKey IOException", e);
        }
        response.close();
        jsonObject = syncKeyJson = jsonObject.getJSONObject("SyncKey");

        int count = jsonObject.getInt("Count");
        JSONArray jsonArray = jsonObject.getJSONArray("List");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            jsonObject = jsonArray.getJSONObject(i);
            sb.append("|").append(jsonObject. getInt("Key")).append("_").append(jsonObject. getInt("Val"));
        }
        sb.deleteCharAt(0);
        syncKey = sb.toString();
        logger.info(syncKey);
//        logger.info(syncKeyJson.toString());
    }

    private int syncCheck() {
        String url = "https://" + pushDomainName + "/cgi-bin/mmwebwx-bin/synccheck?r=" + System.currentTimeMillis()
                + "&skey=" + skey.replace("@", "%40") + "&sid=" + wxsid + "&uin=" + wxuin + "&deviceid=" + Util.get15RandomText()
                + "&synckey=" + syncKey.replace("|", "%7C") + "&_=" + System.currentTimeMillis();

        logger.info("正在等待消息..");
        Request request = new Request.Builder().url(url)
                .addHeader("accept", "*/*")
                .addHeader("connection", "Keep-Alive")
                .addHeader("user-agent", UA)
                .addHeader("referer", "https://" + domainName + "/")
                .addHeader("host", pushDomainName)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (Exception e) {
            return 1100;
        }
        if (!response.isSuccessful()) {
            response.close();
            return 1100;
        }

        String str = null;
        try {
            str = response.body().string();
        } catch (IOException e) {
            logger.error("syncCheck IOException", e);
        }
        response.close();
        String retCode = Util.getStringMiddle(str, "retcode:\"", "\"");
        String selector = Util.getStringMiddle(str, "selector:\"", "\"");

        // 1101 1100 1102掉线
        if (retCode.length() == 4 && retCode.startsWith("1")) {
            logger.warn(str);
            return Integer.parseInt(retCode);
        }
        if (selector.equals("0"))
            return 0;
        // 有消息
        try {
            getMessage();
        } catch (Throwable e) {
             return 1000;
        }
        return 1;
    }

    public void getMessage() {
        JSONObject json = new JSONObject();
        JSONObject baseRequest = new JSONObject();
        baseRequest.put("Uin", wxuin);
        baseRequest.put("Sid", wxsid);
        baseRequest.put("Skey", skey);
        baseRequest.put("DeviceID", Util.get15RandomText());
        json.put("BaseRequest", baseRequest);
        json.put("SyncKey", syncKeyJson);
        StringBuilder sb = new StringBuilder("-1728");
        for (int i = 0; i < 6; i++) {
            sb.append(Util.RANDOM.nextInt(10));
        }
        json.put("rr", Integer.valueOf(sb.toString()));
        String content = json.toString();
        String url = "https://" + domainName + "/cgi-bin/mmwebwx-bin/webwxsync?sid=" + wxsid + "&skey=" + skey.replace("@", "%40") + "&pass_ticket=" + pass_ticket;
        Request request = new Request.Builder().url(url)
                .method("POST", RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), content))
                .addHeader("connection", "keep-alive")
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("user-agent", UA)
                .addHeader("content-type", "application/json;charset=UTF-8")
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (Exception e) {
            return;
        }
        if (!response.isSuccessful()) {
            response.close();
            return;
        }

        try {
            content = response.body().string();
        } catch (IOException e) {
            logger.error("getMessage IOException", e);
        }
        response.close();
        JSONObject jsonObject = JSONObject.fromObject(content);
        logger.info(jsonObject.toString());
        JSONObject syncKeyJson = this.syncKeyJson = jsonObject.getJSONObject("SyncKey");
        int count = syncKeyJson. getInt("Count");
        JSONArray jsonArray = syncKeyJson.getJSONArray("List");
        sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            syncKeyJson = jsonArray.getJSONObject(i);
            sb.append("|").append(syncKeyJson. getInt("Key")).append("_").append(syncKeyJson. getInt("Val"));
        }
        sb.deleteCharAt(0);
        syncKey = sb.toString();
        jsonArray = jsonObject.getJSONArray("AddMsgList");
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            jsonObject = jsonArray.getJSONObject(i);
            int msgType = jsonObject. getInt("MsgType");
            String con = jsonObject.getString("Content");
            if (msgType == 49) {
                checkPay(con);
            }
        }
    }

    /**
     * 判断是否是支付信息
     * @param con
     * @return
     */
    private void checkPay(String con) {
        if (!con.contains("CDATA[微信支付]") || !con.contains("CDATA[收款到账通知") || !con.contains("收款成功"))
            return;
        String money = Util.getStringMiddle(con, "收款金额：￥", "<br/>");
        if (money.isEmpty())
            return;
        try {
            Float.parseFloat(money);
        } catch (NumberFormatException e) {
            logger.error("checkPay NumberFormatException {}",money,e);
            return;
        }

        String mark = Util.getStringMiddle(con, "付款方备注：", "<br/>");
        String all = Util.getStringMiddle(con, "汇总：", "<br/>");
        String count = Util.getStringMiddle(all, "第", "笔");
        String allMoney = Util.getStringRight(all, "￥").replace(".", "");
        all = DATE_FORMAT.format(System.currentTimeMillis()) + "-" + count + "-" + allMoney;
        listener.onReceivedMoney(money, mark, all);
    }

    private static void checkStatusCode(Response response)  {
        if (!response.isSuccessful()) {
            logger.error("checkStatusCode 不成功");
        }
    }
}
