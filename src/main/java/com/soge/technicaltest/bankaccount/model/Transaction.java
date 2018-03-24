package com.soge.technicaltest.bankaccount.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Transaction {
    @NotNull
    @Getter
    private final TransactionType transactionType;

    @NotNull
    @Getter
    private final TransactionStatus transactionStatus;

    @Getter
    private final String message;

    @NotNull
    @Getter
    private final LocalDateTime dateTime;

    @NotNull
    @Getter
    private final Double amount;

    @NotNull
    @Getter
    private final Double balance;

    @NotNull
    @Getter
    private final String currency;
}
