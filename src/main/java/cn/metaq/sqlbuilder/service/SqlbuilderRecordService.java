package cn.metaq.sqlbuilder.service;

import cn.metaq.data.Biz;
import cn.metaq.sqlbuilder.model.SqlbuilderRecord;

import java.util.List;
import java.util.Map;

public interface SqlbuilderRecordService extends Biz<SqlbuilderRecord,String> {

    void save(SqlbuilderRecord object, List<Map<String, Object>> data);
}
