package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPipeline {

    private List<CheckoutStep> steps = new ArrayList<>();

    public PaymentSummary checkout(Basket basket, List<Promotion> promotions) {
        CheckoutContext checkoutContext = new CheckoutContext(basket, promotions);
        for (CheckoutStep checkoutStep : steps) {
            checkoutStep.execute(checkoutContext);
        }

        return checkoutContext.paymentSummary();
    }

    public void addStep(CheckoutStep checkoutStep) {
        steps.add(checkoutStep);
    }
}
