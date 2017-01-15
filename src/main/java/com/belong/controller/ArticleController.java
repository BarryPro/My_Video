package com.belong.controller;

import com.alibaba.fastjson.JSON;
import com.belong.model.Article;
import com.belong.model.PageBean;
import com.belong.model.Review;
import com.belong.service.IArticleService;
import com.belong.service.IMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by belong on 2017/1/10.
 */
@Controller
@RequestMapping(value = "/my_review")
@SessionAttributes(value = {"review","uid"})
public class ArticleController {
    private static final String COMMENT = "video/comment.ftl";
    private static final String A_VID = "a_Vid";
    private static final String UID = "Uid";
    private static final String USERID = "userid";
    private static final String VID = "Vid";
    private static final String CURPAGE = "cur_page";
    private static final String ENCODER = "utf-8";

    @Autowired
    private IMoviesService service;

    @Autowired
    private IArticleService aservice;

    @Autowired
    private VideoController videoController;

    @RequestMapping(value = "/review/Vid/{vid}")
    public String review(@PathVariable(value = "vid") int vid,
                         Map map) {
        map.put("vid", vid);
        Review review = service.review(map);
        map.put("review", review);
        return COMMENT;
    }

    @RequestMapping(value = "/query")
    public String query(@RequestParam(CURPAGE) int cur_page,
                        @RequestParam(VID) int vid,
                        @RequestParam(USERID) int userid,
                        Map map,
                        HttpServletResponse response){
        map.put(CURPAGE,cur_page);
        map.put(A_VID,vid);
        map.put(UID,userid);
        ArrayList<Article> data = aservice.queryArticle(map);
        PageBean pageBean = new PageBean();
        pageBean.setArticles(data);
        pageBean.setRow_num((int) map.get("a_pagenum"));
        pageBean.setRow_total((int) map.get("row_total"));
        pageBean.setPage_total((int) map.get("page_total"));
        pageBean.setCur_page((int) map.get("cur_page"));
        response.setCharacterEncoding(ENCODER);
        videoController.json(pageBean,response);
        return COMMENT;
    }

    @RequestMapping(value = "/add_article")
    public String addArticle(Article article,
                             Map map){
        map.put("article",article);
        aservice.addArticle(map);
        return COMMENT;
    }

    @RequestMapping(value = "/delete")
    public String deleteArticle(@RequestParam(value = "aid") int aid,
                                Map map){
        map.put("aid",aid);
        aservice.deleteArticle(map);
        return COMMENT;
    }

    @RequestMapping(value = "/reply")
    public String reply(Article article,
                             Map map){
        String new_content = article.getAcontent();
        map.put("article",article);
        article = aservice.queryArticleByAid(map);
        article.setAcontent(article.getAcontent()+new_content);
        map.put("article",article);
        aservice.updateArticle(map);
        return COMMENT;
    }

    @RequestMapping(value = "/agree")
    public String agree(@RequestParam("aid") int aid,
                        Map map,
                        HttpServletResponse response) {
        map.put("aid", aid);
        aservice.updateAgree(map);
        json(0, response);

        return COMMENT;
    }

    @RequestMapping(value = "/disagree")
    public String disagree(@RequestParam("aid") int aid,
                           Map map,
                           HttpServletResponse response) {
        map.put("aid", aid);
        aservice.updataDisagree(map);
        json(0, response);

        return COMMENT;
    }

    private void json(int num ,HttpServletResponse response){
        try {
            String json = JSON.toJSONString(num);
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
