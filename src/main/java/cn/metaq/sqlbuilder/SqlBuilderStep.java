package cn.metaq.sqlbuilder;

import cn.metaq.sqlbuilder.constants.SqlBuilderStepType;
import cn.metaq.sqlbuilder.model.CustomQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;

public interface SqlBuilderStep {

    SqlBuilderStepType getType();
    void setType(SqlBuilderStepType type);
    CustomQuery build(DbSpec spec, DbSchema schema);
}
