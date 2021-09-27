package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.ModelTask;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelTaskDao extends BaseDao<ModelTask,Long> {

}
