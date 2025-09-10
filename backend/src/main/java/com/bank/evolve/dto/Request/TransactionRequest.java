package com.bank.evolve.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class TransactionRequest {

    @NotBlank(message = "Você deve informar a conta de destino")
    private String targetAccount;

    @NotBlank(message = "Você deve informar o valor da transação")
    private Double amount;

    @NotBlank(message = "Você deve informar a data da transação")
    private Date transactionDate;

    private String description;
}
