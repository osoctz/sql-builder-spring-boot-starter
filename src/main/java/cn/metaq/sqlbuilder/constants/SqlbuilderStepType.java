package cn.metaq.sqlbuilder.constants;

public enum SqlbuilderStepType {

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

    public static SqlbuilderStepType of(String value) {

        SqlbuilderStepType[] values = SqlbuilderStepType.values();
        for (SqlbuilderStepType v : values) {
            if (v.toString().equals(value)) {
                return v;
            }
        }

        return null;
    }
}
