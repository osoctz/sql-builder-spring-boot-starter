package cn.metaq.sqlbuilder.model;

import cn.metaq.common.core.IEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Date: Mon Sep 27 12:49:10 CST 2021.</p>
 * <p>模型可视化布局表.</p>
 *
 * @author tom
 */
@Entity
@Table(name = "t_def_model_graph")
@Setter
@Getter
public class ModelGraph implements IEntity<Long>{

	private static final long serialVersionUID =  7813001343889421368L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 模型定义表id
	 */
	private Long mid;

	/**
	 * 前端需要的点和线坐标数据
	 */
	private String graph;

}
