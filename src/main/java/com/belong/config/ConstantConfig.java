package com.belong.config;

import java.util.HashMap;
import java.util.Map;

public class ConstantConfig {
    public static final String USERID = "userid";
    public static final String CUR_PAGE = "cur_page";
    public static final String ENCODER = "utf-8";
    public static final String N = "n";
    public static final String HOME = "video/home";
    public static final String SRCPATH = "srcpath";
    public static final String TXT = "txt";
    public static final String UPLOADSUCCESS = "上传成功";
    public static final String MSG = "msg";
    public static final String PLAY_SUCCC = "视频播放中，如果界面没有播放视频请稍后……";
    public static final String SUCCESS = "登录成功,欢迎";
    public static final String POST = "光临本站";
    public static final String FAILED = "对不起，登录失败，请注册账号或者密码和账号不一致";
    public static final String LOGOUT = "注销成功";
    public static final String USER = "global_user";
    public static final String COOKIEUSERNAME = "com.belong.username";
    public static final String COOKIEPASSWORD = "com.belong.password";
    public static final String OFF = "off";
    public static final String VISITOR = "visitor.txt";
    public static final String COUNT = "count";
    public static final String RFAILED = "对不起，注册失败了，别灰心再重新来一次吧";
    public static final String RSUCCESS = "恭喜你注册成功了，快去登陆吧";
    public static final String IMAGE = "image/jpeg";
    public static final String UPLAOD = "upload";
    public static final String SYSTEMSEPARATOR = "/";
    public static final String UPDATE = "修改成功";
    public static final String SETTING = "video/setting";
    public static final String COMMENT = "video/comment";
    public static final String A_VID = "a_Vid";
    public static final String UID = "Uid";
    public static final String VID = "Vid";
    public static final String CURPAGE = "cur_page";
    public static final String MOVIES_PATH = "movies/";
    public static final String PICTURE_PATH = "upload/";
    public static final String RESOURCE_PATH = "D:/Codes/My_Video/target/classes/static/resources/";
    public static final String ORDER_SWITCH = "order_switch";
    public static final String HOST = "127.0.0.1";
    public static final int RESIS_PORT = 6379;

    public static final Map<String,String> VIP_TYPE = new HashMap<>();
    public static final Map<String,Integer> VIP_CASH = new HashMap<>();
    static {
        VIP_TYPE.put("1","VIP");
        VIP_TYPE.put("2","SVIP");

        VIP_CASH.put("1",6);
        VIP_CASH.put("2",15);
        VIP_CASH.put("3",5);
    }
}
