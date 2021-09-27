package cn.metaq.sqlbuilder.biz;

import cn.metaq.data.Biz;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTask;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTaskRecord;

public interface SqlbuilderModelTaskBiz extends Biz<SqlbuilderModelTask, Long> {

  SqlbuilderModelTaskRecord execute(SqlbuilderModelTask task);
}
