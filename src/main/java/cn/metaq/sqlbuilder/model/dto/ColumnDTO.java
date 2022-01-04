package cn.metaq.sqlbuilder.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 字段
 *
 * @author zantang
 */
@Setter
@Getter
public class ColumnDTO implements Serializable {

  private String name;
  private String comment;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String columnType;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Integer dataType;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
