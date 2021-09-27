package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.model.TableDefinition;
import cn.metaq.sqlbuilder.biz.TableDefinitionBiz;
import cn.metaq.sqlbuilder.dao.TableDefinitionDao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * <p>Date: Mon Sep 27 16:57:14 CST 2021.</p>
 * <p>表.</p>
 *
 * @author tom
 */
@Service
public class TableDefinitionBizImpl extends BaseBiz<TableDefinition, Long,TableDefinitionDao> implements TableDefinitionBiz{

  @Override
  public Specification map(TableDefinition propName) {
    return null;
  }
}
