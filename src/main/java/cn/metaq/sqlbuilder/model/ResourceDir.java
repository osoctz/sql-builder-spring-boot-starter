package cn.metaq.sqlbuilder.model;

import cn.metaq.common.core.IEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Date: Mon Sep 27 13:29:54 CST 2021.</p>
 * <p>资源目录.</p>
 *
 * @author tom
 */
@Entity
@Table(name = "t_re_resource_dir")
@Setter
@Getter
public class ResourceDir implements IEntity<Long>{

	private static final long serialVersionUID =  5854872894202812452L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id" )
	private Long id;

	/**
	 * 父id
	 */
	@Column(name = "pid" )
	private Long pid;

	/**
	 * 目录名称
	 */
	@Column(name = "name" )
	private String name;

	/**
	 * 层级
	 */
	@Column(name = "level" )
	private Long level;

	/**
	 * 优先级
	 */
	@Column(name = "priority" )
	private Long priority;

}
