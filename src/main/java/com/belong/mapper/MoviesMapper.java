package com.belong.mapper;

import com.belong.model.Movies;
import com.belong.model.MoviesExample;
import com.belong.model.MoviesWithBLOBs;
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

public interface MoviesMapper {
    @SelectProvider(type=MoviesSqlProvider.class, method="countByExample")
    int countByExample(MoviesExample example);

    @DeleteProvider(type=MoviesSqlProvider.class, method="deleteByExample")
    int deleteByExample(MoviesExample example);

    @Delete({
        "delete from movies",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer vid);

    @Insert({
        "insert into movies (Vid, Vdate, ",
        "id, views, Vname, ",
        "Vinfo, Vpic, ",
        "Vsrc)",
        "values (#{vid,jdbcType=INTEGER}, #{vdate,jdbcType=DATE}, ",
        "#{id,jdbcType=INTEGER}, #{views,jdbcType=DECIMAL}, #{vname,jdbcType=LONGVARCHAR}, ",
        "#{vinfo,jdbcType=LONGVARCHAR}, #{vpic,jdbcType=LONGVARBINARY}, ",
        "#{vsrc,jdbcType=LONGVARCHAR})"
    })
    int insert(MoviesWithBLOBs record);

    @InsertProvider(type=MoviesSqlProvider.class, method="insertSelective")
    int insertSelective(MoviesWithBLOBs record);

    @SelectProvider(type=MoviesSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Vdate", property="vdate", jdbcType=JdbcType.DATE),
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER),
        @Result(column="views", property="views", jdbcType=JdbcType.DECIMAL),
        @Result(column="Vname", property="vname", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="Vinfo", property="vinfo", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="Vpic", property="vpic", jdbcType=JdbcType.LONGVARBINARY),
        @Result(column="Vsrc", property="vsrc", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MoviesWithBLOBs> selectByExampleWithBLOBs(MoviesExample example);

    @SelectProvider(type=MoviesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Vdate", property="vdate", jdbcType=JdbcType.DATE),
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER),
        @Result(column="views", property="views", jdbcType=JdbcType.DECIMAL)
    })
    List<Movies> selectByExample(MoviesExample example);

    @Select({
        "select",
        "Vid, Vdate, id, views, Vname, Vinfo, Vpic, Vsrc",
        "from movies",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Vdate", property="vdate", jdbcType=JdbcType.DATE),
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER),
        @Result(column="views", property="views", jdbcType=JdbcType.DECIMAL),
        @Result(column="Vname", property="vname", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="Vinfo", property="vinfo", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="Vpic", property="vpic", jdbcType=JdbcType.LONGVARBINARY),
        @Result(column="Vsrc", property="vsrc", jdbcType=JdbcType.LONGVARCHAR)
    })
    MoviesWithBLOBs selectByPrimaryKey(Integer vid);

    @UpdateProvider(type=MoviesSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") MoviesWithBLOBs record, @Param("example") MoviesExample example);

    @UpdateProvider(type=MoviesSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") MoviesWithBLOBs record, @Param("example") MoviesExample example);

    @UpdateProvider(type=MoviesSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Movies record, @Param("example") MoviesExample example);

    @UpdateProvider(type=MoviesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MoviesWithBLOBs record);

    @Update({
        "update movies",
        "set Vdate = #{vdate,jdbcType=DATE},",
          "id = #{id,jdbcType=INTEGER},",
          "views = #{views,jdbcType=DECIMAL},",
          "Vname = #{vname,jdbcType=LONGVARCHAR},",
          "Vinfo = #{vinfo,jdbcType=LONGVARCHAR},",
          "Vpic = #{vpic,jdbcType=LONGVARBINARY},",
          "Vsrc = #{vsrc,jdbcType=LONGVARCHAR}",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(MoviesWithBLOBs record);

    @Update({
        "update movies",
        "set Vdate = #{vdate,jdbcType=DATE},",
          "id = #{id,jdbcType=INTEGER},",
          "views = #{views,jdbcType=DECIMAL}",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Movies record);
}