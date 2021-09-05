package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jdbc.BaseDao;
import cn.metaq.sqlbuilder.model.SqlbuilderRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlbuilderRecordDao extends BaseDao<SqlbuilderRecord,String> {
}
