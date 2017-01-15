package com.belong.mapper;

import com.belong.model.Type;
import com.belong.model.TypeExample;
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

public interface TypeMapper {
    @SelectProvider(type=TypeSqlProvider.class, method="countByExample")
    int countByExample(TypeExample example);

    @DeleteProvider(type=TypeSqlProvider.class, method="deleteByExample")
    int deleteByExample(TypeExample example);

    @Delete({
        "delete from _type",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer vid);

    @Insert({
        "insert into _type (Vid, Tid)",
        "values (#{vid,jdbcType=INTEGER}, #{tid,jdbcType=VARCHAR})"
    })
    int insert(Type record);

    @InsertProvider(type=TypeSqlProvider.class, method="insertSelective")
    int insertSelective(Type record);

    @SelectProvider(type=TypeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Tid", property="tid", jdbcType=JdbcType.VARCHAR)
    })
    List<Type> selectByExample(TypeExample example);

    @Select({
        "select",
        "Vid, Tid",
        "from _type",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Vid", property="vid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="Tid", property="tid", jdbcType=JdbcType.VARCHAR)
    })
    Type selectByPrimaryKey(Integer vid);

    @UpdateProvider(type=TypeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Type record, @Param("example") TypeExample example);

    @UpdateProvider(type=TypeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Type record, @Param("example") TypeExample example);

    @UpdateProvider(type=TypeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Type record);

    @Update({
        "update _type",
        "set Tid = #{tid,jdbcType=VARCHAR}",
        "where Vid = #{vid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Type record);
}