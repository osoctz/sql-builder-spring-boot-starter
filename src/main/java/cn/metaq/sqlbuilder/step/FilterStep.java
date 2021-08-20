package cn.metaq.sqlbuilder.step;

import cn.metaq.sqlbuilder.SqlBuilderStep;
import cn.metaq.sqlbuilder.constants.SqlBuilderStepType;
import cn.metaq.sqlbuilder.jackson.databind.SqlBuilderStepDeserializer;
import cn.metaq.sqlbuilder.model.ConditionChain;
import cn.metaq.sqlbuilder.model.CustomQuery;
import cn.metaq.sqlbuilder.util.StringUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * {
 * "type": "filter",
 * "select_fields":[],
 * "condition":{},
 * "source":{}
 * }
 */
@Getter
@Setter
public class FilterStep extends AbstractStep {

    /**
     * 类型
     */
    private SqlBuilderStepType type = SqlBuilderStepType.FILTER;

    /**
     * select字段
     */
    private List<String> select_fields;

    /**
     * where 条件
     */
    private ConditionChain condition;

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

            String tableName = ((TableStep) source).getTable_name();
            Set<String> fields = ((TableStep) source).getFields();

            DbTable sdt = schema.addTable(tableName);
            fields.forEach(s -> sdt.addColumn(s));

            select_fields.forEach(s -> sq.addColumns(sdt.findColumn(s)));

            switch (condition.getType()) {
                case OR:
                    sq.addCondition(Conditions.or(condition.getConditions().stream()
                            .map(s -> getCondition(s, sdt)).toArray()));
                    break;
                default:
                    sq.addCondition(Conditions.and(condition.getConditions().stream()
                            .map(s -> getCondition(s, sdt)).toArray()));
            }

        } else {

            CustomQuery csq = source.build(spec, schema);

            select_fields.forEach(s -> {
                sq.addCustomColumns(new CustomSql(appendAliasPrefix(csq.getAlias(), s)));
            });

            sq.addCustomFromTable(csq.toCustomSqlObject());

            switch (condition.getType()) {
                case OR:
                    sq.addCondition(Conditions.or(condition.getConditions().stream()
                            .map(s -> getCondition(s, csq)).toArray()));
                    break;
                default:
                    sq.addCondition(Conditions.and(condition.getConditions().stream()
                            .map(s -> getCondition(s, csq)).toArray()));
            }
        }

        return CustomQuery.builder()
                .query(sq)
                .alias(alias)
                .build();
    }

    public Condition getCondition(cn.metaq.sqlbuilder.model.Condition s, DbTable sdt) {

        Condition c;

        switch (s.getType()) {
            case LIKE:
                c = BinaryCondition.like(sdt.findColumn(s.getName()), StringUtils.wrap((String) s.getValue(), "%", "%"));
                break;
            case GT:
                c = BinaryCondition.greaterThan(sdt.findColumn(s.getName()), s.getValue());
                break;
            default:
                c = BinaryCondition.equalTo(sdt.findColumn(s.getName()), s.getValue());
        }
        return c;
    }

    public Condition getCondition(cn.metaq.sqlbuilder.model.Condition s, CustomQuery csq) {

        Condition c;

        switch (s.getType()) {
            case LIKE:
                c = BinaryCondition.like(new CustomSql(appendAliasPrefix(csq.getAlias(), s.getName())), StringUtils.wrap((String) s.getValue(), "%", "%"));
                break;
            case GT:
                c = BinaryCondition.greaterThan(new CustomSql(appendAliasPrefix(csq.getAlias(), s.getName())), s.getValue());
                break;
            default:
                c = BinaryCondition.equalTo(new CustomSql(appendAliasPrefix(csq.getAlias(), s.getName())), s.getValue());
        }
        return c;
    }
}
