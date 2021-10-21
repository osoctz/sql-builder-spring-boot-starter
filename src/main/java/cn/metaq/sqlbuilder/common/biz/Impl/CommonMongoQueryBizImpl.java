package cn.metaq.sqlbuilder.common.biz.Impl;

import cn.metaq.data.mongo.BaseBiz;
import cn.metaq.sqlbuilder.common.biz.CommonMongoQueryBiz;
import java.util.HashMap;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * 通用数据结构 mongo查询
 *
 * @author zantang
 * @see "ai.bailian.sqlbuilder.common.MongoExecutorTest"
 */
@Service
public class CommonMongoQueryBizImpl extends BaseBiz<HashMap, String> implements CommonMongoQueryBiz {

  @Override
  public Query map(HashMap params) {

    Criteria criteria = new Criteria();
    Query query = new Query(criteria);
    return query;
  }
}
