package cn.metaq.sqlbuilder.step;

import cn.metaq.sqlbuilder.SqlBuilderStep;
import cn.metaq.sqlbuilder.SqlExecutor;
import cn.metaq.sqlbuilder.constants.SqlBuilderStepType;
import cn.metaq.sqlbuilder.model.CustomQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;

import java.util.List;
import java.util.Map;

public class AbstractStep implements SqlBuilderStep {

    protected SqlExecutor executor;

    @Override
    public SqlBuilderStepType getType() {
        return null;
    }

    @Override
    public void setType(SqlBuilderStepType type) {

    }

    public SqlBuilderStep exec(SqlExecutor executor) {
        this.executor = executor;
        return this;
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

    @Override
    public List<Map<String, Object>> preview(String sql) {
//
//        System.out.println("sql = " + sql);

        return executor.execute(sql);
    }
}
