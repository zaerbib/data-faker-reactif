package com.example.data_faker_react.data;

import lombok.*;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Document
public class DataReactiveFlow {
    @BsonId
    @BsonRepresentation(value = BsonType.OBJECT_ID)
    private String id;
    @Field
    private Double open;
    @Field
    private Double close;
    @Field
    private Double volume;
    @Field
    private Double splitFactor;
    @Field
    private Double dividend;
    @Field
    private String symbol;
    @Field
    private String exchange;
    @Field
    private LocalDateTime date;
    @Field
    private Benef benef;
}
