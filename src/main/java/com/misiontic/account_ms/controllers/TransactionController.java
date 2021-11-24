package com.misiontic.account_ms.controllers;

import com.misiontic.account_ms.exceptions.AccountNotFoundException;
import com.misiontic.account_ms.exceptions.InsufficientBalanceException;
import com.misiontic.account_ms.models.Account;
import com.misiontic.account_ms.models.Transaction;
import com.misiontic.account_ms.repositories.AccountRepository;
import com.misiontic.account_ms.repositories.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TransactionController {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionController(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
    @PostMapping("/transaction")
    Transaction newTransaction(@RequestBody Transaction transaction) {
        Account accountOrigin = accountRepository.findById(transaction.getUsernameOrigin()).orElse(null);
        Account accountDestiny = accountRepository.findById(transaction.getUsernameDestiny()).orElse(null);
        if(accountOrigin == null){
            //la cuenta de origen no existe
            throw new AccountNotFoundException("No existe una cuenta origen con el nombre de "+ transaction.getUsernameOrigin());
        }
        if(accountDestiny  == null){
          //la cuenta destino no existe
            throw new AccountNotFoundException("No existe una cuenta destino con el nombre de "+ transaction.getUsernameDestiny());
        }
        //        5000                           10000
        if(accountOrigin.getBalance() < transaction.getValue()){
            //saldo insuficiente
            throw  new InsufficientBalanceException("saldo insuficiente");
        }

        //                                   5000                   2000
        accountOrigin.setBalance(accountOrigin.getBalance()-transaction.getValue());
        accountOrigin.setLastChange(new Date());
        accountRepository.save(accountOrigin);
          //                              5000                     2000
        accountDestiny.setBalance(accountDestiny.getBalance()+transaction.getValue());
        accountDestiny.setLastChange(new Date());
        accountRepository.save(accountDestiny);

        transaction.setDate(new Date());

        return transactionRepository.save(transaction);
    }

    @GetMapping("/transactions/{username}")
    List<Transaction> userTransaction(@PathVariable String username){
        Account userAccount = accountRepository.findById(username).orElse(null);
        if (userAccount == null)
            throw new AccountNotFoundException("No se encontro una cuenta con el username: " + username);

        List<Transaction> transactionsOrigin = transactionRepository.findByUsernameOrigin(username);
        List<Transaction> transactionsDestinity = transactionRepository.findByUsernameDestiny(username);

        List<Transaction> transactions = Stream.concat(transactionsOrigin.stream(), transactionsDestinity.stream()).collect(Collectors.toList());

        return transactions;
    }

}
