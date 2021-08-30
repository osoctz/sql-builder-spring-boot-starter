package cn.metaq.sqlbuilder;

import cn.metaq.sqlbuilder.constants.SqlbuilderStepType;
import cn.metaq.sqlbuilder.model.CustomQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;

import java.util.List;
import java.util.Map;

public interface SqlbuilderStep {

    SqlbuilderStepType getType();
    void setType(SqlbuilderStepType type);
    CustomQuery build(DbSpec spec, DbSchema schema);
    List<Map<String,Object>> preview(String sql);
    SqlbuilderStep exec(SqlExecutor executor);
}
