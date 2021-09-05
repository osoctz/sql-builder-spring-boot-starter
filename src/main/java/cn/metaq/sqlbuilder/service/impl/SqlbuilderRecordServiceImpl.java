package cn.metaq.sqlbuilder.service.impl;

import cn.metaq.data.jdbc.BaseBiz;
import cn.metaq.data.mongo.util.ObjectIdUtils;
import cn.metaq.sqlbuilder.dao.SqlbuilderRecordDao;
import cn.metaq.sqlbuilder.model.SqlbuilderRecord;
import cn.metaq.sqlbuilder.service.SqlbuilderRecordService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SqlbuilderRecordServiceImpl extends BaseBiz<SqlbuilderRecord, String, SqlbuilderRecordDao> implements SqlbuilderRecordService {

    @Resource
    private MongoTemplate mongoTemplate;

    public void save(SqlbuilderRecord object, List<Map<String, Object>> data) {

        super.save(object);
        long start = System.currentTimeMillis();
        mongoTemplate.insert(data,object.getResultView());
        long end=System.currentTimeMillis();

        System.out.println("time = " + (end-start)/1000+"s");
    }
}
