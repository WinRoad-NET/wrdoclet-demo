package net.winroad.config;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

public class CustomerJsonSerializer {
    private final String INCLUDE_NAME = "json_include";
    private final String EXCLUDE_NAME = "json_exclude";
    private final LongDateConverter LONG_TIME_OBJECT_MAPPER = new LongDateConverter();
    private final ShortDateConverter SHORT_TIME_OBJECT_MAPPER = new ShortDateConverter();

    @JsonFilter("json_include")
    interface DynamicInclude {
    }

    @JsonFilter("json_exclude")
    interface DynamicExclude {
    }

    public void serializer(Class<?> clz, boolean shortDateFormat, String[] includes, String[] excludes) {
        if (clz == null) {
            return;
        }

        ObjectMapper objectMapper = shortDateFormat ? SHORT_TIME_OBJECT_MAPPER : LONG_TIME_OBJECT_MAPPER;
        if (ArrayUtils.isNotEmpty(includes)) {
            objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(INCLUDE_NAME,
                    SimpleBeanPropertyFilter.filterOutAllExcept(includes)));
            objectMapper.addMixIn(clz, DynamicInclude.class);
        } else if (ArrayUtils.isNotEmpty(excludes)) {
            objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(EXCLUDE_NAME,
                    SimpleBeanPropertyFilter.serializeAllExcept(excludes)));
            objectMapper.addMixIn(clz, DynamicExclude.class);
        }
    }

    public String toJson(Object object, boolean shortDateFormat) throws JsonProcessingException {
        ObjectMapper objectMapper = shortDateFormat ? SHORT_TIME_OBJECT_MAPPER : LONG_TIME_OBJECT_MAPPER;
        return objectMapper.writeValueAsString(object);
    }

    // 短时间格式：yyyy-MM-dd
    static class ShortDateConverter extends ObjectMapper {
        ShortDateConverter() {
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
                @Override
                public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
                    if (value == null) {
                    	jsonGenerator.writeString("");
                    }
                    jsonGenerator.writeString(SimpleDateFormatThreadLocal.get("yyyy-MM-dd").format(value));
                }
            });
            this.registerModule(simpleModule);
        }
    }

    // 长时间格式：yyyy-MM-dd HH:mm:ss
    static class LongDateConverter extends ObjectMapper {
        LongDateConverter() {
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
                @Override
                public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
                    if (value == null) {
                    	jsonGenerator.writeString("");
                    }
                    jsonGenerator.writeString(SimpleDateFormatThreadLocal.get("yyyy-MM-dd HH:mm:ss").format(value));
                }
            });
            this.registerModule(simpleModule);
        }
    }
}
