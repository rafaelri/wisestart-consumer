package com.wisestart.consumerservice.infrastructure.pokebalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class PokeBalanceClient {
    @Autowired
    private RestTemplate restTemplate;
    public void openAccount(String accountNumber, BigDecimal initialBalance) {
        restTemplate.postForObject("/balance/accounts", new OpenAccountRequest(accountNumber, initialBalance), Void.class);
    }

    public BigDecimal getBalance(String accountNumber) {
        return restTemplate.getForObject(String.format("/balance/accounts/%s", accountNumber), AccountBalanceResponse.class).balance();
    }

    public void credit(CreditRequest request) {
        restTemplate.postForObject("/balance/credit", request, Void.class);
    }

    public void debit(DebitRequest request) {
        restTemplate.postForObject("/balance/debit", request, Void.class);
    }
}
