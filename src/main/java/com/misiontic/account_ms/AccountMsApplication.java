package com.misiontic.account_ms;

import com.misiontic.account_ms.models.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AccountMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountMsApplication.class, args);
        //Account micuenta = new Account("jhonmendz",50000, new Date());
    }
/*
    @GetMapping("/holamundo")
    public String holamundo(@RequestParam(value="nombre", defaultValue = "")String nombre, ) {
			var nombrelocal = nombre;
			System.out.println(nombre);
        return String.format("Hola mundo %s",nombrelocal);
    }*/
}
