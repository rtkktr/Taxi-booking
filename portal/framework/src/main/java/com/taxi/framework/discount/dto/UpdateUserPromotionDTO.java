package com.taxi.framework.discount.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserPromotionDTO {

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    @NotNull(message = "Promotion ID is mandatory")
    private Long promotionId;

    private boolean isUsed;
}