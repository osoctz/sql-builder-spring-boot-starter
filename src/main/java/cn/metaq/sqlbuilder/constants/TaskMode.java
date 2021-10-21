package cn.metaq.sqlbuilder.constants;

/**
 * 任务类型
 *
 * @author zantang
 */
public enum TaskMode {
  IMMEDIATE(0), MANUAL(1), SCHEDULE(2);

  private final Integer value;

  TaskMode(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
