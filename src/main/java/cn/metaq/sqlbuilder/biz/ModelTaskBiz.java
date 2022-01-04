package cn.metaq.sqlbuilder.biz;

import cn.metaq.common.core.dto.Pagination;
import cn.metaq.data.Biz;
import cn.metaq.data.QueryBiz;
import cn.metaq.sqlbuilder.model.dto.ModelTaskDTO;
import cn.metaq.sqlbuilder.model.entity.ModelTaskExt;
import cn.metaq.sqlbuilder.model.qo.ModelTaskQo;
import org.springframework.data.jpa.domain.Specification;

/**
 * 模型任务
 *
 * @author zantang
 */
public interface ModelTaskBiz extends Biz<ModelTaskExt, Long>, QueryBiz<ModelTaskExt, Specification> {

  Pagination<ModelTaskDTO> list(ModelTaskQo queryDTO, int offset, int limit);

  void save(ModelTaskDTO task);
}
