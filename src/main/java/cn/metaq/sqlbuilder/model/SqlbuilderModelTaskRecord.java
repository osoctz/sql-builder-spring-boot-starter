package cn.metaq.sqlbuilder.model;

import cn.metaq.common.core.IEntity;
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
 *
 * 模型任务运行记录
 * @author zantang
 */
@Table(name = "t_hi_model_task_record")
@Entity
@Setter
@Getter
public class SqlbuilderModelTaskRecord implements IEntity<Long> {

  @Id
  @GeneratedValue(generator = "snowflakeId")
  @GenericGenerator(name = "snowflakeId", strategy = "cn.metaq.data.jpa.id.SnowflakeIdGenerator")
  private Long id;

  private Long tid;

  private String execute;

  private String collection;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_ts")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date createdTs;

  @PrePersist
  public void preCreate() {
    this.setCreatedBy("test");
    this.setCreatedTs(new Date());
  }
}
