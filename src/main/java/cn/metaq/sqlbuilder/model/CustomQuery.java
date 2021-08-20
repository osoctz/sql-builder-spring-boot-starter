package cn.metaq.sqlbuilder.model;

import com.healthmarketscience.sqlbuilder.*;
import lombok.Builder;

@Builder
public class CustomQuery {

    private String alias;

    private Query query;

    public String getAlias() {
        return alias;
    }

    public Query getQuery(){
        return query;
    }

    public SqlObject toCustomSqlObject() {
        return Converter.toCustomSqlObject(new Subquery(query), alias);
    }

}
