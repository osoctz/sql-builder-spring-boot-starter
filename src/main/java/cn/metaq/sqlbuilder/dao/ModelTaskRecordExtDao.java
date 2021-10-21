package cn.metaq.sqlbuilder.dao;

import cn.metaq.data.jpa.BaseDao;
import cn.metaq.sqlbuilder.model.ModelTaskRecordExt;
import org.springframework.stereotype.Repository;

/**
 * Date: Fri Oct 01 01:43:48 CST 2021.
 *
 * <p>模型任务运行记录扩展表.
 *
 * @author tom
 */
@Repository
public interface ModelTaskRecordExtDao extends BaseDao<ModelTaskRecordExt, Long> {

}
