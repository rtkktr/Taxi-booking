package com.taxi.framework.discount.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPromotionResponseDTO {

    private Long id;
    private Long userId;
    private Long promotionId;
}