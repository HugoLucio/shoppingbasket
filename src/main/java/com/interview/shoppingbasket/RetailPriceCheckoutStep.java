package com.interview.shoppingbasket;

import java.util.List;

import static com.interview.shoppingbasket.Discounts.*;

public class RetailPriceCheckoutStep implements CheckoutStep {
    private PricingService pricingService;
    private double retailTotal;

    public RetailPriceCheckoutStep(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        List<Promotion> promotions = checkoutContext.getPromotions();
        retailTotal = 0.0;

        for (BasketItem basketItem : basket.getItems()) {

            double price = pricingService.getPrice(basketItem.getProductCode());
            basketItem.setProductRetailPrice(price);

            Promotion promotionOnItem = promotions.stream()
                    .filter(promo -> promo.getProductCode().equals(basketItem.getProductCode()))
                    .findFirst().orElse(new Promotion(null, NONE));

            retailTotal = applyPromotion(promotionOnItem, basketItem, price);
        }

        checkoutContext.setRetailPriceTotal(retailTotal);
    }

    public double applyPromotion(Promotion promotion, BasketItem basketItem, double price) {

        int quantity = basketItem.getQuantity();

        switch (promotion.getPromotionType()) {
            case TEN_PERCENT_OFF:
                retailTotal += quantity * price * TEN_PERCENT_OFF.getPromotion();
                break;
            case TWO_FOR_ONE:
                int discountedQuantity = quantity / (int) TWO_FOR_ONE.getPromotion() + quantity % (int) TWO_FOR_ONE.getPromotion();
                retailTotal += discountedQuantity * price;
                break;
            case FIFTY_PERCENT_OFF:
                retailTotal += quantity * price * TWO_FOR_ONE.getPromotion();
                break;
            default:
                retailTotal += quantity * price;
        }
        return retailTotal;

    }
}
