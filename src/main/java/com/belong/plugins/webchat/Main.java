package com.belong.plugins.webchat;

import com.belong.plugins.util.MtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

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

    public static void main(String[] args) throws IOException {
        logger.info("微信支付监听 V1.1");
        Runtime.getRuntime().addShutdownHook(SHUTDOWN_HANDLER);
        if (ONLINE_FILE.exists())
            //方法检验结果
            ONLINE_FILE.delete();

        login();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.nextLine().equalsIgnoreCase("exit")) {
                System.exit(0);
                break;
            }
        }
    }

    private static void login() {
        // 定义微信登录监听收钱的监听类
        final WeChat.WeChatListener listener = new WeChat.WeChatListener() {
            ImageViewer viewerFrame;

            @Override
            public void onLoadingQRCode() {
                logger.info("WeChat.WeChatListener onLoadingQRCode 正在获取登录二维码..");
            }

            @Override
            public void onReceivedQRCode(byte[] jpgData) {
                logger.info("WeChat.WeChatListener onReceivedQRCode 获取成功，请用手机微信扫码");
                viewerFrame = new ImageViewer(jpgData);
            }

            @Override
            public void onQRCodeScanned(byte[] jpgData) {
                logger.info("WeChat.WeChatListener onQRCodeScanned 扫码成功，请在手机微信中点击登录");
                if (viewerFrame != null) {
                    viewerFrame.setImage(jpgData);
                }
            }

            @Override
            public void onLoginResult(boolean loginSucceed) {
                if (viewerFrame != null) {
                    viewerFrame.dispose();
                    viewerFrame = null;
                }
                if (loginSucceed) {
                    logger.info("WeChat.WeChatListener onLoginResult 登录成功");
                    // 登录成功后标识用户在线
                    try {
                        if (!ONLINE_FILE.createNewFile()) {
                            logger.error("WeChat.WeChatListener onLoginResult 创建Online文件失败");
                        }
                    } catch (IOException e) {
                        logger.error("WeChat.WeChatListener onLoginResult 创建Online文件失败",e);
                    }
                } else {
                    logger.info("WeChat.WeChatListener onLoginResult 登录失败");
                    // 删除在线文件
                    ONLINE_FILE.delete();
                }
            }

            String lastID = "";

            @Override
            public void onReceivedMoney(String money, String mark, String id) throws IOException {
                if (lastID.equals(id))
                    return;
                lastID = id;
                logger.info("WeChat.WeChatListener onReceivedMoney 二维码收款：{}元，备注：{}", money, mark.isEmpty() ? "无" : mark);
                // 下面是收到转账后处理，业务代码不公开，请改成你自己的
                MtUtil.openVip(mark, money, id);
            }

            @Override
            public void onDropped(long onlineTime) {
                // 删除在线文件
                ONLINE_FILE.delete();
                if (onlineTime > 5000) {
                    try {
                        if (Email.sendEmail("1278423697@qq.com", "微信离线通知", "服务器的微信已经离线啦，快去登录！"))
                            logger.info("WeChat.WeChatListener onDropped 微信已离线，发送通知邮件成功");
                        else
                            logger.error("WeChat.WeChatListener onDropped 微信已离线，发送通知邮件失败");
                    } catch (Exception e) {
                        logger.error("WeChat.WeChatListener onDropped 微信已离线，发送通知邮件失败",e);
                    }
                } else {
                    logger.info("请尝试重新登录");
                    login();
                }
            }

            @Override
            public void onException(IOException e) {
                logger.error("Main onException",e);
                if (viewerFrame != null) {
                    viewerFrame.dispose();
                    viewerFrame = null;
                }
            }
        };
        // 通过设置监听类进行微信登录
        new WeChat(listener).login();
    }
}
