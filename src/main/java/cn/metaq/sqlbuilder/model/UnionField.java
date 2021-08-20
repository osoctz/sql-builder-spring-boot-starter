package cn.metaq.sqlbuilder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UnionField {

    private List<String> left=new ArrayList<>();
    private List<String> right=new ArrayList<>();
}
