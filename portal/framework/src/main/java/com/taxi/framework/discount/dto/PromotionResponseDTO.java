package com.taxi.framework.discount.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PromotionResponseDTO {

    private Long id;
    private String code;
    private String description;
    private double discountPercentage;
    private boolean userSpecific;
}