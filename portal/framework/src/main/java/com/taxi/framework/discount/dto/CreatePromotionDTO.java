package com.taxi.framework.discount.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePromotionDTO {

    @NotBlank(message = "Promotion code is mandatory")
    private String code;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @Positive(message = "Discount percentage must be positive")
    private double discountPercentage;

    @NotNull(message = "User specific flag is mandatory")
    private boolean userSpecific;
}