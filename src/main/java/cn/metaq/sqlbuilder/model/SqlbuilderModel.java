package cn.metaq.sqlbuilder.model;

import cn.metaq.common.core.IEntity;
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
 * 战法模型定义
 *
 * @author zantang
 */
@Table(name = "t_def_model")
@Entity
@Setter
@Getter
public class SqlbuilderModel implements IEntity<Long> {

  @Id
  @GeneratedValue(generator = "snowflakeId")
  @GenericGenerator(name = "snowflakeId", strategy = "cn.metaq.data.jpa.id.SnowflakeIdGenerator")
  private Long id;

  private String name;

  private String definition;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_ts")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date createdTs;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "updated_ts")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date updateTs;

  @PrePersist
  public void preCreate() {
    this.setCreatedBy("test");
    this.setCreatedTs(new Date());
  }

  @PreUpdate
  public void preUpdate() {
    this.setUpdatedBy("test");
    this.setUpdateTs(new Date());
  }
}