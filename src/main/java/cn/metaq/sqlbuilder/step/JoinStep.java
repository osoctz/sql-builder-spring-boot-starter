package cn.metaq.sqlbuilder.step;

import cn.metaq.sqlbuilder.SqlbuilderStep;
import cn.metaq.sqlbuilder.constants.SqlbuilderStepType;
import cn.metaq.sqlbuilder.jackson.databind.SqlbuilderStepDeserializer;
import cn.metaq.sqlbuilder.model.ConditionOn;
import cn.metaq.sqlbuilder.model.CustomQuery;
import cn.metaq.sqlbuilder.model.JoinField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.Conditions;
import com.healthmarketscience.sqlbuilder.CustomSql;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import static cn.metaq.sqlbuilder.constants.SqlbuilderStepType.INNER_JOIN;

/**
 * {
 * "type": "left_join",
 * "join_fields": {
 * "left": [],
 * "right": []
 * },
 * "on": [],
 * "left": "table|distict|filter|intersect等",
 * "right": "table|distict|filter|intersect等"
 * }
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinStep extends AbstractStep {

    /**
     * 类型
     */
    private SqlbuilderStepType type = INNER_JOIN;

    /**
     * 关联字段
     */
    private JoinField join_fields;

    /**
     * on 条件
     */
    private List<ConditionOn> on;

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

    /**
     * {
     * "type": "LEFT_JOIN",
     * "join_fields": {
     * "left": ["id","name"],
     * "right": ["address","email"]
     * },
     * "on": [
     * {
     * "left": "id",
     * "right": "id"
     * }
     * ],
     * "left": {
     * "type": "TABLE",
     * "fields": ["id","name"]
     * },
     * "right": {
     * "type": "TABLE",
     * "fields": ["id","email","address"]
     * },
     * "alias": "v1"
     * }
     *
     * @param spec
     * @param schema
     * @return
     */
    @Override
    public CustomQuery build(DbSpec spec, DbSchema schema) {

        SelectQuery sq = new SelectQuery();
        if (SqlbuilderStepType.TABLE.equals(left.getType())) {

            TableStep leftStep = (TableStep) left;
            DbTable ldt = schema.addTable(leftStep.getTable_name());
            leftStep.getFields().forEach(s -> ldt.addColumn(s));
            //select字段
            join_fields.getLeft().forEach(s -> sq.addColumns(ldt.findColumn(s)));

            if (SqlbuilderStepType.TABLE.equals(right.getType())) {

                TableStep rightStep = (TableStep) right;
                DbTable rdt = schema.addTable(rightStep.getTable_name());
                rightStep.getFields().forEach(s -> rdt.addColumn(s));
                join_fields.getRight().forEach(s -> sq.addColumns(rdt.findColumn(s)));
                //join
                sq.addJoin(getJoinType(), ldt, rdt,
                        on.stream().map(l -> ldt.findColumn(l.getLeft())).collect(Collectors.toList()),
                        on.stream().map(r -> rdt.findColumn(r.getRight())).collect(Collectors.toList()));
                //condition
            } else {
                CustomQuery csq = right.build(spec, schema);

                join_fields.getRight().forEach(s -> sq.addCustomColumns(new CustomSql(appendAliasPrefix(csq.getAlias(), s))));

                if (on != null && !on.isEmpty()) {

                    sq.addCustomJoin(getJoinType(), ldt
                            , csq.toCustomSqlObject(),
                            Conditions.and(on.stream().map(o -> BinaryCondition.equalTo(ldt.findColumn(o.getLeft()),
                                    new CustomSql(appendAliasPrefix(csq.getAlias(), o.getRight())))).toArray()));
                }
            }
        } else {

            CustomQuery csq = left.build(spec, schema);
            join_fields.getLeft().forEach(s -> sq.addCustomColumns(new CustomSql(appendAliasPrefix(csq.getAlias(), s))));

            if (SqlbuilderStepType.TABLE.equals(right.getType())) {

                DbTable rdt = schema.addTable(((TableStep) right).getTable_name());
                ((TableStep) right).getFields().forEach(s -> rdt.addColumn(s));
                join_fields.getRight().forEach(s -> sq.addColumns(rdt.findColumn(s)));

                if (on != null && !on.isEmpty()) {

                    sq.addCustomJoin(getJoinType(), csq.toCustomSqlObject(), rdt,
                            Conditions.and(on.stream().map(o ->
                                    BinaryCondition.equalTo(new CustomSql(appendAliasPrefix(csq.getAlias(), o.getLeft())), rdt.findColumn(o.getRight())))
                                    .toArray()));
                }
            } else {

                CustomQuery lcsq = right.build(spec, schema);
                join_fields.getRight().forEach(s -> sq.addCustomColumns(new CustomSql(appendAliasPrefix(lcsq.getAlias(), s))));

                if (on != null && !on.isEmpty()) {

                    sq.addCustomJoin(getJoinType(), csq.toCustomSqlObject(), lcsq.toCustomSqlObject(),
                            Conditions.and(on.stream().map(o ->
                                    BinaryCondition.equalTo(new CustomSql(appendAliasPrefix(csq.getAlias(), o.getLeft())),
                                            new CustomSql(appendAliasPrefix(lcsq.getAlias(), o.getLeft()))))
                                    .toArray()));
                }
            }
        }

        return CustomQuery.builder()
                .query(sq)
                .alias(alias)
                .build();
    }

}
