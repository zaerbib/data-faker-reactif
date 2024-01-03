package com.example.data_faker_react.changeDb;

import com.example.data_faker_react.data.Benef;
import com.example.data_faker_react.data.DataReactiveFlow;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import static com.example.data_faker_react.utils.DataReactiveFlowChangeLogsUtils.changeUnitOnList;

@Slf4j
@ChangeUnit(id="add-field-change-react", order = "001", author = "dev")
public class AddFieldChange {

    private static final String COLLECTION = "dataReactiveFlow";

    @Execution
    public void execution(ReactiveMongoTemplate reactiveMongoTemplate) {
        changeUnitOnList(reactiveMongoTemplate, this::makeChange);
    }

    @RollbackExecution
    public void rollBack(ReactiveMongoTemplate reactiveMongoTemplate) {
        // think to rollBack
    }

    private void makeChange(DataReactiveFlow dataReactiveFlow) {
        Double diff = dataReactiveFlow.getClose() - dataReactiveFlow.getOpen();
        Double invest = dataReactiveFlow.getVolume() - (dataReactiveFlow.getVolume() * dataReactiveFlow.getDividend());
        dataReactiveFlow.setBenef(Benef.builder()
                .diff(diff)
                .invest(invest)
                .build());
    }

}
