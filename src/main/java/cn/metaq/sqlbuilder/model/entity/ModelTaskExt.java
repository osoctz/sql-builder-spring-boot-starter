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
 * <p>模型任务扩展表.
 *
 * @author tom
 */
@Entity
@Table(name = "t_def_model_task_ext")
@Setter
@Getter
public class ModelTaskExt implements IEntity<Long> {

  private static final long serialVersionUID = 6068830620744075908L;

  @Id
  private Long id;

  /** 战法模型定义id */
  @Column(name = "mid")
  private Long mid;

  /** 构建的sql */
  @Column(name = "build")
  private String build;

  /** 改进的sql */
  @Column(name = "improve")
  private String improve;

  /** 结果集字段和字段注释映射 */
  @Column(name = "columns")
  private String columns;
}
