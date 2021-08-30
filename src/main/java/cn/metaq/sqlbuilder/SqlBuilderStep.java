package cn.metaq.sqlbuilder;

import cn.metaq.sqlbuilder.constants.SqlBuilderStepType;
import cn.metaq.sqlbuilder.model.CustomQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;

import java.util.List;
import java.util.Map;

public interface SqlBuilderStep {

    SqlBuilderStepType getType();
    void setType(SqlBuilderStepType type);
    CustomQuery build(DbSpec spec, DbSchema schema);
    List<Map<String,Object>> preview(String sql);
}
