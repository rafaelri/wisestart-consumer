package com.wisestart.consumerservice.cli;

import com.wisestart.consumerservice.infrastructure.pokebalance.PokeBalanceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CommandLine implements CommandLineRunner {
    @Autowired
    PokeBalanceClient pokeBalanceClient;
    @Override
    public void run(String... args) throws Exception {
        if ("openAccount".equals(args[0])) {
            pokeBalanceClient.openAccount(args[1], new BigDecimal(args[2]));
        }
        else if ("getBalance".equals(args[0])) {
            var balance = pokeBalanceClient.getBalance(args[1]);
            System.out.printf("Balance for %s is %s%n", args[1], balance);
        }
    }
}
