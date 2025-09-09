package com.bank.evolve.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TransferTaxesRequest {

    @NotBlank(message = "Para cadastrar uma taxa, você deve informar o número de dias.")
    private int amountDays;

    @NotBlank(message = "Para cadastrar uma taxa, você deve informar o valor de taxa fixo.")
    private double fixedTax;

    @NotBlank(message = "Para cadastrar uma taxa, você deve informar o percentual de taxa.")
    private double taxPercentage;

}
