package cn.metaq.sqlbuilder.model;

import cn.metaq.common.core.IEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Date: Mon Sep 27 13:22:15 CST 2021.</p>
 * <p>模型定义模版表.</p>
 *
 * @author tom
 */
@Entity
@Table(name = "t_def_model_template")
@Setter
@Getter
public class ModelTemplate implements IEntity<Long>{

	private static final long serialVersionUID =  2089944332976494288L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id" )
	private Long id;

	/**
	 * 战法模型定义id
	 */
	@Column(name = "mid" )
	private Long mid;

	/**
	 * 战法模型模版名称
	 */
	@Column(name = "name" )
	private String name;

	/**
	 * 模型定义json
	 */
	@Column(name = "definition" )
	private String definition;

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
