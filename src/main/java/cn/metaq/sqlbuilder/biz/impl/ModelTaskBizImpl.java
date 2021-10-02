package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.biz.ModelTaskBiz;
import cn.metaq.sqlbuilder.dao.ModelTaskDao;
import cn.metaq.sqlbuilder.dao.ModelTaskRecordDao;
import cn.metaq.sqlbuilder.model.ModelTask;
import cn.metaq.sqlbuilder.model.ModelTaskRecord;
import cn.metaq.sqlbuilder.service.JdbcSqlExecutor;
import javax.annotation.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModelTaskBizImpl extends
    BaseBiz<ModelTask, Long, ModelTaskDao> implements
    ModelTaskBiz {

  private static final String VIEW_PREFIX = "record_";

  @Resource
  private JdbcSqlExecutor executor;
  @Resource
  private MongoTemplate mongoTemplate;
  @Resource
  private ModelTaskRecordDao recordDao;

  @Transactional(rollbackFor = RuntimeException.class)
  @Override
  public ModelTaskRecord execute(ModelTask task) {

    task = dao.save(task);

    ModelTaskRecord record = new ModelTaskRecord();
    record.setTid(task.getId());
    record.setExecute(task.getImprovement());
    record.setColumns(task.getColumns());

    recordDao.save(record);

    record.setCollection(VIEW_PREFIX + record.getId());
    mongoTemplate.insert(executor.execute(task.getImprovement()), record.getCollection());
    return record;
  }

  @Override
  public Specification map(ModelTask modelTask) {
    return null;
  }
}
