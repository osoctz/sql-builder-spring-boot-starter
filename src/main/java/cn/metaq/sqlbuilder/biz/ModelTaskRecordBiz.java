package cn.metaq.sqlbuilder.biz;

import cn.metaq.common.core.dto.Pagination;
import cn.metaq.data.Biz;
import cn.metaq.sqlbuilder.model.dto.ModelTaskRecordViewDTO;
import cn.metaq.sqlbuilder.model.entity.ModelTaskRecordExt;
import cn.metaq.sqlbuilder.model.qo.ModelTaskRecordQo;

/**
 * 模型任务记录
 *
 * @author zantang
 */
public interface ModelTaskRecordBiz extends Biz<ModelTaskRecordExt, Long> {

  /**
   * 结果视图分页查询
   *
   * @param modelTaskRecordQo
   * @param offset
   * @param limit
   * @return
   */
  Pagination<ModelTaskRecordViewDTO> list(ModelTaskRecordQo modelTaskRecordQo, int offset, int limit);
}
