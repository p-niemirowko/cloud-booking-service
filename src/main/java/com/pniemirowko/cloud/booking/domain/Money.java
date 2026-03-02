package com.pniemirowko.cloud.booking.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Money {

    private BigDecimal amount;
    private String currency; // todo add possible currencies

}
