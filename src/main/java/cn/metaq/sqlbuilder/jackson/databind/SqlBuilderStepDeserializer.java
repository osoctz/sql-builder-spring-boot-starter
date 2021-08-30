package cn.metaq.sqlbuilder.jackson.databind;

import cn.metaq.sqlbuilder.SqlBuilderStep;
import cn.metaq.sqlbuilder.constants.SqlBuilderStepType;
import cn.metaq.sqlbuilder.step.FilterStep;
import cn.metaq.sqlbuilder.step.TableStep;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;


import java.io.IOException;

import static cn.metaq.sqlbuilder.constants.SqlBuilderStepType.*;

public class SqlBuilderStepDeserializer extends JsonDeserializer<SqlBuilderStep> {

    @Override
    public SqlBuilderStep deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        //表达式json
        JsonNode node = jp.getCodec().readTree(jp);

        String stepJson = node.toString();
        String type = JsonPath.read(stepJson, "$.type");
        SqlBuilderStep step = null;

        switch (SqlBuilderStepType.of(type)) {
            case TABLE:
                step = mapper.readValue(stepJson, TableStep.class);
                break;
            case FILTER:
                step = mapper.readValue(stepJson, FilterStep.class);
                break;
            default:
                step = mapper.readValue(stepJson, TableStep.class);
        }

        return step;
    }
}
