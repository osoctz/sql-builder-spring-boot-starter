package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.entity.TaskRecord;
import org.springframework.stereotype.Repository;

/**
 * Date: Fri Oct 01 01:43:48 CST 2021.
 *
 * <p>任务运行记录表.
 *
 * @author tom
 */
@Repository
public interface TaskRecordDao extends BaseDao<TaskRecord, Long> {

}
