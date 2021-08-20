package cn.metaq.sqlbuilder.step;

import cn.metaq.sqlbuilder.SqlBuilderStep;
import cn.metaq.sqlbuilder.constants.SqlBuilderStepType;
import cn.metaq.sqlbuilder.model.CustomQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;

public class AbstractStep implements SqlBuilderStep {

    @Override
    public SqlBuilderStepType getType() {
        return null;
    }

    @Override
    public void setType(SqlBuilderStepType type) {

    }

    protected SelectQuery.JoinType getJoinType() {

        SelectQuery.JoinType joinType;
        switch (getType()) {
            case LEFT_JOIN:
                joinType = SelectQuery.JoinType.LEFT_OUTER;
                break;
            case RIGHT_JOIN:
                joinType = SelectQuery.JoinType.RIGHT_OUTER;
                break;
            default:
                joinType = SelectQuery.JoinType.INNER;
        }

        return joinType;
    }

    protected String appendAliasPrefix(String alias, String columnName) {

        StringBuilder builder = new StringBuilder();
        return builder.append(alias)
                .append('.')
                .append(columnName)
                .toString();
    }

    @Override
    public CustomQuery build(DbSpec spec, DbSchema schema) {
        return null;
    }
}
