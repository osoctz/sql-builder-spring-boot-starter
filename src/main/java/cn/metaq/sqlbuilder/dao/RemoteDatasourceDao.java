package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.RemoteDatasource;
import org.springframework.stereotype.Repository;

/**
 * <p>Date: Mon Sep 27 16:00:01 CST 2021.</p>
 * <p>远程数据源.</p>
 *
 * @author tom
 */
@Repository
public interface RemoteDatasourceDao extends BaseDao<RemoteDatasource, Long>{
}
