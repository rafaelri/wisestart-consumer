package com.wisestart.consumerservice.infrastructure.pokebalance;

import java.math.BigDecimal;


public record AccountBalanceResponse(String account, BigDecimal balance) {
}
