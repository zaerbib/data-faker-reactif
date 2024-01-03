package com.example.data_faker_react.config;

import com.example.data_faker_react.data.Benef;
import com.example.data_faker_react.utils.DataReactiveFlowGenerate;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class BenefCodec implements Codec<Benef> {
    @Override
    public Benef decode(BsonReader bsonReader, DecoderContext decoderContext) {
        String json = bsonReader.readString();
        return DataReactiveFlowGenerate.jsonParserToBenefFlow(json);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Benef benef, EncoderContext encoderContext) {
        Document document = new Document();
        document.append("diff", benef.getDiff());
        document.append("invest", benef.getInvest());

        bsonWriter.writeString(document.toJson());
    }

    @Override
    public Class<Benef> getEncoderClass() {
        return Benef.class;
    }
}
