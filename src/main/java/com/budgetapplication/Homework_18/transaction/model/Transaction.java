package com.budgetapplication.Homework_18.transaction.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Transaction {


    @Id
    @GeneratedValue
    private long id;

    @Column
    private String product;

    @Column
    private TransactionType transactionType;

    @Column
    private double amount;

    

}
