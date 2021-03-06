package cn.metaq.sqlbuilder;

import cn.metaq.sqlbuilder.model.dto.ModelDTO;
import cn.metaq.std.sqlbuilder.step.DiffBuilderStep;
import cn.metaq.std.sqlbuilder.step.DistinctBuilderStep;
import cn.metaq.std.sqlbuilder.step.FilterBuilderStep;
import cn.metaq.std.sqlbuilder.step.JoinBuilderStep;
import cn.metaq.std.sqlbuilder.step.mysql.ConcatBuilderStep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.Conditions;
import com.healthmarketscience.sqlbuilder.Converter;
import com.healthmarketscience.sqlbuilder.CustomSql;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.Subquery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbJoin;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import org.junit.Test;

public class SqlbuilderTest {

    @Test
    public void testBuild(){

        // create default schema
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();

        // add table with basic customer info
        DbTable customerTable = schema.addTable("customer");
        DbColumn custIdCol = customerTable.addColumn("cust_id", "number", null);
        DbColumn custNameCol = customerTable.addColumn("name", "varchar", 255);
        // add order table with basic order info
        DbTable orderTable = schema.addTable("order");
        DbColumn orderIdCol = orderTable.addColumn("order_id", "number", null);
        DbColumn orderCustIdCol = orderTable.addColumn("cust_id", "number", null);
        DbColumn orderTotalCol = orderTable.addColumn("total", "number", null);
        DbColumn orderDateCol = orderTable.addColumn("order_date", "timestamp", null);

        DbJoin custOrderJoin = spec.addJoin(null, "customer",
                null, "order",
                "cust_id");

        //SELECT t0.name FROM customer t0 WHERE (t0.cust_id = 1)
        String query1 = new SelectQuery()
                        .addColumns(custNameCol)
                        .addCondition(BinaryCondition.equalTo(custIdCol, 1))
                        .validate().toString();
        System.out.println(query1);

        String query2 =
                new SelectQuery()
                        .addAllTableColumns(orderTable)
                        .addJoins(SelectQuery.JoinType.INNER, custOrderJoin)
                        .addCondition(BinaryCondition.equalTo(custNameCol, "bob"))
                        .addCondition(Conditions.or(BinaryCondition.equalTo(orderIdCol,"1"),BinaryCondition.equalTo(custNameCol, "bob")))
                        .addOrderings(orderDateCol)
                        .validate().toString();
        System.out.println(query2);


        Subquery sq=new Subquery(new SelectQuery().addColumns(orderCustIdCol).validate().toString());

        String query3 =
                new SelectQuery()
                        .addAllTableColumns(orderTable)
                        .addCustomJoin(SelectQuery.JoinType.INNER,customerTable
                                ,new CustomSql(sq),
                                BinaryCondition.equalTo(new CustomSql("t0.custId"), new CustomSql("t1.custId")))
                        .addCondition(BinaryCondition.equalTo(custNameCol, "bob"))
                        //.addCondition(Conditions.or(BinaryCondition.equalTo(orderIdCol,"1"),BinaryCondition.equalTo(custNameCol, "bob")))
                        .addOrderings(orderDateCol)
                        .validate().toString();
        System.out.println(query3);



        String query4 =
                new SelectQuery()
                        .addAllTableColumns(orderTable)
                        .addCustomJoin(SelectQuery.JoinType.INNER,customerTable
                                ,Converter.toCustomSqlObject(new SelectQuery().addColumns(orderCustIdCol).validate().toString(),"v1"),
                                BinaryCondition.equalTo(custIdCol, new CustomSql("v1.custId")))
                        .addCondition(BinaryCondition.equalTo(custNameCol, "bob"))
                        //.addCondition(Conditions.or(BinaryCondition.equalTo(orderIdCol,"1"),BinaryCondition.equalTo(custNameCol, "bob")))
                        .addOrderings(orderDateCol)
                        .validate().toString();
        System.out.println(query4);
    }


