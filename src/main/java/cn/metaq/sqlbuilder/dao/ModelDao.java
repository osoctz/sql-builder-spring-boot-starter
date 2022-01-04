package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.entity.Model;
import org.springframework.stereotype.Repository;

/**
 * 模型定义dao
 *
 * @author zantang
 */
@Repository
public interface ModelDao extends BaseDao<Model, Long> {

}
