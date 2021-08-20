package cn.metaq.sqlbuilder.util;

public class StringUtils {

    public static String wrap(String str, String prefix, String suffix) {

        StringBuilder builder = new StringBuilder();
        return builder.append(prefix)
                .append(str)
                .append(suffix)
                .toString();
    }

}
