package com.wisestart.consumerservice.infrastructure.pokebalance;

import java.math.BigDecimal;

public record OpenAccountRequest(String account, BigDecimal initialBalance) {
}
