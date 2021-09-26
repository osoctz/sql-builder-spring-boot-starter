package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.biz.SqlbuilderModelTaskBiz;
import cn.metaq.sqlbuilder.dao.SqlbuilderModelTaskDao;
import cn.metaq.sqlbuilder.dao.SqlbuilderModelTaskRecordDao;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTask;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTaskRecord;
import cn.metaq.sqlbuilder.service.JdbcSqlExecutor;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqlbuilderModelTaskBizImpl extends
    BaseBiz<SqlbuilderModelTask, Long, SqlbuilderModelTaskDao> implements
    SqlbuilderModelTaskBiz {


  @Override
  public Specification map(SqlbuilderModelTask sqlbuilderModelTask) {
    return null;
  }
}
