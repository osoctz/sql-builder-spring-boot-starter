package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.biz.ModelBiz;
import cn.metaq.sqlbuilder.dao.ModelDao;
import cn.metaq.sqlbuilder.model.entity.Model;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 模型定义biz接口实现
 *
 * @author zantang
 */
@Service
public class ModelBizImpl extends BaseBiz<Model,Model, Long, ModelDao> implements ModelBiz {

  @Override
  public Specification map(Model model) {
    return null;
  }
}
