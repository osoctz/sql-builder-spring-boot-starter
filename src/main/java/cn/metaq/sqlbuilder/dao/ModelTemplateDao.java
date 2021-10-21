package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.ModelTemplate;
import org.springframework.stereotype.Repository;

/**
 * Date: Mon Sep 27 15:59:29 CST 2021.
 *
 * <p>模型定义模版表.
 *
 * @author tom
 */
@Repository
public interface ModelTemplateDao extends BaseDao<ModelTemplate, Long> {

}
