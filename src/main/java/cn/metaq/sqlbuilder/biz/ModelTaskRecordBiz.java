package cn.metaq.sqlbuilder.biz;

import cn.metaq.data.Biz;
import cn.metaq.sqlbuilder.dto.ColumnDTO;
import cn.metaq.sqlbuilder.model.ModelTask;
import cn.metaq.sqlbuilder.model.ModelTaskRecord;
import java.util.List;

public interface ModelTaskRecordBiz extends Biz<ModelTaskRecord,Long> {

  ModelTaskRecord execute(ModelTask task);
}
