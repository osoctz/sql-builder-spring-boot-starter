package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.common.core.dto.Pagination;
import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.data.jpa.BaseTemplate;
import cn.metaq.sqlbuilder.biz.ModelTaskRecordBiz;
import cn.metaq.sqlbuilder.dao.ModelTaskRecordExtDao;
import cn.metaq.sqlbuilder.model.dto.ModelTaskRecordViewDTO;
import cn.metaq.sqlbuilder.model.entity.ModelTaskRecordExt;
import cn.metaq.sqlbuilder.model.entity.QModelTaskExt;
import cn.metaq.sqlbuilder.model.entity.QModelTaskRecordExt;
import cn.metaq.sqlbuilder.model.entity.QTask;
import cn.metaq.sqlbuilder.model.entity.QTaskRecord;
import cn.metaq.sqlbuilder.model.qo.ModelTaskRecordQo;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 模型任务
 *
 * @author zantang
 */
@Service
public class ModelTaskRecordBizImpl extends
    BaseBiz<ModelTaskRecordExt, ModelTaskRecordExt, Long, ModelTaskRecordExtDao> implements ModelTaskRecordBiz {
  @Autowired
  private JPAQueryFactory jqf;

  @Override
  public Pagination<ModelTaskRecordViewDTO> list(ModelTaskRecordQo modelTaskRecordQo, int offset, int limit) {

    QModelTaskRecordExt modelTaskRecordExt = QModelTaskRecordExt.modelTaskRecordExt;
    QTaskRecord taskRecord = QTaskRecord.taskRecord;
    QModelTaskExt modelTaskExt = QModelTaskExt.modelTaskExt;
    QTask task = QTask.task;

    List<ModelTaskRecordViewDTO> list = jqf.select(
            Projections.fields(ModelTaskRecordViewDTO.class, taskRecord.id, modelTaskExt.mid, task.name, taskRecord.status,
                modelTaskRecordExt.collection))
        .from(modelTaskRecordExt)
        .leftJoin(taskRecord)
        .on(modelTaskRecordExt.id.eq(taskRecord.id))
        .leftJoin(modelTaskExt)
        .on(taskRecord.tid.eq(modelTaskExt.id))
        .leftJoin(task)
        .on(taskRecord.tid.eq(task.id))
        .where(this.buildQuery(modelTaskRecordQo, modelTaskRecordExt, taskRecord, modelTaskExt, task)
            .toArray(Predicate[]::new))
        .fetch();

    Pagination pagination = new Pagination();
    pagination.setOffset(offset);
    pagination.setLimit(limit);
    pagination.setTotal(Math.toIntExact(this.count(modelTaskRecordQo)));
    pagination.setData(list);
    return pagination;
  }

  public Long count(ModelTaskRecordQo modelTaskRecordQo) {

    QModelTaskRecordExt modelTaskRecordExt = QModelTaskRecordExt.modelTaskRecordExt;
    QTaskRecord taskRecord = QTaskRecord.taskRecord;
    QModelTaskExt modelTaskExt = QModelTaskExt.modelTaskExt;
    QTask task = QTask.task;

    return jqf.selectOne()
        .from(modelTaskRecordExt)
        .leftJoin(taskRecord)
        .on(modelTaskRecordExt.id.eq(taskRecord.id))
        .leftJoin(modelTaskExt)
        .on(taskRecord.tid.eq(modelTaskExt.id))
        .leftJoin(task)
        .on(taskRecord.tid.eq(task.id))
        .where(this.buildQuery(modelTaskRecordQo, modelTaskRecordExt, taskRecord, modelTaskExt, task)
            .toArray(Predicate[]::new))
        .fetchCount();
  }

  private List<Predicate> buildQuery(ModelTaskRecordQo modelTaskRecordQo, QModelTaskRecordExt modelTaskRecordExt,
      QTaskRecord taskRecord, QModelTaskExt modelTaskExt, QTask task) {

    List<Predicate> predicates = new ArrayList<>();
    if (modelTaskRecordQo.getMid() != null) {
      predicates.add(modelTaskExt.mid.eq(modelTaskRecordQo.getMid()));
    }

    if (modelTaskRecordQo.getStatus() != null) {
      predicates.add(taskRecord.status.eq(modelTaskRecordQo.getStatus()));
    }

    if (modelTaskRecordQo.getName() != null && modelTaskRecordQo.getName().length() > 0) {
      predicates.add(task.name.like(modelTaskRecordQo.getName()));
    }
    return predicates;
  }

  @Override
  public Specification map(ModelTaskRecordExt modelTaskRecord) {
    return null;
  }
}
