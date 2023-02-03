package com.interview.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPipelineTest {

    @Mock
    private PricingService pricingService;

    @Mock
    private CheckoutPipeline checkoutPipeline;

    @Mock
    private CheckoutContext checkoutContext;
    @Mock
    Basket basket;

    @Mock
    CheckoutStep checkoutStep1;

    @Mock
    CheckoutStep checkoutStep2;

    @BeforeEach
    void setup() {
        pricingService = Mockito.mock(PricingService.class);
        checkoutContext = Mockito.mock(CheckoutContext.class);

        checkoutStep1 = new BasketConsolidationCheckoutStep();
        checkoutStep2 = new RetailPriceCheckoutStep(pricingService);
        checkoutPipeline = new CheckoutPipeline();

        basket = new Basket();
    }

    @Test
    void returnZeroPaymentForEmptyPipeline() {
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket, null);

        assertEquals(paymentSummary.getRetailTotal(), 0.0);
    }

    @Test
    void executeAllPassedCheckoutSteps() {

        basket.add("product1", "myproduct1", 20);
        basket.add("product2", "myproduct2", 11);
        basket.add("product3", "myproduct3", 5);
        basket.add("product4", "myproduct4", 10);

        checkoutPipeline = new CheckoutPipeline();

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion("product1", Discounts.TEN_PERCENT_OFF));
        promotions.add(new Promotion("product2", Discounts.TWO_FOR_ONE));
        promotions.add(new Promotion("product3", Discounts.FIFTY_PERCENT_OFF));
        when(checkoutContext.getPromotions()).thenReturn(promotions);

        when(pricingService.getPrice("product1")).thenReturn(10.0);
        when(pricingService.getPrice("product2")).thenReturn(20.0);
        when(pricingService.getPrice("product3")).thenReturn(30.0);
        when(pricingService.getPrice("product4")).thenReturn(10.0);

        checkoutPipeline.addStep(checkoutStep1);
        checkoutPipeline.addStep(checkoutStep2);

        PaymentSummary summary = checkoutPipeline.checkout(basket, promotions);

        assertEquals(540, summary.getRetailTotal());

    }



}
