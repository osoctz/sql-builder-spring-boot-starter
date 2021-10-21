package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.Task;
import org.springframework.stereotype.Repository;

/**
 * Date: Fri Oct 01 01:43:48 CST 2021.
 *
 * <p>任务基本信息表.
 *
 * @author tom
 */
@Repository
public interface TaskDao extends BaseDao<Task, Long> {

}
