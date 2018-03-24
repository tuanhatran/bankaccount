package com.soge.technicaltest.bankaccount.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Data
public class Account {
    @NotNull
    private String accountId;

    @NotNull
    private Double balance;

    @NotNull
    private String currency;

    @NotNull
    private final List<Transaction> transactionHistory;
}
