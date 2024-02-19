package com.example.data_faker_react.utils;

import com.example.data_faker_react.data.DataReactiveFlow;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@UtilityClass
@Slf4j
public class JsonUtilsTest {

    private ObjectMapper jsonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.registerModules(Arrays.asList(new ParameterNamesModule(), new Jdk8Module(), new JavaTimeModule()));

        return mapper;
    }

    public <T> T jsonToPojo(String jsonFilePath, Class<T> clazz) {
        try {
            InputStream is = DataReactiveFlow.class.getClassLoader().getResourceAsStream(jsonFilePath);
            return jsonObjectMapper().readValue(is, clazz);
        } catch (Exception e) {
            log.error("Error reading from json file {}", jsonFilePath);
            log.debug(e.getMessage());
            return null;
        }
    }

    public <T> List<T> jsonToListPojos(String jsonFilePath, Class<T> clazz) {
        InputStream is = DataReactiveFlow.class.getClassLoader().getResourceAsStream(jsonFilePath);
        CollectionType javaType = jsonObjectMapper().getTypeFactory().constructCollectionType(List.class, clazz);

        try {
            if (is == null) {
                throw new Exception();
            } else {
                return (List<T>) jsonObjectMapper().readValue(is.readAllBytes(), javaType);
            }
        } catch (Exception e) {
            log.error("Error reading from json file {}", jsonFilePath);
            log.debug(e.getMessage());
            return null;
        }
    }

    public List<Map<Object, Object>> jsonToListOfPojos(String jsonFilePath) {
        InputStream is = DataReactiveFlow.class.getClassLoader().getResourceAsStream(jsonFilePath);
        CollectionType javaType = jsonObjectMapper().getTypeFactory().constructCollectionType(List.class, Map.class);

        try {
            if (is == null) {
                throw new Exception();
            } else {
                return jsonObjectMapper().readValue(is.readAllBytes(), javaType);
            }
        } catch (Exception e) {
            log.error("Error reading from json file {}", jsonFilePath);
            log.debug(e.getMessage());
            return null;
        }
    }

    public String jsonToString(Object object) {
        try {
            return jsonObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    public Object jsonToObject(String json, Class cl) {
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.registerModules(new JavaTimeModule());
        jsonMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        InputStream inputStream = JsonUtils.class.getResourceAsStream(json);

        try {
            return jsonMapper.readValue(inputStream, cl);
        } catch (Exception e) {
            log.error("jsonTo - error", e);
            return null;
        }
    }

    public Object jsonToMap(String json, TypeReference typeReference) {
        ObjectMapper jsonMapper = new ObjectMapper();
        InputStream inputStream = TypeReference.class.getResourceAsStream(json);

        try {
            return jsonMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error("JsonToMap error", e);
            return null;
        }
    }

    public static String readFile(String filePath) {
        InputStream inputStream = TypeReference.class.getResourceAsStream(filePath);
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

}
