package cn.metaq.sqlbuilder.step;

import cn.metaq.sqlbuilder.SqlBuilderStep;
import cn.metaq.sqlbuilder.constants.SqlBuilderStepType;
import cn.metaq.sqlbuilder.jackson.databind.SqlBuilderStepDeserializer;
import cn.metaq.sqlbuilder.model.CustomQuery;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.healthmarketscience.sqlbuilder.CustomSql;
import com.healthmarketscience.sqlbuilder.FunctionCall;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * {
 * "type": "group_concat",
 * "group_by_fields":[],
 * "group_concat_fields":[],
 * "source":{}
 * }
 */
@Setter
@Getter
public class GroupConcatStep extends AbstractStep {

    /**
     * 类型
     */
    private SqlBuilderStepType type = SqlBuilderStepType.GROUP_CONCAT;

    /**
     * group by字段
     */
    private List<String> group_by_fields;

    /**
     * group_concat字段
     */
    private List<String> group_concat_fields;

    /**
     * 前一个构建步骤
     */
    @JsonDeserialize(using = SqlBuilderStepDeserializer.class)
    private SqlBuilderStep source;

    private String alias;

    @Override
    public CustomQuery build(DbSpec spec, DbSchema schema) {

        SelectQuery sq = new SelectQuery();
        if (SqlBuilderStepType.TABLE.equals(source.getType())) {

            DbTable sdt = schema.addTable(((TableStep) source).getTable_name());
            ((TableStep) source).getFields().forEach(s -> sdt.addColumn(s));

            group_by_fields.forEach(s -> {
                sq.addColumns(sdt.findColumn(s));
                sq.addGroupings(sdt.findColumn(s));
            });

            group_concat_fields.forEach(s-> sq.addCustomColumns(new CustomSql("group_concat("+appendAliasPrefix(sdt.getAlias(),s)+")")));
            sq.addFromTable(sdt);

        } else {

            CustomQuery csq = source.build(spec, schema);
            group_by_fields.forEach(s -> {

                sq.addCustomColumns(appendAliasPrefix(csq.getAlias(), s));
                sq.addCustomGroupings(appendAliasPrefix(csq.getAlias(), s));
            });

            group_concat_fields.forEach(s-> sq.addCustomColumns(new CustomSql("group_concat("+appendAliasPrefix(csq.getAlias(), s)+")")));
            sq.addCustomFromTable(csq.toCustomSqlObject());
        }

        return CustomQuery.builder()
                .query(sq)
                .alias(alias)
                .build();
    }
}
