#数据模型定义
## 表
```json5
{
  "type": "table",
  "fields": []
}
```

## 过滤
```json5
{
  "type": "filter",
  "select_fields":[],
  "condition":[],
  "source":{}
}
```

## 去重
```json5
{
  "type":"distinct",
  "distinct_fields":[],
  "source":{}
}
```
## 左关联
```json5
{
    "type": "left_join", 
    "select_fields": {
        "left": [], 
        "right": []
    },
    "on": [],
    "left": "table|distict|filter|intersect等", 
    "right": "table|distict|filter|intersect等"
}
```

## 交集
```json5

```

## 并集
```json5
{
    "type": "union", 
    "union_fields": {
        "left": [], 
        "right": []
    },
    "left": {},
    "right": {}
}
```

## 差集
```json5
{
  "type": "except",
  "union_fields": {
    "left": [],
    "right": []
  },
  "on": [],
  "left": {},
  "right": {}
}
```

## 统计
```json5
{
  "type": "count",
  "group_by_fields":[],
  "source": {}
}
```

## 求和
```json5
{
  "type": "sum",
  "group_by_fields":[],
  "source":{}
}
```
## 合并列值
```json5
{
  "type": "group_concat",
  "group_by_fields":[],
  "group_concat_fields":[],
  "source":{}
}
```

## 综合示例

![](WX20210818-152324@2x.png)
```json5
{
  "type": "count",
  "group_by_fields": [],
  "source": {
    "type": "union",
    "select_fields": {
      "left": [],
      "right": []
    },
    "left": {
      "type": "inner_join",
      "select_fields": {
        "left": [],
        "right": []
      },
      "join": {
        "type": "leftjoin",
        "on": {
          "left": "id",
          "right": "id"
        }
      },
      "left": {
        "type": "filter",
        "select_fields": [],
        "condition": {},
        "source": {
          "type": "table",
          "fields": ["id", "name", "age"]
        }
      },
      "right": {
        "type": "filter",
        "select_fields": [],
        "condition": {},
        "source": {
          "type": "distinct",
          "distinct_fields": [],
          "source": {
            "type": "table",
            "fields": ["id", "addr", "email"]
          }
        }
      }
    },
    "right": {
      "type": "table",
      "fields": []
    }
  }
}
```

## 统计示例
```json5
{
  "type": "COUNT",
  "group_by_fields": ["age"],
  "alias": "c0",
  "source": {
    "type": "TABLE",
    "table_name": "t_person1",
    "fields": ["id","name","age"]
  }
}

```
```sql
SELECT t0.age,count(1) FROM t_person1 t0 GROUP BY t0.age
```

## 去重示例
```json5
{
  "type": "DISTINCT",
  "distinct_fields": ["age"],
  "alias": "d0",
  "source": {
    "type": "TABLE",
    "table_name": "t_person1",
    "fields": ["id","name","age"]
  }
}
```

```sql
SELECT DISTINCT t0.age FROM t_person1 t0
```

## 过滤示例
```json5
{
  "type": "FILTER",
  "select_fields": ["id","age","name"],
  "alias": "f0",
  "source": {
    "type": "TABLE",
    "table_name": "t_person1",
    "fields": ["id","name","age"]
  },
  "condition": {
    "type": "OR",
    "conditions": [
      {
        "type": "GT",
        "name": "age",
        "value": "20"
      }
    ]
  }
}
```

```sql
SELECT t0.id,t0.age,t0.name FROM t_person1 t0 WHERE (t0.age >= 20)

```
## 合并列值
```json5
{
  "type": "GROUP_CONCAT",
  "group_by_fields": ["age"],
  "group_concat_fields": ["name"],
  "alias": "g0",
  "source": {
    "type": "TABLE",
    "table_name": "t_person1",
    "fields": ["id","name","age"]
  }
}
```

```sql
SELECT t0.age,group_concat(t0.name) FROM t_person1 t0 GROUP BY t0.age
```

## 关联示例
```json5
{
  "type": "LEFT_JOIN",
  "join_fields": {
    "left": ["id","name"],
    "right": ["age"]
  },
  "on": [
    {
      "left": "name",
      "right": "name"
    }
  ],
  "left": {
    "type": "FILTER",
    "select_fields": ["id","name","age"],
    "source": {
      "type": "TABLE",
      "table_name": "t_person2",
      "fields": ["id","name","age"]
    },
    "condition": {
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
    "select_fields": ["id","name","age"],
    "source": {
      "type": "TABLE",
      "table_name": "t_person1",
      "fields": ["id","name","age"]
    },
    "condition": {
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
SELECT v2.id, v2.name, v1.age
FROM (
	SELECT t0.id, t0.name, t0.age
	FROM t_person2 t0
	WHERE t0.age > 5
) v2
	LEFT JOIN (
		SELECT t1.id, t1.name, t1.age
		FROM t_person1 t1
		WHERE t1.age > 10
	) v1
	ON v2.name = v1.name
```
## 差集示例
```json5
{
  "type": "EXCEPT",
  "union_fields": {
    "left": ["id","name","age"],
    "right": ["id","name","age"]
  },
  "on": [
    {
      "left": "id",
      "right": "id"
    }
  ],
  "left": {
    "type": "TABLE",
    "table_name": "t_person1",
    "fields": ["id","name","age"]
  },
  "right": {
    "type": "TABLE",
    "table_name": "t_person2",
    "fields": ["id","name","age"]
  },
  "alias": "e0"
}
```
```sql
SELECT _u0.id, _u0.NAME, _u0.age
FROM (
    SELECT t0.id, t0.NAME, t0.age
    FROM t_person1 t0
    UNION
    SELECT t1.id, t1.NAME, t1.age
    FROM t_person2 t1
    ) _u0
WHERE NOT EXISTS (
    SELECT _j0.id, _j0.NAME, _j0.age
    FROM (
    SELECT t2.id, t2.NAME, t2.age
    FROM t_person1 t2
    INNER JOIN t_person2 t3 ON t2.NAME = t3.NAME
    ) _j0
    WHERE _j0.NAME = _u0.NAME
    )
```


## 测试
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



