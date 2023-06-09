package com.budgetapplication.Homework_18.transaction.controller;

import com.budgetapplication.Homework_18.transaction.model.Transaction;
import com.budgetapplication.Homework_18.transaction.manager.TheTransactionManager;
import com.budgetapplication.Homework_18.transaction.model.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TheTransactionManager theTransactionManager;



    //GET /transactions - get all transactions
    @GetMapping("/all")
    public List<Transaction> getAll() {
        return theTransactionManager.getAllTransactions();
    }

    //GET /transactions/{id} - get transaction with id
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable long id) {
        return theTransactionManager.getTransactionById(id);
    }

    //GET transactions, filter by product
    @GetMapping("/filter")
    //exemplu:localhost:8080/transactions/filter?product=dress&type=sell&minAmount=1&maxAmount=100
    public List<Transaction> filterByProductAndType(@RequestParam String product,
                                                    @RequestParam String type,
                                                    @RequestParam Long minAmount,
                                                    @RequestParam(required = false) Long maxAmount) {
        return theTransactionManager.filterByParameter(product, type, minAmount, maxAmount);
    }

    //POST /transactions - adds a new transaction
    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return theTransactionManager.addNewTransaction(transaction);
    }

    //PUT  /transactions/{id} - replaces the transaction with id

    @PutMapping("/{id}")
    public Transaction replaceTransactionUsingID(@RequestBody Transaction transaction,
                                                 @PathVariable long id) {
        return theTransactionManager.replaceTransactionWithId(transaction, id);
    }

    //DELETE /transactions/{id} - deletes the transaction with id
    @DeleteMapping("/{id}")
    public Transaction deleteTransactionUsingId(@PathVariable long id) {
        return theTransactionManager.deleteTransactionWithId(id);
    }

    //GET /transactions/reports/type -> returns a map from type to list of transactions of that type
    @GetMapping("/type-report")
    public Map<TransactionType, List<Transaction>> getReportByType() {
        return theTransactionManager.getTransactionTypeReport();
    }

    // GET /transactions/reports/product -> returns a map from product to list of transactions for that product
    @GetMapping("/product-report/{product}") //http://localhost:8080/transactions/product-report/dress
    public Map<String, List<Transaction>> getReportForProduct(@PathVariable String product) {
        return theTransactionManager.getTransactionProductReport(product);
    }
}
