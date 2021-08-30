package cn.metaq.sqlbuilder.step;

import cn.metaq.sqlbuilder.constants.SqlBuilderStepType;
import cn.metaq.sqlbuilder.model.CustomQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class TableStep extends AbstractStep {


    /**
     * 类型
     */
    private SqlBuilderStepType type = SqlBuilderStepType.TABLE;

    private String table_name;

    /**
     * 字段
     */
    public Set<String> fields;

    private String alisa;

    @Override
    public CustomQuery build(DbSpec spec, DbSchema schema) {

        SelectQuery sq = new SelectQuery();
        DbTable sdt = schema.addTable(table_name);
        fields.forEach(s -> sdt.addColumn(s));

        sq.addFromTable(sdt);
        return CustomQuery.builder()
                .query(sq)
                .alias(null)
                .build();
    }

}
