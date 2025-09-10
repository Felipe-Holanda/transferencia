package com.bank.evolve.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelTransactionRequest {
    private String reason;
}