    @Test
    public void testSqlBuilderJoin() throws JsonProcessingException {

        String json="{\n" +
                "  \"type\": \"LEFT_JOIN\",\n" +
                "  \"join_fields\": {\n" +
                "    \"left\": [\"id\",\"name\"],\n" +
                "    \"right\": [\"address\",\"email\"]\n" +
                "  },\n" +
                "  \"on\": [\n" +
                "    {\n" +
                "      \"left\": \"id\",\n" +
                "      \"right\": \"id\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"left\": {\n" +
                "    \"type\": \"FILTER\",\n" +
                "    \"select_fields\": [\"name\",\"id\"],\n" +
                "    \"source\": {\n" +
                "      \"type\": \"TABLE\",\n" +
                "      \"table_name\": \"t_menu\",\n" +
                "      \"fields\": [\"id\",\"name\"]\n" +
                "    },\n" +
                "    \"condition\": {\n" +
                "      \"conditions\": [\n" +
                "        {\n" +
                "          \"type\": \"EQ\",\n" +
                "          \"name\": \"id\",\n" +
                "          \"value\": \"2\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"alias\": \"v2\"\n" +
                "  },\n" +
                "  \"right\": {\n" +
                "    \"type\": \"FILTER\",\n" +
                "    \"select_fields\": [\"name\",\"email\",\"address\"],\n" +
                "    \"source\": {\n" +
                "      \"type\": \"FILTER\",\n" +
                "      \"select_fields\": [\"name\",\"email\",\"address\"],\n" +
                "      \"source\": {\n" +
                "        \"type\": \"TABLE\",\n" +
                "        \"table_name\": \"t_resource\",\n" +
                "        \"fields\": [\"id\",\"name\",\"email\",\"address\"]\n" +
                "      },\n" +
                "      \"condition\": {\n" +
                "        \"conditions\": [\n" +
                "          {\n" +
                "            \"type\": \"EQ\",\n" +
                "            \"name\": \"type\",\n" +
                "            \"value\": \"5\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"alias\": \"v4\"\n" +
                "    },\n" +
                "    \"condition\": {\n" +
                "      \"conditions\": [\n" +
                "        {\n" +
                "          \"type\": \"EQ\",\n" +
                "          \"name\": \"id\",\n" +
                "          \"value\": \"1\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"alias\": \"v1\"\n" +
                "  },\n" +
                "  \"alias\": \"v3\"\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        JoinBuilderStep joinStep=mapper.readValue(json, JoinBuilderStep.class);

        DbSpec spec = new DbSpec();
        System.out.println(joinStep.build(spec).getQuery().validate());
    }

    @Test
    public void testSqlBuilderJoin2() throws JsonProcessingException {

        String json="{\n" +
                "  \"type\": \"LEFT_JOIN\",\n" +
                "  \"join_fields\": {\n" +
                "    \"left\": [\"id\",\"name\"],\n" +
                "    \"right\": [\"age\"]\n" +
                "  },\n" +
                "  \"on\": [\n" +
                "    {\n" +
                "      \"left\": \"name\",\n" +
                "      \"right\": \"name\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"left\": {\n" +
                "    \"type\": \"FILTER\",\n" +
                "    \"select_fields\": [\"id\",\"name\",\"age\"],\n" +
                "    \"source\": {\n" +
                "      \"type\": \"TABLE\",\n" +
                "      \"table_name\": \"t_person2\",\n" +
                "      \"fields\": [\"id\",\"name\",\"age\"]\n" +
                "    },\n" +
                "    \"condition\": {\n" +
                "      \"conditions\": [\n" +
                "        {\n" +
                "          \"type\": \"GT\",\n" +
                "          \"name\": \"age\",\n" +
                "          \"value\": 5\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"alias\": \"v2\"\n" +
                "  },\n" +
                "  \"right\": {\n" +
                "    \"type\": \"FILTER\",\n" +
                "    \"select_fields\": [\"id\",\"name\",\"age\"],\n" +
                "    \"source\": {\n" +
                "      \"type\": \"TABLE\",\n" +
                "      \"table_name\": \"t_person1\",\n" +
                "      \"fields\": [\"id\",\"name\",\"age\"]\n" +
                "    },\n" +
                "    \"condition\": {\n" +
                "      \"conditions\": [\n" +
                "        {\n" +
                "          \"type\": \"GT\",\n" +
                "          \"name\": \"age\",\n" +
                "          \"value\": 10\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"alias\": \"v1\"\n" +
                "  },\n" +
                "  \"alias\": \"v3\"\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        JoinBuilderStep joinStep=mapper.readValue(json, JoinBuilderStep.class);

        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addSchema("tlf");
        System.out.println(joinStep.build(spec).getQuery().validate());
    }

    @Test
    public void testCountStep() throws JsonProcessingException {
        String json="{\n" +
                "  \"debug\": false,\n" +
                "  \"data\": {\n" +
                "    \"type\": \"COUNT\",\n" +
                "    \"group_by_fields\": [\"age\"],\n" +
                "    \"alias\": \"c0\",\n" +
                "    \"source\": {\n" +
                "      \"type\": \"TABLE\",\n" +
                "      \"table_name\": \"t_person1\",\n" +
                "      \"fields\": [\"id\",\"name\",\"age\"]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        ModelDTO model=mapper.readValue(json, ModelDTO.class);

        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addSchema("tlf");
        String sql=model.getDetails().build(spec).getQuery().validate().toString();
        //System.out.println(sql);
//        model.getDetails().executor(new SqlExecutor() {
//            @Override
//            public List<Map<String, Object>> execute(String sql) {
//                //jdbc
//                //mybatis
//                System.out.println(sql);
//                return null;
//            }
//        }).preview(sql);
    }

