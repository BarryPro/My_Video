package com.belong.controller;

import com.belong.config.ConstantConfig;
import com.belong.model.User;
import com.belong.service.IUserService;
import com.belong.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private VideoController videoController;

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
        logger.info("UserController login{}",map);
        User cor_user = service.login(map);
        map.put("user",cor_user);
        if(cor_user!=null){
            // 更新用户的登录时间
            service.updateLoginTime(map);
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

    @RequestMapping(value= "/getVipJpg")
    public String getUserVip(@RequestParam(value = "user_id") String user_id,
            Map map,HttpServletResponse response){
        int vip = -1;
        if (!"-1".equals(user_id)) {
            map.put("user_id",user_id);
            vip = service.getVip(map);
        }
        map.put("vip",vip);
        videoController.json(map,response);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value="/setting_save")
    public String settingSave(@RequestParam("inputAlias") String inputAlias,
                              @RequestParam("inputEmail") String inputEmail,
                              @RequestParam("num") String num,
                              @RequestParam("user_id") String user_id,
                              Map map,
                              HttpServletResponse response){
        logger.info("settingSave inputAlias,inputEmail,num user_id[{},{},{},{}]",inputAlias,inputEmail,num,user_id);
        try {
            map.put("num",Integer.parseInt(num));
            map.put("email",inputEmail);
            map.put("alias",inputAlias);
            map.put("user_id",Integer.parseInt(user_id));
        } catch (NumberFormatException e){
            logger.error("NumberFormatException inputAlias,inputEmail,num user_id[{},{},{},{}]",inputAlias,inputEmail,num,user_id,e);
        }
        if(service.updateSettingSave(map)){
            map.put(ConstantConfig.MSG,"用户设置保存成功,重新登录账号后设置生效！");
        } else {
            map.put(ConstantConfig.MSG,"用户设置保存失败");
        }
        videoController.json(map,response);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/logout")
    public String logout(SessionStatus sessionStatus,
                         Map map){
        //注销当前的session
        sessionStatus.setComplete();
        map.put(ConstantConfig.MSG,ConstantConfig.LOGOUT);
        logger.info("UserController logout {}",map);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/visitor")
    public String getVisitor(HttpServletResponse response){
        InputStream is = UserController.class.getClassLoader().getResourceAsStream(ConstantConfig.VISITOR);
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
            logger.error("IOException UserController getVisitor",e);
        }
        return ConstantConfig.HOME;
    }

    //访客加1
    private void add(){
        InputStream is = UserController.class.getClassLoader().getResourceAsStream(ConstantConfig.VISITOR);
        Properties pro = new Properties();
        try {
            pro.load(is);
            String counter = pro.get(ConstantConfig.COUNT).toString();
            int sum = Integer.parseInt(counter);
            sum++;
            //得到项目目录
            String tpath = UserController.class.getClassLoader().getResource("").toString();
            String upload = tpath+ConstantConfig.VISITOR;
            //去掉file: 5个字符
            String stdupload = upload.substring(5,upload.length());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(stdupload)));
            String str = "count="+sum;
            bos.write(str.getBytes());
            bos.flush();
            bos.close();
        } catch (IOException e) {
            logger.info("IOException UserController add",e);
        }
    }

    @RequestMapping(value = "/register")
    public String register(User user,
                           Map map,
                           HttpServletRequest request,
                           @RequestPart("file0") MultipartFile file){
        String postfix = file.getContentType();
        if(ConstantConfig.PIC_TYPE.containsKey(postfix)){
            byte[] pic = null;
            try {
                pic = file.getBytes();
                //Blob blob = Blob.class.;
            } catch (IOException e) {
                logger.error("IOException UserController register pic",e);
            }
            //1.得到服务器的绝对路径eg:D:\IntelliJIDEA\Frame\MyVideo2\target\MyVideo2\
            String tpath = ConstantConfig.RESOURCE_PATH;
            String targetDIR = tpath+ConstantConfig.UPLAOD;
            //得到唯一的文件名存放到服务器中
            UUID filename = UUID.randomUUID();
            //组装文件名
            String _file  = filename+ConstantConfig.PIC_TYPE.get(postfix);
            String targetFile = targetDIR+ConstantConfig.SYSTEMSEPARATOR+_file;
            //得到最终存放的路径
            File tarFile = new File (targetFile);
            videoController.saveFile(file,targetFile,true);
            //加密保存
            String pwd = user.getPassword();
            user.setPassword(MD5.getMD5(user.getPassword()));
            user.setPic(pic);
            map.put("user",user);
            if(service.register(map)){
                map.put(ConstantConfig.MSG,ConstantConfig.RSUCCESS);
            } else {
                map.put(ConstantConfig.MSG,ConstantConfig.RFAILED);
            }
            user.setPassword(pwd);
            map.put("user",user);
            service.login(map);
        }
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/pic/userid/{uid}")
    public String getPic(@PathVariable(value = "uid") int uid,
                         Map map,
                         HttpServletResponse response){
        response.setContentType(ConstantConfig.IMAGE);
        try {
            map.put("user_id",uid);
            User user = service.getPic(map);
            byte[] buffer = user.getPic();
            OutputStream os = response.getOutputStream();
            os.write(buffer);
            os.flush();
            os.close();
        }catch (NullPointerException NPE){
            logger.error("NullPointerException UserController getPic");
        } catch (Exception e) {
            logger.error("IOException UserController getPic",e);
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
