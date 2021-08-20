package cn.metaq.sqlbuilder.model;

import cn.metaq.sqlbuilder.constants.ConditionPredicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 条件过滤表达式
 *
 * @author tz
 * @date 2020/11/19 上午10:57
 * @since 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Condition<T> {

    private ConditionPredicate type;
    private String name;
    private T value;
}
