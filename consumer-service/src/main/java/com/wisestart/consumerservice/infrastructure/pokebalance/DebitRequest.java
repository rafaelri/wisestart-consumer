package com.wisestart.consumerservice.infrastructure.pokebalance;

import java.math.BigDecimal;

public record DebitRequest(String account, BigDecimal amount, String description) {
}
