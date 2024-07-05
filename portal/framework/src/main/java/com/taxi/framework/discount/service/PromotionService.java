package com.taxi.framework.discount.service;

import com.taxi.framework.discount.dto.*;
import java.util.List;

public interface PromotionService {

    PromotionResponseDTO createPromotion(CreatePromotionDTO createPromotionDTO);

    PromotionResponseDTO updatePromotion(Long id, UpdatePromotionDTO updatePromotionDTO);

    void deletePromotion(Long id);

    PromotionResponseDTO getPromotionById(Long id);

    List<PromotionResponseDTO> getAllPromotions();

    PromotionResponseDTO getPromotionByCode(String code);

    double applyPromotionToBooking(long bookingId, String promotionCode);

    UserPromotionResponseDTO assignPromotionToUser(CreateUserPromotionDTO createUserPromotionDTO);

    UserPromotionResponseDTO updateAssignedPromotionToUser(Long id, UpdateUserPromotionDTO updateUserPromotionDTO);

    void removePromotionFromUser(Long id);

    List<UserPromotionResponseDTO> getUserPromotions(Long userId);

    UserPromotionResponseDTO getUserPromotionById(Long id);
}