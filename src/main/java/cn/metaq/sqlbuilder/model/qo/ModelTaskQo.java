package cn.metaq.sqlbuilder.model.qo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 模型运行任务查询对象
 *
 * @author zantang
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModelTaskQo implements Serializable {

  private Long id;
  private String name;
  private Integer type;
  private Integer mode;
  private Long mid;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
