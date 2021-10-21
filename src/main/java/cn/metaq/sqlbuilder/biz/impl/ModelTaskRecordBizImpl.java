package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.common.core.dto.Pagination;
import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.data.jpa.BaseTemplate;
import cn.metaq.sqlbuilder.biz.ModelTaskRecordBiz;
import cn.metaq.sqlbuilder.dao.ModelTaskRecordExtDao;
import cn.metaq.sqlbuilder.dto.ModelTaskRecordViewDTO;
import cn.metaq.sqlbuilder.model.ModelTaskRecordExt;
import cn.metaq.sqlbuilder.qo.ModelTaskRecordQo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 模型任务
 *
 * @author zantang
 */
@Service
public class ModelTaskRecordBizImpl extends BaseBiz<ModelTaskRecordExt, Long, ModelTaskRecordExtDao> implements
    ModelTaskRecordBiz {

  @Resource
  private BaseTemplate baseTemplate;

  @Override
  public Pagination<ModelTaskRecordViewDTO> list(ModelTaskRecordQo modelTaskRecordQo, int offset, int limit) {

    String jql =
        "select new ai.bailian.sqlbuilder.model.dto.ModelTaskRecordViewDTO(b.id," + "c.mid," + "d.name," + "b.status,"
            + "a.collection) " + "from ModelTaskRecordExt a " + "left join TaskRecord b on a.id=b.id "
            + "left join ModelTaskExt c on b.tid=c.id " + "left join Task d on b.tid=d.id where 1=1 ";

    Map<String, Object> params = new HashMap<>(3);

    jql = buildQueryString(modelTaskRecordQo, jql, params);

    List<ModelTaskRecordViewDTO> list = baseTemplate.list(ModelTaskRecordViewDTO.class, jql, params, offset, limit);

    Pagination pagination = new Pagination();
    pagination.setOffset(offset);
    pagination.setLimit(limit);
    pagination.setTotal(Math.toIntExact(this.count(modelTaskRecordQo)));
    pagination.setData(list);
    return pagination;
  }

  public Long count(ModelTaskRecordQo modelTaskRecordQo) {

    String jql = "select count(1) " + "from ModelTaskRecordExt a " + "left join TaskRecord b on a.id=b.id "
        + "left join ModelTaskExt c on b.tid=c.id " + "left join Task d on b.tid=d.id where 1=1 ";

    Map<String, Object> params = new HashMap<>(3);
    jql = buildQueryString(modelTaskRecordQo, jql, params);

    return baseTemplate.count(jql, params);
  }

  private String buildQueryString(ModelTaskRecordQo modelTaskRecordQo, String jql, Map<String, Object> params) {
    if (modelTaskRecordQo.getMid() != null) {
      jql += " and c.mid=:mid";
      params.put("mid", modelTaskRecordQo.getMid());
    }

    if (modelTaskRecordQo.getStatus() != null) {
      jql += " and b.status=:status";
      params.put("status", modelTaskRecordQo.getStatus());
    }

    if (modelTaskRecordQo.getName() != null && modelTaskRecordQo.getName().length() > 0) {
      jql += " and d.name like :name";
      params.put("name", "%" + modelTaskRecordQo.getName() + "%");
    }
    return jql;
  }

  @Override
  public Specification map(ModelTaskRecordExt modelTaskRecord) {
    return null;
  }
}
