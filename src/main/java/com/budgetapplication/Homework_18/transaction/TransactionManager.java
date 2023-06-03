package com.budgetapplication.Homework_18.transaction;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class TransactionManager {

    Transaction transaction;
    private final List<Transaction> transactionList = new ArrayList<>();

//    public TransactionManager() throws FileNotFoundException {
//        addingTransactionsToList();
//    }


    @PostConstruct
    private void addingTransactionsToList() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("src/main/resources/transactions.txt"));
        long id = 1;
        while (scanner.hasNext()) {
            String transaction = scanner.nextLine();
            String[] tokens = transaction.split(Pattern.quote("|"));
           Transaction tran = Transaction.builder()
                    .id(id++)
                    .product(tokens[0])
                    .transactionType(TransactionType.valueOf(tokens[1].toUpperCase()))
                    .amount(Double.parseDouble(tokens[2]))
                    .build();
           transactionList.add(tran);

        }
    }


    public List<Transaction> getAllTransactions() {
        return transactionList;
    }

    public List<Transaction> filterByParameter(String product, String type, Long minAmount, Long maxAmount) {
        if (maxAmount != null) {
            List<Transaction> filteredWithMaxAmount = getAllTransactions().stream()
                    .filter(transaction -> transaction.getProduct().equalsIgnoreCase(product))
                    .filter(transaction -> transaction.getTransactionType().equals(TransactionType.valueOf(type.toUpperCase())))
                    .filter(transaction -> transaction.getAmount() >= minAmount)
                    .filter(transaction -> transaction.getAmount() <= maxAmount)
                    .toList();
            return filteredWithMaxAmount;
        }

        List<Transaction> filteredListWithoutMaxAmount = getAllTransactions().stream()
                .filter(transaction -> transaction.getProduct().equalsIgnoreCase(product))
                .filter(transaction -> transaction.getTransactionType().equals(TransactionType.valueOf(type.toUpperCase())))
                .filter(transaction -> transaction.getAmount() >= minAmount)
                .toList();



        return filteredListWithoutMaxAmount;

        }





    public Transaction getTransactionById(long id) {
         return getAllTransactions().stream()
                .filter(transaction -> transaction.getId() == id)
                .findFirst()
                 .orElse(null);

    }

    public Transaction addNewTransaction(Transaction transaction) {

        // metoda se asigura ca nu exista doua ID-uri identice in lista
        long newId = transaction.getId();
        boolean idPresentInList = false;

        for (Transaction t : transactionList) {
            if (t.getId() == transaction.getId()) {
                idPresentInList = true;
                break;
            }
        }
        if (idPresentInList) {
            newId = (long) transactionList.get(transactionList.size() - 1).getId() + 1;
            System.out.println("Your given id [" + transaction.getId() + "] has been updated to - " + newId);
        }
       transaction = Transaction.builder()
                .id(newId)
                .product(transaction.getProduct())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .build();
        transactionList.add(transaction);

        return transaction;
    }

    public Transaction replaceTransactionWithId(Transaction transaction, long id) {

        Transaction existingTransaction = getTransactionById(id);
        transactionList.remove(existingTransaction);
         transaction = Transaction.builder()
                 // se pastreaza ID-ul tranzactiei sterse, restul field-urilor
                 // putand fi modificate
                 .id(id)
                 .product(transaction.getProduct())
                 .transactionType(transaction.getTransactionType())
                 .amount(transaction.getAmount())
                 .build();
         transactionList.add(transaction);
        return transaction;
    }

    public Transaction deleteTransactionWithId(long id) {

        Transaction existingTransaction = getTransactionById(id);
        transactionList.remove(existingTransaction);

        return existingTransaction;
    }

    public Map<TransactionType, List<Transaction>> getTransactionTypeReport() {

        Map<TransactionType, List<Transaction>> typeReport = new HashMap<>();
        List<Transaction> transactionsSell = new ArrayList<>();
        List<Transaction> transactionsBuy = new ArrayList<>();

        for (Transaction t : transactionList) {
            if (t.getTransactionType() == TransactionType.SELL) {
                transactionsSell.add(t);
                typeReport.put(TransactionType.SELL, transactionsSell);
            } else {
                transactionsBuy.add(t);
                typeReport.put(TransactionType.BUY, transactionsBuy);

            }
        }

        return typeReport;

    }

    public Map<String, List<Transaction>> getTransactionProductReport(String product) {

        Map<String, List<Transaction>> typeReport = new HashMap<>();
        List<Transaction> transactionsOfProduct = new ArrayList<>();

        for (Transaction t : transactionList) {
            if (t.getProduct().equalsIgnoreCase(product)) {
                transactionsOfProduct.add(t);
            }
        }
        typeReport.put(product, transactionsOfProduct);

        return typeReport;
    }

}
