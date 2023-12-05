package com.wisestart.consumerservice.infrastructure.pokebalance;

import java.math.BigDecimal;

public record CreditRequest(String account, BigDecimal amount, String description) {
}
