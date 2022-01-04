package cn.metaq.sqlbuilder.model.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 模型运行任务定义表
 *
 * @author zantang
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModelTaskDTO implements Serializable {

  private Long id;
  private String name;
  private Integer type;
  private Integer mode;
  private String build;
  private String improve;
  private String columns;
  private Long mid;

  private String createdBy;
  private Date createdTs;
  private String updatedBy;
  private Date updateTs;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
