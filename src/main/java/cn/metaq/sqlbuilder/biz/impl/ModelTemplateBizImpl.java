package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.common.core.dto.Pagination;
import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.biz.ModelTemplateBiz;
import cn.metaq.sqlbuilder.dao.ModelTemplateDao;
import cn.metaq.sqlbuilder.model.ModelTemplate;
import cn.metaq.sqlbuilder.qo.ModelTemplateQo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Date: Mon Sep 27 16:56:20 CST 2021.
 *
 * <p>模型定义模版表.
 *
 * @author tom
 */
@Service
public class ModelTemplateBizImpl extends BaseBiz<ModelTemplate, Long, ModelTemplateDao> implements ModelTemplateBiz {

  @Override
  public <R> Pagination<R> list(Class<R> var1, ModelTemplateQo modelTemplateQo, int offset, int limit) {

    Pageable pageable = PageRequest.of(offset / limit, limit);
    Page<R> page = this.dao.findAll(this.map(modelTemplateQo), pageable);

    Pagination pagination = new Pagination();
    pagination.setOffset(offset);
    pagination.setLimit(limit);
    pagination.setTotal((int) page.getTotalElements());
    pagination.setData(page.getContent());
    return pagination;
  }

  @Override
  public <R> List<R> list(Class<R> var1, ModelTemplateQo modelTemplateQo) {
    return this.dao.findAll(this.map(modelTemplateQo));
  }

  public Specification map(ModelTemplateQo qo) {

    return ((root, cq, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (null != qo.getMid()) {
        predicates.add(cb.equal(root.get("mid"), qo.getMid()));
      }
      if (null != qo.getName()) {
        predicates.add(cb.like(root.get("name"), "%" + qo.getName() + "%"));
      }

      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    });
  }

  @Override
  public Specification map(ModelTemplate modelTemplate) {
    return null;
  }
}
