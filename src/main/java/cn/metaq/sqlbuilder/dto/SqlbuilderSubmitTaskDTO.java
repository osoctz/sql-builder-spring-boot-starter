package cn.metaq.sqlbuilder.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * 提交任务
 *
 * @author zantang
 */
@Setter
@Getter
public class SqlbuilderSubmitTaskDTO implements Serializable {

  /**
   * 任务类型 0-立即执行 1-手动执行 3-定时触发
   */
  private Integer taskType;

  /**
   * 改进后的sql
   */
  private String improvement;

  @Override
  public String toString() {
    return "SqlbuilderSubmitTaskDTO{" +
        " taskType=" + taskType +
        ", improvement='" + improvement + '\'' +
        '}';
  }
}
