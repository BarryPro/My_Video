package com.belong.controller;

import com.belong.encrypt.MD5;
import com.belong.model.User;
import com.belong.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;


/**
 * Created by belong on 2017/1/2.
 */
@Controller
@RequestMapping(value = "/my_user")
@SessionAttributes("global_user")
public class UserController {
    private static final String SUCCESS = "登录成功,欢迎";
    private static final String POST = "光临本站";
    private static final String FAILED = "对不起，登录失败，请注册账号或者密码和账号不一致";
    private static final String LOGOUT = "注销成功";
    private static final String MSG = "msg";
    private static final String USER = "global_user";
    private static final String COOKIEUSERNAME = "com.belong.username";
    private static final String COOKIEPASSWORD = "com.belong.password";
    private static final String OFF = "off";
    private static final String FILE = "visitor.txt";
    private static final String COUNT = "count";
    private static final String RFAILED = "对不起，注册失败了，别灰心再重新来一次吧";
    private static final String RSUCCESS = "恭喜你注册成功了，快去登陆吧";
    private static final String IMAGE = "image/jpeg";
    private static final String UPLAOD = "upload";
    private static final String SYSTEMSEPARATOR = "/";
    private static final String HOME = "video/home";
    private static final String UPDATE = "修改成功";
    private static final String SETTING = "video/setting";
    private static final String COMMENT = "video/comment";

    private HashMap<String,String> typep = new HashMap();

    public UserController(){
        typep.put("image/jpeg", ".jpg");
        typep.put("image/png", ".png");
        typep.put("image/gif", ".gif");
        typep.put("image/x-ms-bmp", ".bmp");
    }

    @Autowired
    private IUserService service;

    @RequestMapping(value = "/login")
    public String login(User user,
                        Map map,
                        @RequestParam(value = "action") int action,
                        @RequestParam(value = "cookie",defaultValue = "off") String cookie,
                        HttpServletResponse response){
        String msg;
        String cookiePWD = user.getPassword();
        user.setPassword(MD5.getMD5(user.getPassword()));
        User cor_user = service.login(user);
        if(cor_user!=null){
            if(!cookie.equals(OFF)){
                Cookie cookie1 = new Cookie(COOKIEUSERNAME,cor_user.getUsername());
                cookie1.setMaxAge(7*24*3600);
                Cookie cookie2 = new Cookie(COOKIEPASSWORD,cookiePWD);
                cookie2.setVersion(7*24*3600);
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }
            map.put(USER,cor_user);
            msg = SUCCESS+cor_user.getUsername()+POST;
        } else {
            msg = FAILED;
        }
        map.put(MSG,msg);
        if(action == 0){
            return HOME;
        } else {
            return COMMENT;
        }

    }

    @RequestMapping(value = "/logout")
    public String logout(SessionStatus sessionStatus,
                         Map map){
        //注销当前的session
        sessionStatus.setComplete();
        map.put(MSG,LOGOUT);
        return HOME;
    }

    @RequestMapping(value = "/visitor")
    public String getVisitor(HttpServletResponse response){
        InputStream is = UserController.class.getClassLoader().getResourceAsStream(FILE);
        Properties pro = new Properties();
        try {
            pro.load(is);
            String counter = pro.get(COUNT).toString();
            PrintWriter writer = response.getWriter();
            add();
            writer.write(counter);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HOME;
    }

    //访客加1
    private void add(){
        InputStream is = UserController.class.getClassLoader().getResourceAsStream(FILE);
        Properties pro = new Properties();
        try {
            pro.load(is);
            String counter = pro.get(COUNT).toString();
            int sum = Integer.parseInt(counter);
            sum++;
            //得到项目目录
            String tpath = UserController.class.getClassLoader().getResource("").toString();
            String upload = tpath+FILE;
            //去掉file: 5个字符
            String stdupload = upload.substring(5,upload.length());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(stdupload)));
            String str = "count="+sum;
            bos.write(str.getBytes());
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/register")
    public String register(User user,
                           Map map,
                           HttpServletRequest request,
                           @RequestPart("file0") MultipartFile file){
        String postfix = file.getContentType();
        if(typep.containsKey(postfix)){
            byte[] pic = null;
            try {
                pic = file.getBytes();
                //Blob blob = Blob.class.;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //1.得到服务器的绝对路径eg:D:\IntelliJIDEA\Frame\MyVideo2\target\MyVideo2\
            String tpath = request.getSession().getServletContext().getRealPath(SYSTEMSEPARATOR);
            String targetDIR = tpath+SYSTEMSEPARATOR+UPLAOD;
            //得到唯一的文件名存放到服务器中
            UUID filename = UUID.randomUUID();
            //组装文件名
            String _file  = filename+typep.get(postfix);
            String targetFile = targetDIR+SYSTEMSEPARATOR+_file;
            //得到最终存放的路径
            File tarFile = new File (targetFile);
            try {
                if(!tarFile.exists()){
                    tarFile.mkdirs();
                }
                file.transferTo(tarFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //加密保存
            user.setPassword(MD5.getMD5(user.getPassword()));
            user.setPic(pic);
            map.put("user",user);
            if(service.register(map)){
                map.put(MSG,RSUCCESS);
            } else {
                map.put(MSG,RFAILED);
            }
        }
        return HOME;
    }

    @RequestMapping(value = "/pic/userid/{uid}")
    public String getPic(@PathVariable(value = "uid") int uid, HttpServletResponse response){
        response.setContentType(IMAGE);
        try {
            User user = service.getPic(uid);
            byte[] buffer = user.getPic();
            OutputStream os = response.getOutputStream();
            os.write(buffer);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/num_setting")
    public String getSelect(@RequestParam("value") int value,
                            @RequestParam("userid") int userid,
                            Map map){
        map.put("id",userid);
        map.put("pagenum",value);
        service.updateVideoNumber(map);
        map.put(MSG,UPDATE);
        return HOME;
    }

    @RequestMapping(value = "/setting")
    public String setting(){
        return SETTING;
    }
}
