package com.bank.evolve.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionRequest {

    @NotBlank(message = "Você deve informar a conta de destino")
    private String targetAccount;

    @NotNull(message = "Você deve informar o valor da transação")
    private Double amount;

    @NotNull(message = "Você deve informar a data do agendamento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    private String description;
}
