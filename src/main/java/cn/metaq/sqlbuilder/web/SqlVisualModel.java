package cn.metaq.sqlbuilder.web;

import cn.metaq.std.sqlbuilder.jackson.databind.SqlbuilderStepDeserializer;
import cn.metaq.std.sqlbuilder.step.SqlbuilderStep;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

/**
 * 可视化建模流程图数据及相关设置参数
 *
 * @author zantang
 */
@Setter
@Getter
public class SqlVisualModel {

  /**
   * 是否调试模式
   */
  private boolean debug = Boolean.FALSE;

  @JsonDeserialize(using = SqlbuilderStepDeserializer.class)
  private SqlbuilderStep details;
}
