package com.example.data_faker_react.config;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class DataReactiveFlowProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if(aClass.equals(DataReactiveFlowCodec.class)) {
            return (Codec<T>) new DataReactiveFlowCodec();
        }
        return null;
    }
}
