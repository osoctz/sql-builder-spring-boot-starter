package cn.metaq.sqlbuilder.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 模型执行结果视图
 *
 * @author zantang
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModelTaskRecordViewDTO implements Serializable {

  /** 记录id */
  private Long id;

  /** 模型id */
  private Long mid;

  /** 模型名称/任务名称 */
  private String name;

  /** 运行状态 0-成功 1-失败 */
  private Integer status;

  /** mongo中集合名称 */
  private String collection;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
