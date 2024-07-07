package com.taxi.framework.discount.service;
import com.taxi.framework.booking.dto.BaseBookingRequestDTO;
import com.taxi.framework.discount.dto.*;

import com.taxi.framework.discount.model.Promotion;
import com.taxi.framework.discount.model.UserPromotion;
import com.taxi.framework.discount.repository.PromotionRepository;
import com.taxi.framework.discount.repository.UserPromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractPromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    private final UserPromotionRepository userPromotionRepository;

    public AbstractPromotionServiceImpl(PromotionRepository promotionRepository, UserPromotionRepository userPromotionRepository) {
        this.promotionRepository = promotionRepository;
        this.userPromotionRepository = userPromotionRepository;
    }

    @Override
    @Transactional
    public PromotionResponseDTO createPromotion(CreatePromotionDTO createPromotionDTO) {
        Promotion promotion = new Promotion();
        promotion.setPromoCode(createPromotionDTO.getCode());
        promotion.setDescription(createPromotionDTO.getDescription());
        promotion.setDiscountPercentage(createPromotionDTO.getDiscountPercentage());
        promotion.setStartDate(createPromotionDTO.getStartDate());
        promotion.setEndDate(createPromotionDTO.getEndDate());
        Promotion savedPromotion = promotionRepository.save(promotion);

        return convertToPromotionDTO(savedPromotion);
    }

    @Override
    @Transactional
    public PromotionResponseDTO updatePromotion(Long id, UpdatePromotionDTO updatePromotionDTO) {
        Optional<Promotion> optionalPromotion = promotionRepository.findById(id);
        if (optionalPromotion.isPresent()) {
            Promotion promotion = optionalPromotion.get();
            promotion.setPromoCode(updatePromotionDTO.getCode());
            promotion.setDescription(updatePromotionDTO.getDescription());
            promotion.setDiscountPercentage(updatePromotionDTO.getDiscountPercentage());
            promotion.setStartDate(updatePromotionDTO.getStartDate());
            promotion.setEndDate(updatePromotionDTO.getEndDate());
            Promotion updatedPromotion = promotionRepository.save(promotion);

            return convertToPromotionDTO(updatedPromotion);
        } else {
            throw new RuntimeException("Promotion not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public PromotionResponseDTO getPromotionById(Long id) {
        Optional<Promotion> optionalPromotion = promotionRepository.findById(id);
        return optionalPromotion.map(this::convertToPromotionDTO).orElseThrow(() -> new RuntimeException("Promotion not found with id: " + id));
    }

    @Override
    public List<PromotionResponseDTO> getAllPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions.stream().map(this::convertToPromotionDTO).collect(Collectors.toList());
    }

    @Override
    public PromotionResponseDTO getPromotionByCode(String code) {
        Optional<Promotion> optionalPromotion = promotionRepository.findByPromoCode(code);
        return optionalPromotion.map(this::convertToPromotionDTO).orElseThrow(() -> new RuntimeException("Promotion not found with code: " + code));
    }

    @Override
    public double applyPromotionToBooking(BaseBookingRequestDTO bookingDTO, String promotionCode) {
        // Retrieve promotion details from repository
        Promotion promotion = promotionRepository.findByPromoCode(promotionCode)
                .orElseThrow(() -> new RuntimeException("Promotion not found with code: " + promotionCode));

        // Calculate discounted price
        double originalPrice = bookingDTO.getPrice();
        double discount = promotion.getDiscountPercentage();

        return originalPrice - (originalPrice * discount / 100);
    }


    @Override
    @Transactional
    public UserPromotionResponseDTO assignPromotionToUser(CreateUserPromotionDTO createUserPromotionDTO) {
        UserPromotion userPromotion = new UserPromotion();
        userPromotion.setUserId(createUserPromotionDTO.getUserId());
        userPromotion.setPromotion(promotionRepository.getById(createUserPromotionDTO.getPromotionId()));
        userPromotion.setUsed(false);

        UserPromotion savedUserPromotion = userPromotionRepository.save(userPromotion);
        return convertToUserPromotionDTO(savedUserPromotion);
    }

    @Override
    @Transactional
    public UserPromotionResponseDTO updateAssignedPromotionToUser(Long id, UpdateUserPromotionDTO updateUserPromotionDTO) {
        Optional<UserPromotion> optionalUserPromotion = userPromotionRepository.findById(id);
        if (optionalUserPromotion.isPresent()) {
            UserPromotion userPromotion = optionalUserPromotion.get();
            userPromotion.setUserId(updateUserPromotionDTO.getUserId());
            userPromotion.setPromotion(promotionRepository.getById(updateUserPromotionDTO.getPromotionId()));
            userPromotion.setUsed(updateUserPromotionDTO.isUsed());

            UserPromotion updatedUserPromotion = userPromotionRepository.save(userPromotion);
            return convertToUserPromotionDTO(updatedUserPromotion);
        } else {
            throw new RuntimeException("User Promotion not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void removePromotionFromUser(Long id) {
        userPromotionRepository.deleteById(id);
    }

    @Override
    public List<UserPromotionResponseDTO> getUserPromotions(Long userId) {
        List<UserPromotion> userPromotions = userPromotionRepository.findByUserId(userId);
        return userPromotions.stream().map(this::convertToUserPromotionDTO).collect(Collectors.toList());
    }

    @Override
    public UserPromotionResponseDTO getUserPromotionById(Long id) {
        Optional<UserPromotion> optionalUserPromotion = userPromotionRepository.findById(id);
        return optionalUserPromotion.map(this::convertToUserPromotionDTO)
                .orElseThrow(() -> new RuntimeException("User Promotion not found with id: " + id));
    }

    private PromotionResponseDTO convertToPromotionDTO(Promotion promotion) {
        PromotionResponseDTO promotionResponseDTO = new PromotionResponseDTO();
        promotionResponseDTO.setId(promotion.getId());
        promotionResponseDTO.setCode(promotion.getPromoCode());
        promotionResponseDTO.setDescription(promotion.getDescription());
        promotionResponseDTO.setDiscountPercentage(promotion.getDiscountPercentage());
        promotionResponseDTO.setStartDate(promotion.getStartDate());
        promotionResponseDTO.setEndDate(promotion.getEndDate());
        return promotionResponseDTO;
    }

    private UserPromotionResponseDTO convertToUserPromotionDTO(UserPromotion userPromotion) {
        UserPromotionResponseDTO userPromotionResponseDTO = new UserPromotionResponseDTO();
        userPromotionResponseDTO.setId(userPromotion.getId());
        userPromotionResponseDTO.setUserId(userPromotion.getUserId());
        userPromotionResponseDTO.setPromotionId(userPromotion.getPromotion().getId());
        userPromotionResponseDTO.setUsed(userPromotion.isUsed());
        return userPromotionResponseDTO;
    }
}