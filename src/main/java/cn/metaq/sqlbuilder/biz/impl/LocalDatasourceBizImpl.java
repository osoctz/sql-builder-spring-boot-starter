package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.model.LocalDatasource;
import cn.metaq.sqlbuilder.biz.LocalDatasourceBiz;
import cn.metaq.sqlbuilder.dao.LocalDatasourceDao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * <p>Date: Mon Sep 27 16:57:14 CST 2021.</p>
 * <p>本地数据源.</p>
 *
 * @author tom
 */
@Service
public class LocalDatasourceBizImpl extends BaseBiz<LocalDatasource, Long,LocalDatasourceDao> implements LocalDatasourceBiz{

  @Override
  public Specification map(LocalDatasource propName) {
    return null;
  }
}
