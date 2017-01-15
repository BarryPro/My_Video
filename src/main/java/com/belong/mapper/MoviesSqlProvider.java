package com.belong.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.belong.model.MoviesExample.Criteria;
import com.belong.model.MoviesExample.Criterion;
import com.belong.model.MoviesExample;
import com.belong.model.MoviesWithBLOBs;
import java.util.List;
import java.util.Map;

public class MoviesSqlProvider {

    public String countByExample(MoviesExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("movies");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(MoviesExample example) {
        BEGIN();
        DELETE_FROM("movies");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(MoviesWithBLOBs record) {
        BEGIN();
        INSERT_INTO("movies");
        
        if (record.getVid() != null) {
            VALUES("Vid", "#{vid,jdbcType=INTEGER}");
        }
        
        if (record.getVdate() != null) {
            VALUES("Vdate", "#{vdate,jdbcType=DATE}");
        }
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getViews() != null) {
            VALUES("views", "#{views,jdbcType=DECIMAL}");
        }
        
        if (record.getVname() != null) {
            VALUES("Vname", "#{vname,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getVinfo() != null) {
            VALUES("Vinfo", "#{vinfo,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getVpic() != null) {
            VALUES("Vpic", "#{vpic,jdbcType=LONGVARBINARY}");
        }
        
        if (record.getVsrc() != null) {
            VALUES("Vsrc", "#{vsrc,jdbcType=LONGVARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExampleWithBLOBs(MoviesExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("Vid");
        } else {
            SELECT("Vid");
        }
        SELECT("Vdate");
        SELECT("id");
        SELECT("views");
        SELECT("Vname");
        SELECT("Vinfo");
        SELECT("Vpic");
        SELECT("Vsrc");
        FROM("movies");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String selectByExample(MoviesExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("Vid");
        } else {
            SELECT("Vid");
        }
        SELECT("Vdate");
        SELECT("id");
        SELECT("views");
        FROM("movies");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        MoviesWithBLOBs record = (MoviesWithBLOBs) parameter.get("record");
        MoviesExample example = (MoviesExample) parameter.get("example");
        
        BEGIN();
        UPDATE("movies");
        
        if (record.getVid() != null) {
            SET("Vid = #{record.vid,jdbcType=INTEGER}");
        }
        
        if (record.getVdate() != null) {
            SET("Vdate = #{record.vdate,jdbcType=DATE}");
        }
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getViews() != null) {
            SET("views = #{record.views,jdbcType=DECIMAL}");
        }
        
        if (record.getVname() != null) {
            SET("Vname = #{record.vname,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getVinfo() != null) {
            SET("Vinfo = #{record.vinfo,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getVpic() != null) {
            SET("Vpic = #{record.vpic,jdbcType=LONGVARBINARY}");
        }
        
        if (record.getVsrc() != null) {
            SET("Vsrc = #{record.vsrc,jdbcType=LONGVARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("movies");
        
        SET("Vid = #{record.vid,jdbcType=INTEGER}");
        SET("Vdate = #{record.vdate,jdbcType=DATE}");
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("views = #{record.views,jdbcType=DECIMAL}");
        SET("Vname = #{record.vname,jdbcType=LONGVARCHAR}");
        SET("Vinfo = #{record.vinfo,jdbcType=LONGVARCHAR}");
        SET("Vpic = #{record.vpic,jdbcType=LONGVARBINARY}");
        SET("Vsrc = #{record.vsrc,jdbcType=LONGVARCHAR}");
        
        MoviesExample example = (MoviesExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("movies");
        
        SET("Vid = #{record.vid,jdbcType=INTEGER}");
        SET("Vdate = #{record.vdate,jdbcType=DATE}");
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("views = #{record.views,jdbcType=DECIMAL}");
        
        MoviesExample example = (MoviesExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(MoviesWithBLOBs record) {
        BEGIN();
        UPDATE("movies");
        
        if (record.getVdate() != null) {
            SET("Vdate = #{vdate,jdbcType=DATE}");
        }
        
        if (record.getId() != null) {
            SET("id = #{id,jdbcType=INTEGER}");
        }
        
        if (record.getViews() != null) {
            SET("views = #{views,jdbcType=DECIMAL}");
        }
        
        if (record.getVname() != null) {
            SET("Vname = #{vname,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getVinfo() != null) {
            SET("Vinfo = #{vinfo,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getVpic() != null) {
            SET("Vpic = #{vpic,jdbcType=LONGVARBINARY}");
        }
        
        if (record.getVsrc() != null) {
            SET("Vsrc = #{vsrc,jdbcType=LONGVARCHAR}");
        }
        
        WHERE("Vid = #{vid,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(MoviesExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}