package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.sqlbuilder.model.ResourceDir;
import cn.metaq.sqlbuilder.biz.ResourceDirBiz;
import cn.metaq.sqlbuilder.dao.ResourceDirDao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * <p>Date: Mon Sep 27 16:57:14 CST 2021.</p>
 * <p>资源目录.</p>
 *
 * @author tom
 */
@Service
public class ResourceDirBizImpl extends BaseBiz<ResourceDir, Long,ResourceDirDao> implements ResourceDirBiz{

  @Override
  public Specification map(ResourceDir propName) {
    return null;
  }
}
