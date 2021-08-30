package cn.metaq.sqlbuilder.jackson.databind;

import cn.metaq.sqlbuilder.SqlbuilderStep;
import cn.metaq.sqlbuilder.constants.SqlbuilderStepType;
import cn.metaq.sqlbuilder.step.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;

public class SqlbuilderStepDeserializer extends JsonDeserializer<SqlbuilderStep> {

    @Override
    public SqlbuilderStep deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        //表达式json
        JsonNode node = jp.getCodec().readTree(jp);

        String stepJson = node.toString();
        String type = JsonPath.read(stepJson, "$.type");
        SqlbuilderStep step = null;

        switch (SqlbuilderStepType.of(type)) {
            case FILTER:
                step = mapper.readValue(stepJson, FilterStep.class);
                break;
            case COUNT:
                step = mapper.readValue(stepJson, CountStep.class);
                break;
            case SUM:
                step = mapper.readValue(stepJson, SumStep.class);
                break;
            case UNION:
                step = mapper.readValue(stepJson, UnionStep.class);
                break;
            case DISTINCT:
                step = mapper.readValue(stepJson, DistinctStep.class);
                break;
            case EXCEPT:
                step = mapper.readValue(stepJson, ExceptStep.class);
                break;
            case INNER_JOIN:
            case RIGHT_JOIN:
            case LEFT_JOIN:
                step = mapper.readValue(stepJson, JoinStep.class);
                break;
            case GROUP_CONCAT:
                step = mapper.readValue(stepJson, GroupConcatStep.class);
                break;
            default:
                step = mapper.readValue(stepJson, TableStep.class);
        }

        return step;
    }
}
