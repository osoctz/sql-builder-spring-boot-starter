package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.model.ColumnDefinition;
import cn.metaq.sqlbuilder.biz.ColumnDefinitionBiz;
import cn.metaq.sqlbuilder.dao.ColumnDefinitionDao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * <p>Date: Mon Sep 27 16:57:14 CST 2021.</p>
 * <p>表字段.</p>
 *
 * @author tom
 */
@Service
public class ColumnDefinitionBizImpl extends BaseBiz<ColumnDefinition, Long,ColumnDefinitionDao> implements ColumnDefinitionBiz{

  @Override
  public Specification map(ColumnDefinition propName) {
    return null;
  }
}
