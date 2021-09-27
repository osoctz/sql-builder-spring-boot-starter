package cn.metaq.sqlbuilder.model;

import cn.metaq.common.core.IEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Date: Mon Sep 27 13:30:04 CST 2021.</p>
 * <p>表.</p>
 *
 * @author tom
 */
@Entity
@Table(name = "t_re_table_definition")
@Setter
@Getter
public class TableDefinition implements IEntity<Long>{

	private static final long serialVersionUID =  2641937712282411710L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id" )
	private Long id;

	/**
	 * 数据源id
	 */
	@Column(name = "sid" )
	private String sid;

	/**
	 * 表名
	 */
	@Column(name = "name" )
	private String name;

	/**
	 * 注释
	 */
	@Column(name = "comment" )
	private String comment;

	/**
	 * 创建者
	 */
	@Column(name = "createdBy" )
	private String createdBy;

	/**
	 * 创建时间
	 */
	@Column(name = "createdTs" )
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date createdTs;

}
