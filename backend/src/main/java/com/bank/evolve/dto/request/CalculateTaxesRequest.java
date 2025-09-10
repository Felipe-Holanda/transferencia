package com.bank.evolve.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CalculateTaxesRequest {

    @NotNull(message = "Você deve informar o valor da transferência")
    private Double amount;

    @NotNull(message = "Você deve informar a data do agendamento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
}
