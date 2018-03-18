package com.belong.model;

/**
 * Created by belong on 2017/1/2.
 */
import java.io.Serializable;
import java.util.ArrayList;
import lombok.Setter;
import lombok.Getter;


/**
 * Created by belong on 16-11-12.
 */
@Setter
@Getter
public class PageBean implements Serializable{
    private int row_num;//每页的行数
    private int row_total;//一共由多少行
    private int page_total;//一共有多少页
    private int cur_page;//当前是第几页
    private ArrayList<Movies> data;//存放电影信息
    private ArrayList<Article> articles;//用于存放评论信息
}
