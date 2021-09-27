package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.biz.ModelTaskRecordBiz;
import cn.metaq.sqlbuilder.dao.ModelTaskRecordDao;
import cn.metaq.sqlbuilder.model.ModelTask;
import cn.metaq.sqlbuilder.model.ModelTaskRecord;
import cn.metaq.sqlbuilder.service.JdbcSqlExecutor;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class ModelModelTaskRecordBizImpl extends
    BaseBiz<ModelTaskRecord, Long, ModelTaskRecordDao> implements
    ModelTaskRecordBiz {

  private static final String VIEW_PREFIX = "record_";

  @Resource
  private JdbcSqlExecutor executor;

  @Resource
  private MongoTemplate mongoTemplate;

  @Transactional
  @Override
  public ModelTaskRecord execute(ModelTask task) {

    List<Map<String, Object>> data = executor.execute(task.getImprovement());

    ModelTaskRecord record = new ModelTaskRecord();
    record.setTid(task.getId());
    record.setExecute(task.getImprovement());

    dao.save(record);

    record.setCollection(VIEW_PREFIX + record.getId());
    mongoTemplate.insert(data, record.getCollection());

    return record;
  }

  @Override
  public Specification map(ModelTaskRecord modelTaskRecord) {
    return null;
  }
}
