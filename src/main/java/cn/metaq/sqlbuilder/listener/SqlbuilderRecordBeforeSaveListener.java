package cn.metaq.sqlbuilder.listener;

import cn.metaq.data.mongo.util.ObjectIdUtils;
import cn.metaq.sqlbuilder.model.SqlbuilderRecord;
import org.springframework.data.relational.core.mapping.event.AbstractRelationalEventListener;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

@Component
public class SqlbuilderRecordBeforeSaveListener extends AbstractRelationalEventListener<SqlbuilderRecord> {

    private static final String VIEW_PREFIX = "record_";

    @Override
    protected void onBeforeSave(BeforeSaveEvent<SqlbuilderRecord> event) {

        SqlbuilderRecord record = event.getEntity();
        String id = ObjectIdUtils.nextId();
        record.setId(id);
        record.setResultView(VIEW_PREFIX + record.getId());

        super.onBeforeSave(event);
    }
}
