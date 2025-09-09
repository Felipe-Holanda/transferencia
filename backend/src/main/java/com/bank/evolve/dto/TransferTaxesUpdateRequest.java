package com.bank.evolve.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferTaxesUpdateRequest {

    private int amountDays;

    private double fixedTax;

    private double taxPercentage;

}
