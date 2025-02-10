package com.challenge.java.model.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientWithLifeExpectancyDTO {

    private ClientDTO clientDTO;

    private Double lifeExpectancy;
}
