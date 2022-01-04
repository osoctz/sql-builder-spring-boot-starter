package cn.metaq.sqlbuilder.model.dto;

import cn.metaq.std.sqlbuilder.jackson.databind.SqlbuilderStepDeserializer;
import cn.metaq.std.sqlbuilder.step.SqlbuilderStep;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 可视化建模流程图数据及相关设置参数
 *
 * @author zantang
 */
@Setter
@Getter
public class ModelDTO implements Serializable {

  /** 是否调试模式 */
  private Boolean debug = false;

  private Integer offset;
  private Integer limit;

  private String name;

  @JsonDeserialize(using = SqlbuilderStepDeserializer.class)
  private SqlbuilderStep details;

  private String graph;

  /** 结果columns */
  private List<ColumnDTO> columns;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
