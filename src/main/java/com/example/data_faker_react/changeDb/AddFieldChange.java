package com.example.data_faker_react.changeDb;

import com.example.data_faker_react.data.Benef;
import com.example.data_faker_react.data.DataReactiveFlow;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import io.mongock.driver.mongodb.reactive.util.MongoSubscriberSync;
import io.mongock.driver.mongodb.reactive.util.SubscriberSync;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.function.Function;

import static com.example.data_faker_react.utils.DataReactiveFlowChangeLogsUtils.DATA_REACT_FLOW;
import static com.example.data_faker_react.utils.DataReactiveFlowChangeLogsUtils.changeUnitExecution;

@Slf4j
@ChangeUnit(id="add-field-change-react", order = "001", author = "dev")
public class AddFieldChange {

    private static final String COLLECTION = "dataReactiveFlow";

    @Execution
    public void execution(MongoDatabase mongoDatabase) {
        changeUnitExecution(mongoDatabase, getDataUpdate(mongoDatabase));
    }

    @RollbackExecution
    public void rollBack(MongoDatabase mongoDatabase) {
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

    private Function<DataReactiveFlow, UpdateResult> getDataUpdate(MongoDatabase mongoDatabase) {
        MongoCollection<DataReactiveFlow> collection = mongoDatabase.getCollection(DATA_REACT_FLOW, DataReactiveFlow.class);
        return dataReactiveFlow -> {
            SubscriberSync<UpdateResult> updateSubcriber = new MongoSubscriberSync<>();
            Double diff = dataReactiveFlow.getClose() - dataReactiveFlow.getOpen();
            Double invest = dataReactiveFlow.getVolume() - (dataReactiveFlow.getVolume() * dataReactiveFlow.getDividend());
            dataReactiveFlow.setBenef(Benef.builder()
                    .diff(diff)
                    .invest(invest)
                    .build());
            collection.updateOne(getQuery(dataReactiveFlow), fromDataToDocumentUpdate(dataReactiveFlow)).subscribe(updateSubcriber);
            return updateSubcriber.getFirst();
        };
    }

    private Document getQuery(DataReactiveFlow dataReactiveFlow) {
        return new Document("_id", new ObjectId(dataReactiveFlow.getId()));
    }

    private Document fromDataToDocumentUpdate(DataReactiveFlow dataReactiveFlow) {
        Document document = new Document();
        document.append("benef", dataReactiveFlow.getBenef());

        return new Document("$set",
                new Document("benef", document.get("benef")));
    }

}
