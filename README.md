## 表可选字段说明
```json5
{
  "type": "TABLE",
  "name": "t_person1",
  "dialect": "MYSQL", 
  "schema": "test_srv",
  "fields": [
    "id",
    "name",
    "age"
  ]
}
```

## 统计示例
```json5
{
  "type": "COUNT",
  "groupByFields": [
    {
      "name": "age",
      "alias": "_age0"
    }
  ],
  "alias": "c0",
  "source": {
    "type": "TABLE",
    "name": "t_person1",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  }
}

```
```sql
SELECT t0.age AS _age0,count(1) AS count FROM test_srv.t_person1 t0 GROUP BY t0.age

```

### 求和
```json5
{
  "type": "SUM",
  "groupByFields": [
    {
      "name": "name",
      "alias": "_name0"
    }
  ],
  "sumFields": [
    {
      "name": "age",
      "alias": "_age0"
    }
  ],
  "source": {
    "type": "TABLE",
    "name": "t_person1",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  },
  "alias": "s0"
}
```

```sql
SELECT SUM(t0.age) AS _age0,t0.name AS _name0 FROM test_srv.t_person1 t0 GROUP BY t0.name
```
## 去重示例
```json5
{
  "type": "DISTINCT",
  "distinctFields": [
    {
      "name": "age",
      "alias": "_age0"
    }
  ],
  "source": {
    "type": "TABLE",
    "name": "t_person1",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  },
  "alias": "d0"
}
```

```sql
SELECT DISTINCT t0.age AS _age0 FROM test_srv.t_person1 t0
```

## 过滤示例
```json5
{
  "type": "FILTER",
  "selectFields": [
    {
      "name": "id",
      "alias": "_id0"
    },
    {
      "name": "name",
      "alias": "_name0"
    },
    {
      "name": "age",
      "alias": "_age0"
    }
  ],
  "chain": {
    "conditions": [
      {
        "type": "GT",
        "name": "age",
        "value": 10
      }
    ]
  },
  "source": {
    "type": "TABLE",
    "name": "t_person2",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  },
  "alias": "f0"
}
```

```sql
SELECT t0.id AS _id0,t0.name AS _name0,t0.age AS _age0 FROM test_srv.t_person2 t0 WHERE (t0.age > 10)
```

## 合并列值
```json5
{
  "type": "CONCAT",
  "dialect": "MYSQL",
  "groupByFields": [
    {
      "name": "age",
      "alias": "_age0"
    }
  ],
  "concatFields": [
    {
      "name": "name",
      "alias": "_name0"
    }
  ],
  "alias": "g0",
  "source": {
    "type": "TABLE",
    "name": "t_person1",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  }
}
```

```sql
SELECT t0.age AS _age0,group_concat(t0.name) AS _name0 FROM test_srv.t_person1 t0 GROUP BY t0.age
```

## 关联示例
```json5
{
  "type": "LEFT_JOIN",
  "joinFields": {
    "left": [
      {
        "name": "_id0",
        "alias": "_id2"
      },
      {
        "name": "_name0",
        "alias": "_name2"
      }
    ],
    "right": [
      {
        "name": "_age1",
        "alias": "_age3"
      }
    ]
  },
  "on": [
    {
      "left": "_name0",
      "right": "_name1"
    }
  ],
  "left": {
    "type": "FILTER",
    "selectFields": [
      {
        "name": "id",
        "alias": "_id0"
      },
      {
        "name": "name",
        "alias": "_name0"
      },
      {
        "name": "age",
        "alias": "_age0"
      }
    ],
    "source": {
      "type": "TABLE",
      "name": "t_person2",
      "schema": "test_srv",
      "fields": [
        "id",
        "name",
        "age"
      ]
    },
    "chain": {
      "conditions": [
        {
          "type": "GT",
          "name": "age",
          "value": 5
        }
      ]
    },
    "alias": "v2"
  },
  "right": {
    "type": "FILTER",
    "selectFields": [
      {
        "name": "id",
        "alias": "_id1"
      },
      {
        "name": "name",
        "alias": "_name1"
      },
      {
        "name": "age",
        "alias": "_age1"
      }
    ],
    "source": {
      "type": "TABLE",
      "name": "t_person1",
      "schema": "test_srv",
      "fields": [
        "id",
        "name",
        "age"
      ]
    },
    "chain": {
      "conditions": [
        {
          "type": "GT",
          "name": "age",
          "value": 10
        }
      ]
    },
    "alias": "v1"
  },
  "alias": "v3"
}
```

