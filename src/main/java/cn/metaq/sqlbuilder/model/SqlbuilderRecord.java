package cn.metaq.sqlbuilder.model;

import cn.metaq.common.core.IEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table(value = "sqlbuilder_record")
@Setter
@Getter
public class SqlbuilderRecord implements IEntity<String> {

    @Id
    private String id;

    @Column(value = "result_view")
    private String resultView;

    @Column(value = "view_sql")
    private String viewSql;

    @Column(value = "create_ts")
    private Date createTs;

}
