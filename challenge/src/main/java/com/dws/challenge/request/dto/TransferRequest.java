package com.dws.challenge.request.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferRequest {

    @NotNull(message= "Account from ID cannot be null")
    private String accountFromId;

    @NotNull(message= "Account to ID cannot be null")
    private String accountToId;
    
    @Min(value=1, message= "Trasfer amount must be greater than zero")
    private BigDecimal amount;
}
