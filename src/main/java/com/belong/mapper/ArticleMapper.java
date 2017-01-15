package com.belong.mapper;

import com.belong.model.Article;
import com.belong.model.ArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface ArticleMapper {
    @SelectProvider(type=ArticleSqlProvider.class, method="countByExample")
    int countByExample(ArticleExample example);

    @DeleteProvider(type=ArticleSqlProvider.class, method="deleteByExample")
    int deleteByExample(ArticleExample example);

    @Delete({
        "delete from article",
        "where Aid = #{aid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer aid);

    @Insert({
        "insert into article (Aid, Uid, ",
        "Agree, Adate, Vid, ",
        "Disagree, Acontent)",
        "values (#{aid,jdbcType=INTEGER}, #{uid,jdbcType=INTEGER}, ",
        "#{agree,jdbcType=INTEGER}, #{adate,jdbcType=DATE}, #{vid,jdbcType=INTEGER}, ",
        "#{disagree,jdbcType=INTEGER}, #{acontent,jdbcType=LONGVARCHAR})"
    })
    int insert(Article record);

    @InsertProvider(type=ArticleSqlProvider.class, method="insertSelective")
    int insertSelective(Article record);

    @SelectProvider(type=ArticleSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="Aid", property="aid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Uid", property="uid", jdbcType=JdbcType.INTEGER),
        @Result(column="Agree", property="agree", jdbcType=JdbcType.INTEGER),
        @Result(column="Adate", property="adate", jdbcType=JdbcType.DATE),
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER),
        @Result(column="Disagree", property="disagree", jdbcType=JdbcType.INTEGER),
        @Result(column="Acontent", property="acontent", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Article> selectByExampleWithBLOBs(ArticleExample example);

    @SelectProvider(type=ArticleSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="Aid", property="aid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Uid", property="uid", jdbcType=JdbcType.INTEGER),
        @Result(column="Agree", property="agree", jdbcType=JdbcType.INTEGER),
        @Result(column="Adate", property="adate", jdbcType=JdbcType.DATE),
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER),
        @Result(column="Disagree", property="disagree", jdbcType=JdbcType.INTEGER)
    })
    List<Article> selectByExample(ArticleExample example);

    @Select({
        "select",
        "Aid, Uid, Agree, Adate, Vid, Disagree, Acontent",
        "from article",
        "where Aid = #{aid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Aid", property="aid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Uid", property="uid", jdbcType=JdbcType.INTEGER),
        @Result(column="Agree", property="agree", jdbcType=JdbcType.INTEGER),
        @Result(column="Adate", property="adate", jdbcType=JdbcType.DATE),
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER),
        @Result(column="Disagree", property="disagree", jdbcType=JdbcType.INTEGER),
        @Result(column="Acontent", property="acontent", jdbcType=JdbcType.LONGVARCHAR)
    })
    Article selectByPrimaryKey(Integer aid);

    @UpdateProvider(type=ArticleSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    @UpdateProvider(type=ArticleSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Article record, @Param("example") ArticleExample example);

    @UpdateProvider(type=ArticleSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    @UpdateProvider(type=ArticleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Article record);

    @Update({
        "update article",
        "set Uid = #{uid,jdbcType=INTEGER},",
          "Agree = #{agree,jdbcType=INTEGER},",
          "Adate = #{adate,jdbcType=DATE},",
          "Vid = #{vid,jdbcType=INTEGER},",
          "Disagree = #{disagree,jdbcType=INTEGER},",
          "Acontent = #{acontent,jdbcType=LONGVARCHAR}",
        "where Aid = #{aid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Article record);

    @Update({
        "update article",
        "set Uid = #{uid,jdbcType=INTEGER},",
          "Agree = #{agree,jdbcType=INTEGER},",
          "Adate = #{adate,jdbcType=DATE},",
          "Vid = #{vid,jdbcType=INTEGER},",
          "Disagree = #{disagree,jdbcType=INTEGER}",
        "where Aid = #{aid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Article record);
}