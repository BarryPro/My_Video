package com.belong.controller;

import com.belong.config.ConstantConfig;
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
        map.put("user",user);
        User cor_user = service.login(map);
        if(cor_user!=null){
            if(!cookie.equals(ConstantConfig.OFF)){
                Cookie cookie1 = new Cookie(ConstantConfig.COOKIEUSERNAME,cor_user.getUsername());
                cookie1.setMaxAge(7*24*3600);
                Cookie cookie2 = new Cookie(ConstantConfig.COOKIEPASSWORD,cookiePWD);
                cookie2.setVersion(7*24*3600);
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }
            map.put(ConstantConfig.USER,cor_user);
            msg = ConstantConfig.SUCCESS+cor_user.getUsername()+ConstantConfig.POST;
        } else {
            msg = ConstantConfig.FAILED;
        }
        map.put(ConstantConfig.MSG,msg);
        if(action == 0){
            return ConstantConfig.HOME;
        } else {
            return ConstantConfig.COMMENT;
        }

    }

    @RequestMapping(value = "/logout")
    public String logout(SessionStatus sessionStatus,
                         Map map){
        //注销当前的session
        sessionStatus.setComplete();
        map.put(ConstantConfig.MSG,ConstantConfig.LOGOUT);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/visitor")
    public String getVisitor(HttpServletResponse response){
        InputStream is = UserController.class.getClassLoader().getResourceAsStream(ConstantConfig.FILE);
        Properties pro = new Properties();
        try {
            pro.load(is);
            String counter = pro.get(ConstantConfig.COUNT).toString();
            PrintWriter writer = response.getWriter();
            add();
            writer.write(counter);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ConstantConfig.HOME;
    }

    //访客加1
    private void add(){
        InputStream is = UserController.class.getClassLoader().getResourceAsStream(ConstantConfig.FILE);
        Properties pro = new Properties();
        try {
            pro.load(is);
            String counter = pro.get(ConstantConfig.COUNT).toString();
            int sum = Integer.parseInt(counter);
            sum++;
            //得到项目目录
            String tpath = UserController.class.getClassLoader().getResource("").toString();
            String upload = tpath+ConstantConfig.FILE;
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
            String tpath = request.getSession().getServletContext().getRealPath(ConstantConfig.SYSTEMSEPARATOR);
            String targetDIR = tpath+ConstantConfig.SYSTEMSEPARATOR+ConstantConfig.UPLAOD;
            //得到唯一的文件名存放到服务器中
            UUID filename = UUID.randomUUID();
            //组装文件名
            String _file  = filename+typep.get(postfix);
            String targetFile = targetDIR+ConstantConfig.SYSTEMSEPARATOR+_file;
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
                map.put(ConstantConfig.MSG,ConstantConfig.RSUCCESS);
            } else {
                map.put(ConstantConfig.MSG,ConstantConfig.RFAILED);
            }
        }
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/pic/userid/{uid}")
    public String getPic(@PathVariable(value = "uid") int uid, HttpServletResponse response){
        response.setContentType(ConstantConfig.IMAGE);
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
        map.put(ConstantConfig.MSG,ConstantConfig.UPDATE);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/setting")
    public String setting(){
        return ConstantConfig.SETTING;
    }
}
