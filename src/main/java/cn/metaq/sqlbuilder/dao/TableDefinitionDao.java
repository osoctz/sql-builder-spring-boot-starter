package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.TableDefinition;
import org.springframework.stereotype.Repository;

/**
 * <p>Date: Mon Sep 27 16:00:19 CST 2021.</p>
 * <p>表.</p>
 *
 * @author tom
 */
@Repository
public interface TableDefinitionDao extends BaseDao<TableDefinition, Long>{
}
