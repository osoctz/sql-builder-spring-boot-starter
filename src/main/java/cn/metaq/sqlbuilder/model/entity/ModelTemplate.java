package cn.metaq.sqlbuilder.model.entity;

import cn.metaq.common.core.IEntity;
import cn.metaq.sqlbuilder.util.UserContextUtils;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Date: Mon Sep 27 13:22:15 CST 2021.
 *
 * <p>模型定义模版表.
 *
 * @author tom
 */
@Entity
@Table(name = "t_def_model_template")
@Setter
@Getter
public class ModelTemplate implements IEntity<Long> {

  private static final long serialVersionUID = 2089944332976494288L;

  @Id
  @GeneratedValue(generator = "snowflakeId")
  @GenericGenerator(name = "snowflakeId", strategy = "cn.metaq.data.jpa.id.SnowflakeIdGenerator")
  private Long id;

  /** 战法模型定义id */
  private Long mid;

  /** 战法模型模版名称 */
  private String name;

  /** 模型定义json */
  private String definition;

  /** 创建者 */
  @Column(name = "created_by")
  private String createdBy;

  /** 创建时间 */
  @Column(name = "created_ts")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdTs;

  @PrePersist
  public void preCreate() {
    this.setCreatedBy(UserContextUtils.getUser());
    this.setCreatedTs(new Date());
  }
}
