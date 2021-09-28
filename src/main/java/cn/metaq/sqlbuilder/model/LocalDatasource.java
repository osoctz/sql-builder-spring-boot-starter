package cn.metaq.sqlbuilder.model;

import cn.metaq.common.core.IEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Date: Mon Sep 27 13:29:29 CST 2021.</p>
 * <p>本地数据源.</p>
 *
 * @author tom
 */
@Entity
@Table(name = "t_re_local_datasource")
@Setter
@Getter
public class LocalDatasource implements IEntity<Long>{

	private static final long serialVersionUID =  3237846926880891989L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id" )
	private Long id;

	/**
	 * 名称
	 */
	@Column(name = "name" )
	private String name;

	/**
	 * 注释
	 */
	@Column(name = "comment" )
	private String comment;

	/**
	 * jdbc url
	 */
	@Column(name = "url" )
	private String url;

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
	 * 远程数据源id
	 */
	@Column(name = "rid" )
	private Long rid;

}
