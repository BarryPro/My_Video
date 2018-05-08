package com.belong.util;

import com.alibaba.fastjson.JSONObject;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Random;

public class Util {
    public static final Random RANDOM = new Random();

    public static Object getObjectFromJson(String extra, Class objClass) {
        return JSONObject.parseObject(extra, objClass);
    }

    public static String get15RandomText() {
        StringBuilder sb = new StringBuilder("e");
        sb.append(RANDOM.nextInt(9) + 1);
        for (int i = 0; i < 14; i++)
            sb.append(RANDOM.nextInt(10));
        return sb.toString();
    }


    public static String getStringMiddle(String str, String left, String right) {
        int i = str.indexOf(left);
        if (i == -1)
            return "";
        int j = str.indexOf(right, i + left.length() + 1);
        if (j == -1)
            return "";
        return str.substring(i + left.length(), j);
    }

    public static String getStringRight(String str, String left) {
        int i = str.indexOf(left);
        if (i == -1)
            return "";
        return str.substring(i + left.length());
    }

    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final Charset CHARSET = Charset.forName("UTF-8");

    public static String getMD5(String value) throws Exception {
        // 生成一个MD5加密计算摘要
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md5.update(value.getBytes());
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        return new BigInteger(1, md5.digest()).toString(16);
    }
}
