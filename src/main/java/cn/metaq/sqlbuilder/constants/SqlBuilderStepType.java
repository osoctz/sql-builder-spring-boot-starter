package cn.metaq.sqlbuilder.constants;

public enum SqlBuilderStepType {

    TABLE,
    FILTER,
    DISTINCT,
    LEFT_JOIN,
    RIGHT_JOIN,
    INNER_JOIN,
    UNION,
    EXCEPT,
    COUNT,
    SUM,
    GROUP_CONCAT;

    public static SqlBuilderStepType of(String value) {

        SqlBuilderStepType[] values = SqlBuilderStepType.values();
        for (SqlBuilderStepType v : values) {
            if (v.toString().equals(value)) {
                return v;
            }
        }

        return null;
    }
}
