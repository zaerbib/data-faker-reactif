package com.example.data_faker_react.controller;

import com.example.data_faker_react.data.DataReactiveFlow;
import com.example.data_faker_react.service.DataReactiveFlowService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactor/flow")
public class DataReactiveFowController {

    private final DataReactiveFlowService dataReactiveFlowService;


    public DataReactiveFowController(DataReactiveFlowService dataReactiveFlowService) {
        this.dataReactiveFlowService = dataReactiveFlowService;
    }

    @GetMapping("one")
    public Mono<DataReactiveFlow> generateOne() {
        return dataReactiveFlowService.generateOne();
    }

    @GetMapping("10K")
    public Mono<Integer> generate10K() {
        return dataReactiveFlowService.generate10K();
    }

    @GetMapping("100K")
    public Mono<Integer> generate100K() {
        return dataReactiveFlowService.generate100K();
    }

    @GetMapping("1M")
    public Mono<Integer> generate1M() {
        return dataReactiveFlowService.generate1M();
    }

    @DeleteMapping("deleteAll")
    public Mono<DeleteResult> deleteAll() {
        return dataReactiveFlowService.deleteAll();
    }
}
