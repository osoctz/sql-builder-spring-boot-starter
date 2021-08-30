package cn.metaq.sqlbuilder.step;

import cn.metaq.sqlbuilder.SqlbuilderStep;
import cn.metaq.sqlbuilder.constants.SqlbuilderStepType;
import cn.metaq.sqlbuilder.jackson.databind.SqlbuilderStepDeserializer;
import cn.metaq.sqlbuilder.model.ConditionOn;
import cn.metaq.sqlbuilder.model.CustomQuery;
import cn.metaq.sqlbuilder.model.JoinField;
import cn.metaq.sqlbuilder.model.UnionField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static cn.metaq.sqlbuilder.constants.SqlbuilderStepType.INNER_JOIN;

/**
 * {
 * "type": "except",
 * "union_fields": {
 * "left": [],
 * "right": []
 * },
 * "on": [],
 * "left": {},
 * "right": {}
 * }
 */
@Getter
@Setter
public class ExceptStep extends AbstractStep {

    /**
     * 类型
     */
    private SqlbuilderStepType type = SqlbuilderStepType.EXCEPT;

    /**
     * 关联字段
     */
    private UnionField union_fields;

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

    @Override
    public CustomQuery build(DbSpec spec, DbSchema schema) {
        //union集合减去inner集合
        UnionStep unionStep = new UnionStep();
        unionStep.setUnion_fields(union_fields);
        unionStep.setLeft(left);
        unionStep.setRight(right);
        unionStep.setAlias("_u0");

        JoinStep joinStep = new JoinStep();
        joinStep.setType(INNER_JOIN);
        JoinField joinField = new JoinField();
        joinField.setLeft(union_fields.getLeft());

        joinStep.setJoin_fields(joinField);
        joinStep.setLeft(left);
        joinStep.setRight(right);
        joinStep.setAlias("_j0");
        joinStep.setOn(on);

        CustomQuery ucq = unionStep.build(spec, schema);
        CustomQuery jcq = joinStep.build(spec, schema);

        SelectQuery sq = new SelectQuery();

        union_fields.getLeft().forEach(s -> sq.addCustomColumns(new CustomSql(appendAliasPrefix("_u0", s))));
        sq.addCustomFromTable(ucq.toCustomSqlObject());

        SelectQuery jsq = new SelectQuery();
        union_fields.getLeft().forEach(s -> jsq.addCustomColumns(new CustomSql(appendAliasPrefix("_j0", s))));

        on.forEach(o -> {
            jsq.addCondition(BinaryCondition.equalTo(new CustomSql(appendAliasPrefix(jcq.getAlias(), o.getLeft())),
                    new CustomSql(appendAliasPrefix(ucq.getAlias(), o.getLeft()))));
        });
        jsq.addCustomFromTable(jcq.toCustomSqlObject());

        sq.addCondition(new NotCondition(Conditions.exists(jsq)));

        return CustomQuery.builder()
                .alias(alias)
                .query(sq)
                .build();
    }
}
