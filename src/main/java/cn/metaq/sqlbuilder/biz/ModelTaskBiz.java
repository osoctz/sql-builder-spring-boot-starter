package cn.metaq.sqlbuilder.biz;

import cn.metaq.data.Biz;
import cn.metaq.sqlbuilder.model.ModelTask;
import cn.metaq.sqlbuilder.model.ModelTaskRecord;

public interface ModelTaskBiz extends Biz<ModelTask, Long> {

  ModelTaskRecord execute(ModelTask task);
}