package com.belong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.belong.config.ConstantConfig;
import com.belong.model.Movies;
import com.belong.model.PageBean;
import com.belong.model.Review;
import com.belong.model.User;
import com.belong.service.IMoviesService;
import com.belong.service.IUserService;
import com.belong.util.Util;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
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

    @Autowired
    private IMoviesService service;
    @Autowired
    private IUserService userService;

    //得到主页
    @RequestMapping(value = "/home")
    public String main() {
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/top20")
    public String getTop20(HttpServletResponse response) {
        List<Movies> list = service.getTop20();
        json(list, response);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/db_info")
    public String getDB_info(@RequestParam(ConstantConfig.N) String n,
                             @RequestParam(ConstantConfig.CUR_PAGE) String cur_page,
                             @RequestParam(ConstantConfig.USERID) String userid,
                             HttpServletResponse response) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("Vtype", Integer.parseInt(n));
        map.put("cur_page", Integer.parseInt(cur_page));
        map.put("Uid", Integer.parseInt(userid));
        ArrayList<Movies> data = service.getInfo(map);
        PageBean pageBean = new PageBean();
        pageBean.setData(data);
        pageBean.setRow_num(map.get("row_num"));
        pageBean.setRow_total(map.get("row_total"));
        pageBean.setPage_total(map.get("page_total"));
        pageBean.setCur_page(map.get("cur_page"));
        response.setCharacterEncoding(ConstantConfig.ENCODER);
        json(pageBean, response);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/pic/Vid/{vid}")
    public String getPic(@PathVariable(value = "vid") int vid, HttpServletResponse response) {
        response.setContentType(ConstantConfig.IMAGE);
        OutputStream os = null;
        try {
            Movies movies = service.getPic(vid);
            byte[] buffer = movies.getVpic();
            os = response.getOutputStream();
            os.write(buffer);
            os.flush();
            os.close();
        } catch (RedisConnectionFailureException redisExpction) {
            logger.error("VideoController getPic RedisConnectionFailureException");
        } catch (Exception e) {
            logger.error("IOException VideoController getPic", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("IOException VideoController getPic", e);
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/src/Vid/{vid}/Uid/{uid}")
    public String getPath(@PathVariable(value = "vid") int vid,
                          @PathVariable(value = "uid") int uid,
                          Map map,
                          HttpServletResponse response) {
        service.views(vid);
        map.put("vid", vid);
        Integer vType = service.getVType(map);
        int vip = 0;
        // 判断用户是否登录
        if (uid != -1) {
            map.put("user_id", uid);
            vip = userService.getVip(map);
        } else {
            map.put(ConstantConfig.MSG, "请登录后才进行购买");
        }
        boolean flag = false;
        String src_path = service.getPath(vid);
        // 判断用户的vip级别业务，用来区分是什么订单
        // 1.先判断是付费还是购买vip
        // 是付费的视频，付费的视频是vip和普通用户都需要购买的，只有svip是不需要购买的
        if (vType == 3) {
            User user = userService.getExtra(map);
            if (user != null) {
                Set<String> videoSet = (Set<String>) Util.getObjectFromJson(user.getExtra(), HashSet.class);
                if (videoSet.contains(vid + "")) {
                    map.put("extra_switch", "100");
                    flag = true;
                }
            }
            if (!flag) {
                // svip一下的级别都需要购买付费视频
                if (vip < 20) {
                    // 不允许播放，需要购买视频,第一位表示是否可以播放，第二位表示是否需要付费,第三位是表示是否是会员
                    // 播放标志，1表示可以播放、0表示不可以播放
                    // 付费标志，1表示需要付费、0表示不需要付费
                    // 会员标志，1表示会员、0表示普通用户
                    if (vip == 0) {
                        map.put("extra_switch", "010");
                    } else {
                        // 会员的付费价格按照普通用户打6折
                        map.put("extra_switch", "011");
                    }
                } else {
                    // svip可以观看付费视频
                    map.put("extra_switch", "10");
                    map.put(ConstantConfig.MSG, ConstantConfig.PLAY_SUCCC);
                }
            }
        } else {
            // 除了付费视频的其他不是免费的视频，且用户是普通用户，不能进行播放需要购买
            if (vType != 0 && vip == 0) {
                map.put("extra_switch", "00");
            } else {
                // vip用户可以观看vip视频和用券视频
                map.put("extra_switch", "10");
                map.put(ConstantConfig.MSG, ConstantConfig.PLAY_SUCCC);
            }
        }
        map.put(ConstantConfig.SRCPATH, src_path);
        json(map, response);
        return ConstantConfig.HOME;
    }

    @RequestMapping(value = "/search")
    public String search(@RequestParam(ConstantConfig.TXT) String txt,
                         @RequestParam(ConstantConfig.CUR_PAGE) int cur_page,
                         @RequestParam(ConstantConfig.USERID) int userid,
                         HttpServletResponse response) {
        Map<String, Object> map = new HashMap();
        //模糊查询
        txt = "%" + txt + "%";
        map.put("txt", txt);
        map.put("cur_page", cur_page);
        map.put("Uid", userid);
        ArrayList<Movies> data = service.search(map);
        PageBean pageBean = new PageBean();
        pageBean.setData(data);
        pageBean.setRow_num((int) map.get("row_num"));
        pageBean.setRow_total((int) map.get("row_total"));
        pageBean.setPage_total((int) map.get("page_total"));
        pageBean.setCur_page((int) map.get("cur_page"));
        response.setCharacterEncoding(ConstantConfig.ENCODER);
        json(pageBean, response);
        return ConstantConfig.HOME;
    }

    //json返回网页信息
    @JsonSerialize(using = ToStringSerializer.class)
    protected void json(Object object, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            String json = JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("IOException VideoController json", e);
        }
    }

    @RequestMapping(value = "/upload")
    public String upload(Movies movies,
                         Review review,
                         @RequestPart("filem") MultipartFile filem,
                         @RequestPart("filep") MultipartFile filep,
                         @RequestPart("id") String id,
                         HttpServletRequest request,
                         Map map) {
        String pic_type = filep.getContentType();
        String src_type = filem.getContentType();
        //符合上传要求才可以进行上传
        if (ConstantConfig.VIDEO_TYPE.containsKey(src_type) && ConstantConfig.PIC_TYPE.containsKey(pic_type)) {
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
            if (ConstantConfig.VIDEO_TYPE.containsKey(src_type)) {
                file = fileaname + ConstantConfig.VIDEO_TYPE.get(src_type);
                String vsrc = file;
                movies.setVsrc(vsrc);
                vsrc = ConstantConfig.MOVIES_PATH + file;
                String tarFile = tpath + vsrc;
                saveFile(filem, tarFile, false);
            }
            //处理上传图片
            if (ConstantConfig.PIC_TYPE.containsKey(pic_type)) {
                try {
                    byte[] vpic = filep.getBytes();
                    movies.setVpic(vpic);
                } catch (IOException e) {
                    logger.error("IOException VideoController upload", e);
                }
                file = fileaname + ConstantConfig.PIC_TYPE.get(pic_type);
                String uploadpath = ConstantConfig.PICTURE_PATH + file;
                String tarfile = tpath + uploadpath;
                saveFile(filep, tarfile, true);
            }
            map.put("_Vname", movies.getVname());
            map.put("_Vinfo", movies.getVinfo());
            map.put("_Vpic", movies.getVpic());
            map.put("_Vsrc", movies.getVsrc());
            map.put("_id", movies.getId());
            map.put("_mytype", movies.getType());
            map.put("_Vdirector", review.getVdirector());
            map.put("_Vactor", review.getVactor());
            service.upload(map);
            map.put(ConstantConfig.MSG, ConstantConfig.UPLOADSUCCESS);
        }
        return ConstantConfig.HOME;
    }

    //保存文件到服务器
    public boolean saveFile(MultipartFile file, String absFile, boolean flag) {
        File upload_dir = null;
        if (flag) {
            upload_dir = new File(ConstantConfig.RESOURCE_PATH + ConstantConfig.PICTURE_PATH);
        } else {
            upload_dir = new File(ConstantConfig.RESOURCE_PATH + ConstantConfig.MOVIES_PATH);
        }
        if (!upload_dir.exists()) {
            upload_dir.mkdirs();
        }
        try {
            file.transferTo(new File(absFile));
        } catch (IOException e) {
            logger.error("IOException VideoController saveFile", e);
        }
        return true;
    }

    /**
     * 判断返回那个页面
     *
     * @param page
     * @return
     */
    private String getReturnPage(String page) {
        if ("0".equals(page)) {
            return ConstantConfig.HOME;
        } else {
            return ConstantConfig.COMMENT;
        }
    }
}
