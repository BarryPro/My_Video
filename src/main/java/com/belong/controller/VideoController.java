package com.belong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.belong.config.ConstantConfig;
import com.belong.model.Movies;
import com.belong.model.PageBean;
import com.belong.model.Review;
import com.belong.service.IMoviesService;
import com.belong.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;


/**
 * Created by belong on 2017/1/1.
 */
@Controller
@RequestMapping(value = "/my_video")
@SessionAttributes(value = "video")
public class VideoController {
    Logger logger = LoggerFactory.getLogger(VideoController.class);
    private HashMap<String,String> typep = new HashMap();
    private HashMap<String,String> typem = new HashMap();

    public VideoController(){
        typem.put("video/avi", ".avi");
        typem.put("video/mp4", ".mp4");
        typep.put("image/jpeg", ".jpg");
        typep.put("image/png", ".png");
        typep.put("image/gif", ".gif");
        typep.put("image/x-ms-bmp", ".bmp");
    }

    @Autowired
    private IMoviesService service;
    @Autowired
    private IUserService userService;

    //得到主页
    @RequestMapping(value = "/home")
    public String main(){
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/top20")
    public String getTop20( HttpServletResponse response){
        List<Movies> list = service.getTop20();
        json(list,response);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/db_info")
    public String getDB_info(@RequestParam(ConstantConfig.N) String n,
                             @RequestParam(ConstantConfig.CUR_PAGE) String cur_page,
                             @RequestParam(ConstantConfig.USERID) String userid,
                             HttpServletResponse response){
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("Vtype",Integer.parseInt(n));
        map.put("cur_page",Integer.parseInt(cur_page));
        map.put("Uid",Integer.parseInt(userid));
        ArrayList<Movies> data = service.getInfo(map);
        PageBean pageBean = new PageBean();
        pageBean.setData(data);
        pageBean.setRow_num(map.get("row_num"));
        pageBean.setRow_total(map.get("row_total"));
        pageBean.setPage_total(map.get("page_total"));
        pageBean.setCur_page(map.get("cur_page"));
        response.setCharacterEncoding(ConstantConfig.ENCODER);
        json(pageBean,response);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/pic/Vid/{vid}")
    public String getPic(@PathVariable(value = "vid") int vid, HttpServletResponse response){
        response.setContentType(ConstantConfig.IMAGE);
        OutputStream os = null;
        try {
            Movies movies = service.getPic(vid);
            byte[] buffer = movies.getVpic();
            os = response.getOutputStream();
            os.write(buffer);
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error("IOException VideoController getPic",e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("IOException VideoController getPic",e);
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/src/Vid/{vid}/Uid/{uid}")
    public String getPath(@PathVariable(value = "vid") int vid,
                          @PathVariable(value = "uid") int uid,
                          Map map,
                          HttpServletResponse response){
        service.views(vid);
        map.put("vid",vid);
        Integer vType = service.getVType(map);
        int vip = 0;
        if (uid != -1) {
            map.put("uid",uid);
            vip = userService.getVip(map);
        }
        if (vType != 0 && vip < 1) {
            map.put("play_switch",0);
        } else {
            map.put("play_switch", 1);
            String srcpath = service.getPath(vid);
            map.put(ConstantConfig.SRCPATH, srcpath);
            map.put(ConstantConfig.MSG, ConstantConfig.PLAY_SUCCC);
        }
        json(map,response);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/search")
    public String search(@RequestParam(ConstantConfig.TXT) String txt,
                         @RequestParam(ConstantConfig.CUR_PAGE) int cur_page,
                         @RequestParam(ConstantConfig.USERID) int userid,
                         HttpServletResponse response){
        Map<String,Object> map = new HashMap();
        //模糊查询
        txt = "%"+txt+"%";
        map.put("txt",txt);
        map.put("cur_page",cur_page);
        map.put("Uid",userid);
        ArrayList<Movies> data = service.search(map);
        PageBean pageBean = new PageBean();
        pageBean.setData(data);
        pageBean.setRow_num((int) map.get("row_num"));
        pageBean.setRow_total((int) map.get("row_total"));
        pageBean.setPage_total((int) map.get("page_total"));
        pageBean.setCur_page((int) map.get("cur_page"));
        response.setCharacterEncoding(ConstantConfig.ENCODER);
        json(pageBean,response);
        return ConstantConfig.HOME;
    }

    //json返回网页信息
    protected void json(Object object,HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            String json = JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect);
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("IOException VideoController json",e);
        }
    }

    @RequestMapping(value = "/upload")
    public String upload(Movies movies,
                         Review review,
                         @RequestPart("filem") MultipartFile filem,
                         @RequestPart("filep") MultipartFile filep,
                         @RequestPart("id") String id,
                         HttpServletRequest request,
                         Map map){
        String pic_type = filep.getContentType();
        String src_type = filem.getContentType();
        //符合上传要求才可以进行上传
        if(typem.containsKey(src_type) && typep.containsKey(pic_type)){
            //得到服务器的绝对路径eg:D:\IntelliJIDEA\Frame\MyVideo2\target\MyVideo2\
            String tpath = ConstantConfig.RESOURCE_PATH;
//                    request.getSession().getServletContext().getRealPath(SYSTEMSEPARATOR);
            //得到随机的文件名称
            //System.out.println(tpath);
            UUID fileaname = UUID.randomUUID();
            String file = "";
            //记录是那个用户上传的
            movies.setId(Integer.parseInt(id));
            //处理长传视频
            if(typem.containsKey(src_type)){
                file = fileaname+typem.get(src_type);
                String vsrc = file;
                movies.setVsrc(vsrc);
                vsrc = ConstantConfig.MOVIES_PATH+file;
                String tarFile = tpath+vsrc;
                saveFile(filem,tarFile,false);
            }
            //处理上传图片
            if(typep.containsKey(pic_type)){
                try {
                    byte[] vpic = filep.getBytes();
                    movies.setVpic(vpic);
                } catch (IOException e) {
                    logger.error("IOException VideoController upload",e);
                }
                file = fileaname+typep.get(pic_type);
                String uploadpath = ConstantConfig.PICTURE_PATH+file;
                String tarfile = tpath+uploadpath;
                saveFile(filep,tarfile,true);
            }
            map.put("_Vname",movies.getVname());
            map.put("_Vinfo",movies.getVinfo());
            map.put("_Vpic",movies.getVpic());
            map.put("_Vsrc",movies.getVsrc());
            map.put("_id",movies.getId());
            map.put("_mytype",movies.getType());
            map.put("_Vdirector",review.getVdirector());
            map.put("_Vactor",review.getVactor());
            service.upload(map);
            map.put(ConstantConfig.MSG,ConstantConfig.UPLOADSUCCESS);
        }
        return ConstantConfig.HOME;
    }

    //保存文件到服务器
    public boolean saveFile(MultipartFile file, String absFile, boolean flag){
        File upload_dir = null;
        if (flag) {
            upload_dir = new File(ConstantConfig.RESOURCE_PATH + ConstantConfig.PICTURE_PATH);
        } else {
            upload_dir = new File(ConstantConfig.RESOURCE_PATH + ConstantConfig.MOVIES_PATH);
        }
        if(!upload_dir.exists()){
            upload_dir.mkdirs();
        }
        try {
            file.transferTo(new File(absFile));
        } catch (IOException e) {
            logger.error("IOException VideoController saveFile",e);
        }
        return true;
    }

    /**
     * 判断返回那个页面
     * @param page
     * @return
     */
    private String getReturnPage(String page){
        if ("0".equals(page)) {
            return ConstantConfig.HOME;
        } else {
            return ConstantConfig.COMMENT;
        }
    }
}
