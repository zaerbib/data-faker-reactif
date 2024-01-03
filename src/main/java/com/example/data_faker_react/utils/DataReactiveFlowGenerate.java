package com.example.data_faker_react.utils;

import com.example.data_faker_react.data.Benef;
import com.example.data_faker_react.data.DataReactiveFlow;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataReactiveFlowGenerate {
    private static Faker faker = new Faker();

    public static DataReactiveFlow generateOnDataFlow() {
        return DataReactiveFlow.builder()
                .open(faker.number().randomDouble(2, 90, 200))
                .close(faker.number().randomDouble(2, 90, 200))
                .volume(faker.number().randomDouble(0, 1000, 1000000))
                .splitFactor(faker.number().randomDouble(2, 0, 1))
                .dividend(faker.number().randomDouble(2, 0, 1))
                .symbol(faker.money().currencyCode())
                .exchange(faker.money().currency())
                .date(LocalDateTime.parse(faker.date().future(1, TimeUnit.DAYS, "YYYY-MM-dd HH:mm:ss"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    public static List<DataReactiveFlow> generateNDataFlow(Integer itemNumber) {
        return IntStream.range(0, itemNumber).mapToObj(item -> generateOnDataFlow()).toList();
    }

    public static List<List<DataReactiveFlow>> paritionList(List<DataReactiveFlow> dataFlows, Integer chunkSize) {
        AtomicInteger counter = new AtomicInteger();
        return dataFlows.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement()/chunkSize))
                .values().stream().toList();
    }

    public static DataReactiveFlow jsonParserToDataReactiveFlow(String json) {
        DataReactiveFlow flow;
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        try {
            JsonParser parser = factory.createParser(json);
            flow = parser.readValueAs(DataReactiveFlow.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return flow;
    }

    public static Benef jsonParserToBenefFlow(String json) {
        Benef flow;
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        try {
            JsonParser parser = factory.createParser(json);
            flow = parser.readValueAs(Benef.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return flow;
    }
}
