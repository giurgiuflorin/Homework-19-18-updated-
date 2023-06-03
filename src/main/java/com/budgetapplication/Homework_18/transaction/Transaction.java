package com.budgetapplication.Homework_18.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class Transaction {

    private final long id;
    private final String product;

    private final TransactionType transactionType;
    private final double amount;

    

}
