package cn.metaq.sqlbuilder.model.entity;

import cn.metaq.common.core.IEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Date: Fri Oct 01 01:40:41 CST 2021.
 *
 * <p>模型任务运行记录扩展表.
 *
 * @author tom
 */
@Entity
@Table(name = "t_hi_model_task_record_ext")
@Setter
@Getter
public class ModelTaskRecordExt implements IEntity<Long> {

  private static final long serialVersionUID = 5769227972871737268L;

  /** 运行记录id */
  @Id
  private Long id;

  /** 执行的sql */
  @Column(name = "execute")
  private String execute;

  /** 存储战法运行结果数据的mongo的collection */
  @Column(name = "collection")
  private String collection;

  /** 结果集字段和字段注释映射 */
  @Column(name = "columns")
  private String columns;
}
