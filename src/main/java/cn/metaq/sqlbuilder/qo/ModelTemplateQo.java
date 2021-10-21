package cn.metaq.sqlbuilder.qo;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * 模型模版查询对象
 *
 * @author zantang
 */
@Setter
@Getter
public class ModelTemplateQo implements Serializable {

  /** 战法模型定义id */
  private Long mid;

  /** 战法模型模版名称 */
  private String name;
}
