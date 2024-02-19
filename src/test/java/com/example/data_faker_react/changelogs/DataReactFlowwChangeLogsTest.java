package com.example.data_faker_react.changelogs;

import com.example.data_faker_react.utils.JsonUtilsTest;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.mongock.driver.mongodb.reactive.util.SubscriberSync;
import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DataReactFlowwChangeLogsTest {

    @Mock
    private MongoClient mongoClient;

    @Mock
    private MongoDatabase mongoDatabase;

    @Mock
    private MongoCollection mongoCollection;

    @Mock
    SubscriberSync subscriberSync;


    @BeforeAll
    public void initMocks() {

    }

    private Map changeSetPrepareTest(final String changeSetNumber) {
        final String inputJson = "data/data-faker-react-set-"+changeSetNumber+"-in.json";
        final String outputJson = "data/data-faker-react-set-"+changeSetNumber+"-out.json";
        final Map input = JsonUtilsTest.jsonToPojo(inputJson, Map.class);
        final Map output = JsonUtilsTest.jsonToPojo(outputJson, Map.class);

        return null;
    }
}
