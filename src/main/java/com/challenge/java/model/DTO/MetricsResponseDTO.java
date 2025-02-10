package com.challenge.java.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MetricsResponseDTO {

    double average;
    double standardDeviation;
}
