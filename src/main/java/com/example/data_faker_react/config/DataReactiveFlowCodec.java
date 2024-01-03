package com.example.data_faker_react.config;

import com.example.data_faker_react.data.DataReactiveFlow;
import com.example.data_faker_react.utils.DataReactiveFlowGenerate;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class DataReactiveFlowCodec implements Codec<DataReactiveFlow> {
    @Override
    public DataReactiveFlow decode(BsonReader bsonReader, DecoderContext decoderContext) {
        String json = bsonReader.readString();
        return DataReactiveFlowGenerate.jsonParserToDataReactiveFlow(json);
    }

    @Override
    public void encode(BsonWriter bsonWriter, DataReactiveFlow dataReactiveFlow, EncoderContext encoderContext) {
        Document document = new Document();
        document.append("id", dataReactiveFlow.getId());
        document.append("open", dataReactiveFlow.getOpen());
        document.append("close", dataReactiveFlow.getClose());
        document.append("volume", dataReactiveFlow.getVolume());
        document.append("splitFactor", dataReactiveFlow.getSplitFactor());
        document.append("dividend", dataReactiveFlow.getDividend());
        document.append("symbol", dataReactiveFlow.getSymbol());
        document.append("exchange", dataReactiveFlow.getExchange());
        document.append("date", dataReactiveFlow.getDate());
        document.append("benef", dataReactiveFlow.getBenef());

        bsonWriter.writeString(document.toJson());
    }

    @Override
    public Class<DataReactiveFlow> getEncoderClass() {
        return DataReactiveFlow.class;
    }
}
