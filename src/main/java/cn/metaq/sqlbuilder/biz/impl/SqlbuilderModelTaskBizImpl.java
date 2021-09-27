package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.biz.SqlbuilderModelTaskBiz;
import cn.metaq.sqlbuilder.dao.SqlbuilderModelTaskDao;
import cn.metaq.sqlbuilder.dao.SqlbuilderModelTaskRecordDao;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTask;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTaskRecord;
import cn.metaq.sqlbuilder.service.JdbcSqlExecutor;
import javax.annotation.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqlbuilderModelTaskBizImpl extends
    BaseBiz<SqlbuilderModelTask, Long, SqlbuilderModelTaskDao> implements
    SqlbuilderModelTaskBiz {

  private static final String VIEW_PREFIX = "record_";

  @Resource
  private JdbcSqlExecutor executor;
  @Resource
  private MongoTemplate mongoTemplate;
  @Resource
  private SqlbuilderModelTaskRecordDao recordDao;

  @Override
  public SqlbuilderModelTaskRecord execute(SqlbuilderModelTask task) {

    task= dao.save(task);

    SqlbuilderModelTaskRecord record = new SqlbuilderModelTaskRecord();
    record.setTid(task.getId());
    record.setExecute(task.getImprovement());

    recordDao.save(record);

    record.setCollection(VIEW_PREFIX + record.getId());
    mongoTemplate.insert(executor.execute(task.getImprovement()), record.getCollection());
    return record;
  }

  @Override
  public Specification map(SqlbuilderModelTask sqlbuilderModelTask) {
    return null;
  }
}
