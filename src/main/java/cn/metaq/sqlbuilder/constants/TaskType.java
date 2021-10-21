package cn.metaq.sqlbuilder.constants;

/**
 * 任务类型
 *
 * @author zantang
 */
public enum TaskType {
  MODEL(0), FILE_DS(1), SYNC_META(2), SYNC_DATA(3);

  private final Integer value;

  TaskType(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
