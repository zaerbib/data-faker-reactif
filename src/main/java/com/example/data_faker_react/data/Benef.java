package com.example.data_faker_react.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Benef {
    private Double diff;
    private Double invest;
    private String state;
}
