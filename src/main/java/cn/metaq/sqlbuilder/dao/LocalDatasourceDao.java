package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.LocalDatasource;
import org.springframework.stereotype.Repository;

/**
 * <p>Date: Mon Sep 27 15:59:51 CST 2021.</p>
 * <p>本地数据源.</p>
 *
 * @author tom
 */
@Repository
public interface LocalDatasourceDao extends BaseDao<LocalDatasource, Long>{
}
