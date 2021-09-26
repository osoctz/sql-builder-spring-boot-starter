package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.SqlbuilderModel;
import org.springframework.stereotype.Repository;

/**
 * 模型定义dao
 *
 * @author zantang
 */
@Repository
public interface SqlbuilderModelDao extends BaseDao<SqlbuilderModel, Long> {

}
