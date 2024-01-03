package com.example.data_faker_react.utils;

import com.example.data_faker_react.data.DataReactiveFlow;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Slf4j
public class DataReactiveFlowChangeLogsUtils {
    private static final String DATA_REACT_FLOW = "dataReactiveFlow";
    private static final int BATCH_SIZE = 5000;

    public static void changeUnitOnList(ReactiveMongoTemplate reactiveMongoTemplate,
                                        Consumer<DataReactiveFlow> update) {
        reactiveMongoTemplate.findDistinct("id", DataReactiveFlow.class, ObjectId.class)
                .publishOn(Schedulers.boundedElastic())
                .map(item -> reactiveMongoTemplate.findById(item, DataReactiveFlow.class, DATA_REACT_FLOW))
                .map(item -> item.subscribe(update))
                .doOnComplete(() -> log.info("Stream completed !!!"))
                .subscribe();

    }
}
