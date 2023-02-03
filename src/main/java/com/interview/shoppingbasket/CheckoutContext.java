package com.interview.shoppingbasket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
class CheckoutContext {
    private Basket basket;
    private double retailPriceTotal = 0.0;
    private List<Promotion> promotions;


    CheckoutContext(Basket basket, List<Promotion> promotions) {
        this.basket = basket;
        this.promotions = promotions;
    }

    public PaymentSummary paymentSummary() {
        return new PaymentSummary(retailPriceTotal);
    }

}
