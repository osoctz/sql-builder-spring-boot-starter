package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.biz.SqlbuilderModelBiz;
import cn.metaq.sqlbuilder.dao.SqlbuilderModelDao;
import cn.metaq.sqlbuilder.model.SqlbuilderModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 模型定义biz接口实现
 *
 * @author zantang
 */
@Service
public class SqlbuilderModelBizImpl extends
    BaseBiz<SqlbuilderModel, Long, SqlbuilderModelDao> implements
    SqlbuilderModelBiz {

  @Override
  public Specification map(SqlbuilderModel sqlbuilderModel) {
    return null;
  }
}
