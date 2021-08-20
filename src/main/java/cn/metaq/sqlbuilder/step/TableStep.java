package cn.metaq.sqlbuilder.step;

import cn.metaq.sqlbuilder.SqlBuilderStep;
import cn.metaq.sqlbuilder.constants.SqlBuilderStepType;
import cn.metaq.sqlbuilder.model.CustomQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class TableStep implements SqlBuilderStep {

    /**
     * 类型
     */
    private SqlBuilderStepType type = SqlBuilderStepType.TABLE;

    private String table_name;

    /**
     * 字段
     */
    public Set<String> fields;

    @Override
    public CustomQuery build(DbSpec spec, DbSchema schema) {
        return null;
    }
}
