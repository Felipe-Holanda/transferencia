package com.bank.evolve.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TransferTaxesRequest {

    @NotNull(message = "Para cadastrar uma taxa, você deve informar o número de dias.")
    private int amountDays;

    @NotNull(message = "Para cadastrar uma taxa, você deve informar o valor de taxa fixo.")
    private double fixedTax;

    @NotNull(message = "Para cadastrar uma taxa, você deve informar o percentual de taxa.")
    private double taxPercentage;

}
