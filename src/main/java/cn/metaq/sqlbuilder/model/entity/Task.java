package cn.metaq.sqlbuilder.model.entity;

import cn.metaq.common.core.IEntity;
import cn.metaq.sqlbuilder.util.UserContextUtils;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Date: Fri Oct 01 01:40:41 CST 2021.
 *
 * <p>任务基本信息表.
 *
 * @author tom
 */
@Entity
@Table(name = "t_def_task")
@Setter
@Getter
public class Task implements IEntity<Long> {

  private static final long serialVersionUID = 4654870076608372230L;

  @Id
  @GeneratedValue(generator = "snowflakeId")
  @GenericGenerator(name = "snowflakeId", strategy = "cn.metaq.data.jpa.id.SnowflakeIdGenerator")
  private Long id;

  /** 任务名称 */
  @Column(name = "name")
  private String name;

  /** 0-模型任务 1-元数据同步 2-数据同步 */
  @Column(name = "type")
  private Integer type;

  /** 0-立即执行，1-手动执行，3-定时调度 */
  @Column(name = "mode")
  private Integer mode;

  /** 创建者 */
  @Column(name = "created_by")
  private String createdBy;

  /** 创建时间 */
  @Column(name = "created_ts")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdTs;

  /** 更新者 */
  @Column(name = "updated_by")
  private String updatedBy;

  /** 更新时间 */
  @Column(name = "updated_ts")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedTs;

  @PrePersist
  public void preCreate() {
    this.setCreatedBy(UserContextUtils.getUser());
    this.setCreatedTs(new Date());
  }

  @PreUpdate
  public void preUpdate() {
    this.setUpdatedBy(UserContextUtils.getUser());
    this.setUpdatedTs(new Date());
  }
}
