package com.belong.mapper;

import com.belong.model.Review;
import com.belong.model.ReviewExample;
import com.belong.model.ReviewWithBLOBs;
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

public interface ReviewMapper {
    @SelectProvider(type=ReviewSqlProvider.class, method="countByExample")
    int countByExample(ReviewExample example);

    @DeleteProvider(type=ReviewSqlProvider.class, method="deleteByExample")
    int deleteByExample(ReviewExample example);

    @Delete({
        "delete from review",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer vid);

    @Insert({
        "insert into review (Vid, Vamount, ",
        "Vdirector, Vactor)",
        "values (#{vid,jdbcType=INTEGER}, #{vamount,jdbcType=VARCHAR}, ",
        "#{vdirector,jdbcType=LONGVARCHAR}, #{vactor,jdbcType=LONGVARCHAR})"
    })
    int insert(ReviewWithBLOBs record);

    @InsertProvider(type=ReviewSqlProvider.class, method="insertSelective")
    int insertSelective(ReviewWithBLOBs record);

    @SelectProvider(type=ReviewSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Vamount", property="vamount", jdbcType=JdbcType.VARCHAR),
        @Result(column="Vdirector", property="vdirector", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="Vactor", property="vactor", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<ReviewWithBLOBs> selectByExampleWithBLOBs(ReviewExample example);

    @SelectProvider(type=ReviewSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Vamount", property="vamount", jdbcType=JdbcType.VARCHAR)
    })
    List<Review> selectByExample(ReviewExample example);

    @Select({
        "select",
        "Vid, Vamount, Vdirector, Vactor",
        "from review",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Vamount", property="vamount", jdbcType=JdbcType.VARCHAR),
        @Result(column="Vdirector", property="vdirector", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="Vactor", property="vactor", jdbcType=JdbcType.LONGVARCHAR)
    })
    ReviewWithBLOBs selectByPrimaryKey(Integer vid);

    @UpdateProvider(type=ReviewSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ReviewWithBLOBs record, @Param("example") ReviewExample example);

    @UpdateProvider(type=ReviewSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") ReviewWithBLOBs record, @Param("example") ReviewExample example);

    @UpdateProvider(type=ReviewSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Review record, @Param("example") ReviewExample example);

    @UpdateProvider(type=ReviewSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ReviewWithBLOBs record);

    @Update({
        "update review",
        "set Vamount = #{vamount,jdbcType=VARCHAR},",
          "Vdirector = #{vdirector,jdbcType=LONGVARCHAR},",
          "Vactor = #{vactor,jdbcType=LONGVARCHAR}",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(ReviewWithBLOBs record);

    @Update({
        "update review",
        "set Vamount = #{vamount,jdbcType=VARCHAR}",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Review record);
}