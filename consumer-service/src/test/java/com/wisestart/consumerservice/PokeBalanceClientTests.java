package com.wisestart.consumerservice;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.wisestart.consumerservice.infrastructure.pokebalance.PokeBalanceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "PokeBalance", port = "8080")
public class PokeBalanceClientTests {
    @Autowired
    PokeBalanceClient pokeBalanceClient;

    @Pact(consumer="PokeConsumer")
    public RequestResponsePact openAccount(PactDslWithProvider builder) {
        DslPart openAccountRequestBody = new PactDslJsonBody().stringType("account").decimalType("initialBalance");
        return builder
                .uponReceiving("OpenAccount")
                .path("/api/balance/accounts")
                .body(openAccountRequestBody)
                .matchHeader("Content-type", "application/json.*", "application/json")
                .method("POST")
                .willRespondWith()
                .status(200)
                .toPact();
    }

    @Pact(consumer="PokeConsumer")
    public RequestResponsePact getBalance(PactDslWithProvider builder) {
        DslPart getBalanceResponseBody = new PactDslJsonBody().stringType("account", "12345").decimalType("balance", new BigDecimal("100.0"));
        return builder
                .given("account 12345 with balance of 100 exists")
                .uponReceiving("GetBalance")
                .path("/api/balance/accounts/12345")
                .matchHeader("Accept", "application/json.*", "application/json")
                .method("GET")
                .willRespondWith()
                .body(getBalanceResponseBody)
                .status(200)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod="openAccount")
    public void openAccountTest() {
        pokeBalanceClient.openAccount("1234", new BigDecimal("100.5"));
    }

    @Test
    @PactTestFor(pactMethod = "getBalance")
    public void getBalanceTest() {
        var response = pokeBalanceClient.getBalance("12345");
        assertNotNull("balance", response);
    }
}
