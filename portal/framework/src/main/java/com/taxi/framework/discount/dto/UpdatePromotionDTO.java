package com.taxi.framework.discount.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePromotionDTO {

    @NotBlank(message = "Promotion code is mandatory")
    private String code;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @Positive(message = "Discount percentage must be positive")
    private double discountPercentage;

    private boolean userSpecific;
}