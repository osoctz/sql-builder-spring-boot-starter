package cn.metaq.sqlbuilder.model;

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
 * Date: Fri Oct 01 01:41:20 CST 2021.
 *
 * <p>任务运行记录表.
 *
 * @author tom
 */
@Entity
@Table(name = "t_hi_task_record")
@Setter
@Getter
public class TaskRecord implements IEntity<Long> {

  private static final long serialVersionUID = 845883914583374637L;

  @Id
  @GeneratedValue(generator = "snowflakeId")
  @GenericGenerator(name = "snowflakeId", strategy = "cn.metaq.data.jpa.id.SnowflakeIdGenerator")
  private Long id;

  /** 任务id */
  @Column(name = "tid")
  private Long tid;

  /** 运行状态 0-成功 1-失败 */
  @Column(name = "status")
  private Integer status;

  /** 错误信息 */
  @Column(name = "error_msg")
  private String errorMsg;

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