    @Test
    public void testDistinctStep() throws JsonProcessingException {
        String json="{\n" +
                "  \"type\": \"DISTINCT\",\n" +
                "  \"distinct_fields\": [\"age\"],\n" +
                "  \"alias\": \"d0\",\n" +
                "  \"source\": {\n" +
                "    \"type\": \"TABLE\",\n" +
                "    \"table_name\": \"t_person1\",\n" +
                "    \"fields\": [\"id\",\"name\",\"age\"]\n" +
                "  }\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        DistinctBuilderStep step=mapper.readValue(json, DistinctBuilderStep.class);

        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        System.out.println(step.build(spec).getQuery().validate());
    }

    @Test
    public void testFilterStep() throws JsonProcessingException {
        String json="{\n" +
                "  \"type\": \"FILTER\",\n" +
                "  \"select_fields\": [\"id\",\"age\",\"name\"],\n" +
                "  \"alias\": \"f0\",\n" +
                "  \"source\": {\n" +
                "    \"type\": \"TABLE\",\n" +

                "    \"table_name\": \"t_person1\",\n" +
                "    \"fields\": [\"id\",\"name\",\"age\"]\n" +
                "  },\n" +
                "  \"condition\": {\n" +
                "    \"type\": \"OR\",\n" +
                "    \"conditions\": [\n" +
                "      {\n" +
                "        \"type\": \"GT\",\n" +
                "        \"name\": \"age\",\n" +
                "        \"value\": 20\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        FilterBuilderStep step=mapper.readValue(json,FilterBuilderStep.class);

        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        System.out.println(step.build(spec).getQuery().validate());
    }

    @Test
    public void testGroupConcatStep() throws JsonProcessingException {
        String json="{\n" +
                "  \"type\": \"GROUP_CONCAT\",\n" +
                "  \"dialect\": \"MYSQL\",\n" +
                "  \"group_by_fields\": [\"age\"],\n" +
                "  \"group_concat_fields\": [\"name\"],\n" +
                "  \"alias\": \"g0\",\n" +
                "  \"source\": {\n" +
                "    \"type\": \"TABLE\",\n" +
                "    \"table_name\": \"t_person1\",\n" +
                "    \"fields\": [\"id\",\"name\",\"age\"]\n" +
                "  }\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        ConcatBuilderStep step=mapper.readValue(json,ConcatBuilderStep.class);

        DbSpec spec = new DbSpec();
        System.out.println(step.build(spec).getQuery().validate());
    }

    /**
     * {
     *   "type": "EXCEPT",
     *   "union_fields": {
     *     "left": ["id","name_cn"],
     *     "right": ["id","name"]
     *   },
     *   "on": [
     *     {
     *       "left": "id",
     *       "right": "id"
     *     }
     *   ],
     *   "left": {
     *     "type": "TABLE",
     *     "table_name": "t_airline",
     *     "fields": ["id","name_cn"]
     *   },
     *   "right": {
     *     "type": "TABLE",
     *     "table_name": "t_airport",
     *     "fields": ["id","name"]
     *   }
     * }
     * @throws JsonProcessingException
     */
    @Test
    public void testExceptStep() throws JsonProcessingException {
        String json="{\n" +
                "  \"type\": \"EXCEPT\",\n" +
                "  \"union_fields\": {\n" +
                "    \"left\": [\"id\",\"name\",\"age\"],\n" +
                "    \"right\": [\"id\",\"name\",\"age\"]\n" +
                "  },\n" +
                "  \"on\": [\n" +
                "    {\n" +
                "      \"left\": \"name\",\n" +
                "      \"right\": \"name\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"left\": {\n" +
                "    \"type\": \"TABLE\",\n" +
                "    \"table_name\": \"t_person1\",\n" +
                "    \"fields\": [\"id\",\"name\",\"age\"]\n" +
                "  },\n" +
                "  \"right\": {\n" +
                "    \"type\": \"TABLE\",\n" +
                "    \"table_name\": \"t_person2\",\n" +
                "    \"fields\": [\"id\",\"name\",\"age\"]\n" +
                "  },\n" +
                "  \"alias\": \"e0\"\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        DiffBuilderStep step=mapper.readValue(json, DiffBuilderStep.class);

        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        System.out.println(step.build(spec).getQuery().validate());
    }

}
