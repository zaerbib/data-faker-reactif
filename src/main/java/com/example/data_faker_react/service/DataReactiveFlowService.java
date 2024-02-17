package com.example.data_faker_react.service;

import com.example.data_faker_react.data.DataReactiveFlow;
import com.example.data_faker_react.utils.DataReactiveFlowGenerate;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataReactiveFlowService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final Executor fakerExecutor;

    public DataReactiveFlowService(ReactiveMongoTemplate reactiveMongoTemplate,
                                   Executor fakerExecutor) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.fakerExecutor = fakerExecutor;
    }

    public Mono<DataReactiveFlow> generateOne() {
        return reactiveMongoTemplate.insert(DataReactiveFlowGenerate.generateOnDataFlow());
    }

    public Mono<Integer> generate100K() {
        return generateNDataFlow(100000);
    }

    public Mono<Integer> generate10K() {
        return generateNDataFlow(10000);
    }

    public Mono<Integer> generate1M() {
        return generateNDataFlow(1000000);
    }

    public Flux<Map<String, List<DataReactiveFlow>>> getDataFlowIndeticSymbol() {
        return reactiveMongoTemplate.findAll(DataReactiveFlow.class)
                .publishOn(Schedulers.boundedElastic())
                .buffer()
                .map(item -> item.stream().collect(Collectors.groupingBy(DataReactiveFlow::getSymbol)));
    }

    private Mono<Integer> generateNDataFlow(Integer number) {
        return Flux.fromIterable(DataReactiveFlowGenerate.paritionList(DataReactiveFlowGenerate.generateNDataFlow(number), 500))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(item -> CompletableFuture.supplyAsync(() -> reactiveMongoTemplate.insertAll(item).subscribe(), fakerExecutor))
                .map(List::size)
                .reduce(0, Integer::sum);
    }

    public Mono<DeleteResult> deleteAll() {
        return reactiveMongoTemplate.remove(DataReactiveFlow.class)
                .all();
    }
}
