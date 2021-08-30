package cn.metaq.sqlbuilder.web;

import cn.metaq.sqlbuilder.SqlbuilderStep;
import cn.metaq.sqlbuilder.jackson.databind.SqlbuilderStepDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

/**
 * 可视化建模流程图数据及相关设置参数
 */
@Setter
@Getter
public class SqlVisualModel {

    /**
     * 是否调试
     */
    private boolean debug;

    @JsonDeserialize(using = SqlbuilderStepDeserializer.class)
    private SqlbuilderStep data;
}
