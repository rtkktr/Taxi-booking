package com.taxi.framework.discount.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    @NotNull(message = "Start date is mandatory")
    private LocalDate startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDate endDate;
}