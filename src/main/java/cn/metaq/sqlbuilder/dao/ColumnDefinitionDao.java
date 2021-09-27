package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.ColumnDefinition;
import org.springframework.stereotype.Repository;

/**
 * <p>Date: Mon Sep 27 15:59:40 CST 2021.</p>
 * <p>表字段.</p>
 *
 * @author tom
 */
@Repository
public interface ColumnDefinitionDao extends BaseDao<ColumnDefinition, Long>{
}
