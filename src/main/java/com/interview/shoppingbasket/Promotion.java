package com.interview.shoppingbasket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Promotion {

    private String productCode;
    private Discounts promotionType;

}
