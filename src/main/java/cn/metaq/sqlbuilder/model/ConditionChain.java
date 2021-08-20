package cn.metaq.sqlbuilder.model;

import cn.metaq.sqlbuilder.constants.LogicalPredicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConditionChain {

    private LogicalPredicate type = LogicalPredicate.AND;

    private List<Condition> conditions=new ArrayList<>();
}
