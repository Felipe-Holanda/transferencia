package com.bank.evolve.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DepositRequest {

    @NotNull
    private Double amount = 0.0;

}
