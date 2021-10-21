package cn.metaq.sqlbuilder.common.biz;

import cn.metaq.data.Biz;
import cn.metaq.data.mongo.MongoQueryBiz;
import java.util.HashMap;

public interface CommonMongoQueryBiz extends Biz<HashMap, String>, MongoQueryBiz<HashMap> {

}