```sql
  SELECT v2._id0 AS _id2, v2._name0 AS _name2, v1._age1 AS _age3
  FROM (
      SELECT t0.id AS _id0, t0.name AS _name0, t0.age AS _age0
      FROM test_srv.t_person2 t0
      WHERE t0.age > 5
      ) v2
      LEFT JOIN (
      SELECT t1.id AS _id1, t1.name AS _name1, t1.age AS _age1
      FROM test_srv.t_person1 t1
      WHERE t1.age > 10
      ) v1
  ON v2._name0 = v1._name1
```
## 交集
```json5
{
  "type": "SAME",
  "sameFields": {
    "left": [
      {
        "name": "name",
        "alias": "_name0"
      }
    ],
    "right": [
      {
        "name": "name",
        "alias": "_name1"
      }
    ]
  },
  "left": {
    "type": "TABLE",
    "name": "t_person2",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  },
  "right": {
    "type": "TABLE",
    "name": "t_person1",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  },
  "alias": "v3"
}
```

```sql
SELECT t0.name AS _name0 FROM test_srv.t_person2 t0 INNER JOIN test_srv.t_person1 t1 ON (t0.name = t1.name)

```

## 差集
```json5
{
  "type": "DIFF",
  "diffFields": {
    "left": [
      {
        "name": "id",
        "alias": "_id0"
      },
      {
        "name": "name",
        "alias": "_name0"
      },
      {
        "name": "age",
        "alias": "_age0"
      }
    ],
    "right": [
      {
        "name": "id",
        "alias": "_id1"
      },
      {
        "name": "name",
        "alias": "_name1"
      },
      {
        "name": "age",
        "alias": "_age1"
      }
    ]
  },
  "left": {
    "type": "TABLE",
    "name": "t_person1",
    "schema": "test_srv",
    "fields": ["id","name","age"]
  },
  "right": {
    "type": "TABLE",
    "name": "t_person2",
    "schema": "test_srv",
    "fields": ["id","name","age"]
  },
  "alias": "e0"
}
```
```sql
SELECT _u0.id AS _id0, _u0.name AS _name0, _u0.age AS _age0
FROM (
    SELECT t0.id, t0.name, t0.age
    FROM test_srv.t_person1 t0
    UNION
    SELECT t1.id, t1.name, t1.age
    FROM test_srv.t_person2 t1
    ) _u0
WHERE NOT EXISTS (
    SELECT _j0.id AS _id0, _j0.name AS _name0, _j0.age AS _age0
    FROM (
    SELECT t2.id, t2.name, t2.age
    FROM test_srv.t_person1 t2
    INNER JOIN test_srv.t_person2 t3 ON t2.id = t3.id
    ) _j0
    WHERE _j0.id = _u0.id
    )
```
## 并集
```json5
{
  "type": "UNION",
  "unionFields": {
    "left": [
      {
        "name": "id",
        "alias": "_id0"
      },
      {
        "name": "name",
        "alias": "_name0"
      },
      {
        "name": "age",
        "alias": "_age0"
      }
    ],
    "right": [
      {
        "name": "id",
        "alias": "_id1"
      },
      {
        "name": "name",
        "alias": "_name1"
      },
      {
        "name": "age",
        "alias": "_age1"
      }
    ]
  },
  "left": {
    "type": "TABLE",
    "name": "t_person2",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  },
  "right": {
    "type": "TABLE",
    "name": "t_person1",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  },
  "alias": "u0"
}
```

```sql

```

## 分页
```json5
{
  "type": "PAGE",
  "dialect": "MYSQL",
  "offset": 20,
  "limit": 10,
  "alias": "p0",
  "source": {
    "type": "TABLE",
    "name": "t_person1",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  },
  "orderFields": [
    {
      "name": "id",
      "type": "ASC"
    }
  ]
}
```

```sql
SELECT t0.name,t0.id,t0.age FROM t_person1 t0 ORDER BY t0.id DESC LIMIT 20, 10
```

## 排序
```json5
{
  "type": "ORDER",
  "orderFields":[
    {
      "name": "age",
      "type": "DESC|ASC"
    }
  ],
  "source": {
    "type": "TABLE",
    "name": "t_person1",
    "schema": "test_srv",
    "fields": [
      "id",
      "name",
      "age"
    ]
  }
  "alias": "o0"
}
```
## 不正经测试
```sql
环境
mysql8
mongo 5.0.1

测试机 macos m1 16g
```
```text
# 30w
查询花费时间 :0s
time = 4s


# 300w
查询花费时间 :18s
time = 45s
```

## swagger-ui
```text
http://localhost:8080/swagger-ui/index.html
```



