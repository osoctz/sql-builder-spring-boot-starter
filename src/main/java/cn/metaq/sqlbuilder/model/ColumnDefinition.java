package cn.metaq.sqlbuilder.model;

import cn.metaq.common.core.IEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Date: Mon Sep 27 13:29:17 CST 2021.</p>
 * <p>表字段.</p>
 *
 * @author tom
 */
@Entity
@Table(name = "t_re_column_definition")
@Setter
@Getter
public class ColumnDefinition implements IEntity<Long>{

	private static final long serialVersionUID =  1646257569742746798L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id" )
	private Long id;

	/**
	 * 字段名
	 */
	@Column(name = "name" )
	private String name;

	/**
	 * 类型
	 */
	@Column(name = "data_type" )
	private String dataType;

	/**
	 * 注释
	 */
	@Column(name = "comment" )
	private String comment;

	/**
	 * 创建者
	 */
	@Column(name = "created_by" )
	private String createdBy;

	/**
	 * 创建时间
	 */
	@Column(name = "created_ts" )
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date createdTs;

	/**
	 * 表id
	 */
	@Column(name = "tid" )
	private Long tid;

}
