package cn.metaq.sqlbuilder.model;

import cn.metaq.common.core.IEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Date: Mon Sep 27 13:29:40 CST 2021.</p>
 * <p>远程数据源.</p>
 *
 * @author tom
 */
@Entity
@Table(name = "t_re_remote_datasource")
@Setter
@Getter
public class RemoteDatasource implements IEntity<Long>{

	private static final long serialVersionUID =  3535972484903719903L;

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
	 * 数据库类型 0-mysql,1-oracle,2-pg
	 */
	@Column(name = "type" )
	private Long type;

	/**
	 * ip或主机名
	 */
	@Column(name = "host" )
	private String host;

	/**
	 * 服务端口
	 */
	@Column(name = "port" )
	private Long port;

	/**
	 * 数据库名
	 */
	@Column(name = "dbName" )
	private String dbName;

	/**
	 * 额外的参数
	 */
	@Column(name = "additionalInformation" )
	private String additionalInformation;

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

	/**
	 * 更新者
	 */
	@Column(name = "updatedBy" )
	private String updatedBy;

	/**
	 * 更新时间
	 */
	@Column(name = "updatedTs" )
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date updatedTs;

}
