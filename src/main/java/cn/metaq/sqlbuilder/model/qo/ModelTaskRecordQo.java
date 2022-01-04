package cn.metaq.sqlbuilder.model.qo;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 模型执行记录查询对象
 *
 * @author zantang
 */
@Setter
@Getter
public class ModelTaskRecordQo implements Serializable {

  /** 模型id */
  private Long mid;

  /** 模型名称/任务名称 */
  private String name;

  /** 运行状态 0-成功 1-失败 */
  private Integer status;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
