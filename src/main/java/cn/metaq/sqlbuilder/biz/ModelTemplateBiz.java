package cn.metaq.sqlbuilder.biz;

import cn.metaq.common.core.dto.Pagination;
import cn.metaq.data.Biz;
import cn.metaq.data.QueryBiz;
import cn.metaq.sqlbuilder.model.ModelTemplate;
import cn.metaq.sqlbuilder.qo.ModelTemplateQo;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/**
 * Date: Mon Sep 27 16:10:29 CST 2021.
 *
 * <p>模型定义模版表.
 *
 * @author tom
 */
public interface ModelTemplateBiz extends Biz<ModelTemplate, Long>, QueryBiz<ModelTemplate, Specification> {

  <R> Pagination<R> list(Class<R> var1, ModelTemplateQo modelTemplateQo, int offset, int limit);

  <R> List<R> list(Class<R> var1, ModelTemplateQo modelTemplateQo);
}
