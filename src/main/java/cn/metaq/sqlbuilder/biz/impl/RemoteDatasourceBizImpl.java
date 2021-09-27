package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.model.RemoteDatasource;
import cn.metaq.sqlbuilder.biz.RemoteDatasourceBiz;
import cn.metaq.sqlbuilder.dao.RemoteDatasourceDao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * <p>Date: Mon Sep 27 16:57:14 CST 2021.</p>
 * <p>远程数据源.</p>
 *
 * @author tom
 */
@Service
public class RemoteDatasourceBizImpl extends BaseBiz<RemoteDatasource, Long,RemoteDatasourceDao> implements RemoteDatasourceBiz{

  @Override
  public Specification map(RemoteDatasource propName) {
    return null;
  }
}
