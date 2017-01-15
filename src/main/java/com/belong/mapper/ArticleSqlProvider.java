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

import com.belong.model.Article;
import com.belong.model.ArticleExample.Criteria;
import com.belong.model.ArticleExample.Criterion;
import com.belong.model.ArticleExample;
import java.util.List;
import java.util.Map;

public class ArticleSqlProvider {

    public String countByExample(ArticleExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("article");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(ArticleExample example) {
        BEGIN();
        DELETE_FROM("article");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(Article record) {
        BEGIN();
        INSERT_INTO("article");
        
        if (record.getAid() != null) {
            VALUES("Aid", "#{aid,jdbcType=INTEGER}");
        }
        
        if (record.getUid() != null) {
            VALUES("Uid", "#{uid,jdbcType=INTEGER}");
        }
        
        if (record.getAgree() != null) {
            VALUES("Agree", "#{agree,jdbcType=INTEGER}");
        }
        
        if (record.getAdate() != null) {
            VALUES("Adate", "#{adate,jdbcType=DATE}");
        }
        
        if (record.getVid() != null) {
            VALUES("Vid", "#{vid,jdbcType=INTEGER}");
        }
        
        if (record.getDisagree() != null) {
            VALUES("Disagree", "#{disagree,jdbcType=INTEGER}");
        }
        
        if (record.getAcontent() != null) {
            VALUES("Acontent", "#{acontent,jdbcType=LONGVARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExampleWithBLOBs(ArticleExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("Aid");
        } else {
            SELECT("Aid");
        }
        SELECT("Uid");
        SELECT("Agree");
        SELECT("Adate");
        SELECT("Vid");
        SELECT("Disagree");
        SELECT("Acontent");
        FROM("article");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String selectByExample(ArticleExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("Aid");
        } else {
            SELECT("Aid");
        }
        SELECT("Uid");
        SELECT("Agree");
        SELECT("Adate");
        SELECT("Vid");
        SELECT("Disagree");
        FROM("article");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Article record = (Article) parameter.get("record");
        ArticleExample example = (ArticleExample) parameter.get("example");
        
        BEGIN();
        UPDATE("article");
        
        if (record.getAid() != null) {
            SET("Aid = #{record.aid,jdbcType=INTEGER}");
        }
        
        if (record.getUid() != null) {
            SET("Uid = #{record.uid,jdbcType=INTEGER}");
        }
        
        if (record.getAgree() != null) {
            SET("Agree = #{record.agree,jdbcType=INTEGER}");
        }
        
        if (record.getAdate() != null) {
            SET("Adate = #{record.adate,jdbcType=DATE}");
        }
        
        if (record.getVid() != null) {
            SET("Vid = #{record.vid,jdbcType=INTEGER}");
        }
        
        if (record.getDisagree() != null) {
            SET("Disagree = #{record.disagree,jdbcType=INTEGER}");
        }
        
        if (record.getAcontent() != null) {
            SET("Acontent = #{record.acontent,jdbcType=LONGVARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("article");
        
        SET("Aid = #{record.aid,jdbcType=INTEGER}");
        SET("Uid = #{record.uid,jdbcType=INTEGER}");
        SET("Agree = #{record.agree,jdbcType=INTEGER}");
        SET("Adate = #{record.adate,jdbcType=DATE}");
        SET("Vid = #{record.vid,jdbcType=INTEGER}");
        SET("Disagree = #{record.disagree,jdbcType=INTEGER}");
        SET("Acontent = #{record.acontent,jdbcType=LONGVARCHAR}");
        
        ArticleExample example = (ArticleExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("article");
        
        SET("Aid = #{record.aid,jdbcType=INTEGER}");
        SET("Uid = #{record.uid,jdbcType=INTEGER}");
        SET("Agree = #{record.agree,jdbcType=INTEGER}");
        SET("Adate = #{record.adate,jdbcType=DATE}");
        SET("Vid = #{record.vid,jdbcType=INTEGER}");
        SET("Disagree = #{record.disagree,jdbcType=INTEGER}");
        
        ArticleExample example = (ArticleExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(Article record) {
        BEGIN();
        UPDATE("article");
        
        if (record.getUid() != null) {
            SET("Uid = #{uid,jdbcType=INTEGER}");
        }
        
        if (record.getAgree() != null) {
            SET("Agree = #{agree,jdbcType=INTEGER}");
        }
        
        if (record.getAdate() != null) {
            SET("Adate = #{adate,jdbcType=DATE}");
        }
        
        if (record.getVid() != null) {
            SET("Vid = #{vid,jdbcType=INTEGER}");
        }
        
        if (record.getDisagree() != null) {
            SET("Disagree = #{disagree,jdbcType=INTEGER}");
        }
        
        if (record.getAcontent() != null) {
            SET("Acontent = #{acontent,jdbcType=LONGVARCHAR}");
        }
        
        WHERE("Aid = #{aid,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(ArticleExample example, boolean includeExamplePhrase) {
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