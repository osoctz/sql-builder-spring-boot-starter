package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.model.ModelTemplate;
import cn.metaq.sqlbuilder.biz.ModelTemplateBiz;
import cn.metaq.sqlbuilder.dao.ModelTemplateDao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * <p>Date: Mon Sep 27 16:56:20 CST 2021.</p>
 * <p>模型定义模版表.</p>
 *
 * @author tom
 */
@Service
public class ModelTemplateBizImpl extends BaseBiz<ModelTemplate, Long,ModelTemplateDao> implements ModelTemplateBiz{

  @Override
  public Specification map(ModelTemplate propName) {
    return null;
  }
}
