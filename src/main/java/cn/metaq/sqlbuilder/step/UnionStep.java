package cn.metaq.sqlbuilder.step;

import cn.metaq.sqlbuilder.SqlbuilderStep;
import cn.metaq.sqlbuilder.constants.SqlbuilderStepType;
import cn.metaq.sqlbuilder.jackson.databind.SqlbuilderStepDeserializer;
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
    private SqlbuilderStepType type = SqlbuilderStepType.UNION;

    /**
     * union 字段
     */
    private UnionField union_fields;

    /**
     * 左集
     */
    @JsonDeserialize(using = SqlbuilderStepDeserializer.class)
    private SqlbuilderStep left;

    /**
     * 右集
     */
    @JsonDeserialize(using = SqlbuilderStepDeserializer.class)
    private SqlbuilderStep right;

    private String alias;

    @Override
    public CustomQuery build(DbSpec spec, DbSchema schema) {

        SelectQuery lsq = new SelectQuery();
        SelectQuery rsq = new SelectQuery();

        if (SqlbuilderStepType.TABLE.equals(left.getType())) {

            DbTable ldt = schema.addTable(((TableStep) left).getTable_name());
            ((TableStep) left).getFields().forEach(s -> ldt.addColumn(s));
            //select字段
            union_fields.getLeft().forEach(s -> lsq.addColumns(ldt.findColumn(s)));
            this.structRightJoin(spec, schema, rsq);
        }else{

            CustomQuery cq=left.build(spec,schema);
            union_fields.getLeft().forEach(s->lsq.addCustomColumns(new CustomSql(appendAliasPrefix(cq.getAlias(), s))));

            lsq.addCustomFromTable(cq.toCustomSqlObject());
            this.structRightJoin(spec, schema, rsq);
        }
        //union
        UnionQuery unionQuery = UnionQuery.union(lsq, rsq);

        return CustomQuery.builder()
                .query(unionQuery)
                .alias(alias)
                .build();
    }

    /**
     * 构造right_join
     * @param spec
     * @param schema
     * @param rsq
     */
    private void structRightJoin(DbSpec spec, DbSchema schema, SelectQuery rsq) {
        if (SqlbuilderStepType.TABLE.equals(right.getType())) {

            DbTable rdt = schema.addTable(((TableStep) right).getTable_name());
            ((TableStep) right).getFields().forEach(s -> rdt.addColumn(s));
            union_fields.getRight().forEach(s -> rsq.addColumns(rdt.findColumn(s)));

        } else {

            CustomQuery rcq = right.build(spec, schema);
            union_fields.getRight().forEach(s -> rsq.addCustomColumns(new CustomSql(appendAliasPrefix(rcq.getAlias(), s))));

            rsq.addCustomFromTable(rcq.toCustomSqlObject());
        }
    }
}
