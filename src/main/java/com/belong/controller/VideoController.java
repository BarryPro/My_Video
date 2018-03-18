package com.belong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.belong.config.MyVideoConfig;
import com.belong.model.Movies;
import com.belong.model.PageBean;
import com.belong.model.Review;
import com.belong.service.IMoviesService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by belong on 2017/1/1.
 */
@Controller
@RequestMapping(value = "/my_video")
@SessionAttributes(value = "video")
public class VideoController {
    private static final String USERID = "userid";
    private static final String CUR_PAGE = "cur_page";
    private static final String ENCODER = "utf-8";
    private static final String N = "n";
    private static final String IMAGE = "image/jpeg";
    private static final String HOME = "video/home";
    private static final String PLAYER = "video/player";
    private static final String SRCPATH = "srcpath";
    private static final String TXT = "txt";
    private static final String SYSTEMSEPARATOR = "/";
    private static final String UPLOADSUCCESS = "上传成功";
    private static final String MSG = "msg";

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

    //得到主页
    @RequestMapping(value = "/home")
    public String main(){
        return HOME;
    }

    @RequestMapping(value = "/db_info")
    public String getDB_info(@RequestParam(N) String n,
                             @RequestParam(CUR_PAGE) String cur_page,
                             @RequestParam(USERID) String userid,
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
        response.setCharacterEncoding(ENCODER);
        json(pageBean,response);
        return HOME;
    }

    @RequestMapping(value = "/pic/Vid/{vid}")
    public String getPic(@PathVariable(value = "vid") int vid, HttpServletResponse response){
        response.setContentType(IMAGE);
        OutputStream os = null;
        try {
            Movies movies = service.getPic(vid);
            byte[] buffer = movies.getVpic();
            os = response.getOutputStream();
            os.write(buffer);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/src/Vid/{vid}")
    public String getPath(@PathVariable(value = "vid") int vid,
                          Map map){
        service.views(vid);
        String srcpath = service.getPath(vid);
        map.put(SRCPATH,srcpath);
        return PLAYER;
    }

    @RequestMapping(value = "/search")
    public String search(@RequestParam(TXT) String txt,
                         @RequestParam(CUR_PAGE) int cur_page,
                         @RequestParam(USERID) int userid,
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
        response.setCharacterEncoding(ENCODER);
        json(pageBean,response);
        return HOME;
    }

    //json返回网页信息
    protected void json(PageBean pageBean,HttpServletResponse response){
        try {
            String json = JSON.toJSONString(pageBean,SerializerFeature.DisableCircularReferenceDetect);
            //System.out.println(json);
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
            String tpath = MyVideoConfig.RESOURCE_PATH;
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
                vsrc = MyVideoConfig.MOVIES_PATH+file;
                String tarFile = tpath+vsrc;
                saveFile(filem,tarFile,false);
            }
            //处理上传图片
            if(typep.containsKey(pic_type)){
                try {
                    byte[] vpic = filep.getBytes();
                    movies.setVpic(vpic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                file = fileaname+typep.get(pic_type);
                String uploadpath = MyVideoConfig.PICTURE_PATH+file;
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
            map.put(MSG,UPLOADSUCCESS);
        }
        return HOME;
    }

    //保存文件到服务器
    private boolean saveFile(MultipartFile file, String absFile, boolean flag){
        File upload_dir = null;
        if (flag) {
            upload_dir = new File(MyVideoConfig.RESOURCE_PATH+MyVideoConfig.PICTURE_PATH);
        } else {
            upload_dir = new File(MyVideoConfig.RESOURCE_PATH+MyVideoConfig.MOVIES_PATH);
        }
        if(!upload_dir.exists()){
            upload_dir.mkdirs();
        }
        try {
            file.transferTo(new File(absFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }



}
