package cn.metaq.sqlbuilder.step;

import cn.metaq.sqlbuilder.SqlBuilderStep;
import cn.metaq.sqlbuilder.constants.SqlBuilderStepType;
import cn.metaq.sqlbuilder.jackson.databind.SqlBuilderStepDeserializer;
import cn.metaq.sqlbuilder.model.CustomQuery;
import cn.metaq.sqlbuilder.model.UnionField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.Getter;
import lombok.Setter;

/**
 * {
 * "type": "union",
 * "union_fields": {
 * "left": [],
 * "right": []
 * },
 * "left": {},
 * "right": {}
 * }
 */
@Setter
@Getter
public class UnionStep extends AbstractStep {

    /**
     * 类型
     */
    private SqlBuilderStepType type = SqlBuilderStepType.UNION;

    /**
     * union 字段
     */
    private UnionField union_fields;

    /**
     * 左集
     */
    @JsonDeserialize(using = SqlBuilderStepDeserializer.class)
    private SqlBuilderStep left;

    /**
     * 右集
     */
    @JsonDeserialize(using = SqlBuilderStepDeserializer.class)
    private SqlBuilderStep right;

    private String alias;

    @Override
    public CustomQuery build(DbSpec spec, DbSchema schema) {

        SelectQuery lsq = new SelectQuery();
        SelectQuery rsq = new SelectQuery();

        if (SqlBuilderStepType.TABLE.equals(left.getType())) {

            DbTable ldt = schema.addTable(((TableStep) left).getTable_name());
            ((TableStep) left).getFields().forEach(s -> ldt.addColumn(s));
            //select字段
            union_fields.getLeft().forEach(s -> lsq.addColumns(ldt.findColumn(s)));

            if (SqlBuilderStepType.TABLE.equals(right.getType())) {

                DbTable rdt = schema.addTable(((TableStep) right).getTable_name());
                ((TableStep) right).getFields().forEach(s -> rdt.addColumn(s));
                union_fields.getRight().forEach(s -> rsq.addColumns(rdt.findColumn(s)));

            } else {

                CustomQuery csq = right.build(spec, schema);
                union_fields.getRight().forEach(s -> rsq.addCustomColumns(new CustomSql(appendAliasPrefix(csq.getAlias(), s))));

                rsq.addCustomFromTable(csq.toCustomSqlObject());
            }
        }else{

            CustomQuery cq=left.build(spec,schema);
            union_fields.getLeft().forEach(s->lsq.addCustomColumns(new CustomSql(appendAliasPrefix(cq.getAlias(), s))));

            lsq.addCustomFromTable(cq.toCustomSqlObject());

            if (SqlBuilderStepType.TABLE.equals(right.getType())) {

                DbTable rdt = schema.addTable(((TableStep) right).getTable_name());
                ((TableStep) right).getFields().forEach(s -> rdt.addColumn(s));
                union_fields.getRight().forEach(s -> rsq.addColumns(rdt.findColumn(s)));

            } else {

                CustomQuery rcq = right.build(spec, schema);
                union_fields.getRight().forEach(s -> rsq.addCustomColumns(new CustomSql(appendAliasPrefix(rcq.getAlias(), s))));

                rsq.addCustomFromTable(rcq.toCustomSqlObject());
            }
        }
        //union
        UnionQuery unionQuery = UnionQuery.union(lsq, rsq);

        return CustomQuery.builder()
                .query(unionQuery)
                .alias(alias)
                .build();
    }
}
