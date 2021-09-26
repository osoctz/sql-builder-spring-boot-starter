package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.biz.SqlbuilderModelTaskRecordBiz;
import cn.metaq.sqlbuilder.dao.SqlbuilderModelTaskRecordDao;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTask;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTaskRecord;
import cn.metaq.sqlbuilder.service.JdbcSqlExecutor;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqlbuilderModelTaskRecordBizImpl extends
    BaseBiz<SqlbuilderModelTaskRecord, Long, SqlbuilderModelTaskRecordDao> implements
    SqlbuilderModelTaskRecordBiz {

  private static final String VIEW_PREFIX = "record_";

  @Resource
  private JdbcSqlExecutor executor;

  @Resource
  private MongoTemplate mongoTemplate;

  @Transactional
  @Override
  public SqlbuilderModelTaskRecord execute(SqlbuilderModelTask task) {

    List<Map<String, Object>> data = executor.execute(task.getImprovement());

    SqlbuilderModelTaskRecord record = new SqlbuilderModelTaskRecord();
    record.setTid(task.getId());
    record.setExecute(task.getImprovement());

    dao.save(record);

    record.setCollection(VIEW_PREFIX + record.getId());
    mongoTemplate.insert(data, record.getCollection());

    return record;
  }

  @Override
  public Specification map(SqlbuilderModelTaskRecord sqlbuilderModelTaskRecord) {
    return null;
  }
}
