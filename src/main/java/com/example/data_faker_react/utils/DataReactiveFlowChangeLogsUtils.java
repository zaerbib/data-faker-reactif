package com.example.data_faker_react.utils;

import com.example.data_faker_react.data.DataReactiveFlow;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.mongock.driver.mongodb.reactive.util.MongoSubscriberSync;
import io.mongock.driver.mongodb.reactive.util.SubscriberSync;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;

@Slf4j
public class DataReactiveFlowChangeLogsUtils {
    public static final String DATA_REACT_FLOW = "dataReactiveFlow";
    private static final int BATCH_SIZE = 5000;

    public static void changeUnitExecution(MongoDatabase mongoDatabase, Function<DataReactiveFlow, UpdateResult> dataUpdater) {
        SubscriberSync<DataReactiveFlow> subscriber = new MongoSubscriberSync<>();
        MongoCollection<DataReactiveFlow> clientCollection = mongoDatabase.getCollection(DATA_REACT_FLOW, DataReactiveFlow.class);
        clientCollection.find().subscribe(subscriber);

        List<UpdateResult> list = subscriber.get().stream().map(dataUpdater).toList();

        log.info("Completed with "+list.size()+" items processed");

    }
}
