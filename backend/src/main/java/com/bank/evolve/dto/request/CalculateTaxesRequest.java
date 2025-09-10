package com.bank.evolve.dto.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CalculateTaxesRequest {

    @NotNull(message = "Você deve informar o valor da transferência")
    private Double amount;

    @NotNull(message = "Você deve informar a data do agendamento")
    private Date targetDate;
}
