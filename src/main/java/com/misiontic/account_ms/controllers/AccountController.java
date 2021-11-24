package com.misiontic.account_ms.controllers;

import com.misiontic.account_ms.exceptions.AccountNotFoundException;
import com.misiontic.account_ms.models.Account;
import com.misiontic.account_ms.repositories.AccountRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/account/{username}")
    Account getAccount(@PathVariable String username) {
        return accountRepository.findById(username).orElseThrow(() -> new AccountNotFoundException("No se encontró una cuenta con el nombre " + username));
    }

    //{
// "username":"jmendez",
//    "balance": 500000,
//   "date": "2021-12-25"
// }
    @PostMapping("/account")
    Account newAccount(@RequestBody Account account) {
        Account accountVerify = accountRepository.findById(account.getUsername()).orElse(null);
        if (accountVerify != null) {
            //la cuenta de origen no existe
            throw new AccountNotFoundException("Ya existe una cuenta con el nombre de " + account.getUsername());
        }
        return accountRepository.save(account);
        //db.account.insert({...})
    }
/*
 @DeleteMapping("/account/{username}")
 String deleteAccount(@PathVariable String username){
   accountRepository.deleteById(username);
  return ("Borrado exitoso");
 } */

}
