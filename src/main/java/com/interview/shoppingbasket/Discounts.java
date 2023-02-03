package com.interview.shoppingbasket;

import lombok.Getter;

@Getter
public enum Discounts {

    NONE(0),
    TWO_FOR_ONE(2),
    FIFTY_PERCENT_OFF( 0.5),
    TEN_PERCENT_OFF(0.1);


    private final double promotion;

    Discounts(double promotion) {
        this.promotion = promotion;
    }

}
