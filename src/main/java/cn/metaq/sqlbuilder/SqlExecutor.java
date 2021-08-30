package cn.metaq.sqlbuilder;

import java.util.List;
import java.util.Map;

public interface SqlExecutor {

    /**
     * 需要做sql注入校验
     *
     * @param sql
     * @return
     */
    List<Map<String, Object>> execute(String sql);
}
