package com.taxi.framework.discount.controller;

import com.taxi.framework.booking.dto.BaseBookingRequestDTO;
import com.taxi.framework.discount.dto.*;
import com.taxi.framework.discount.service.PromotionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractPromotionController {

    protected final PromotionService promotionService;

    protected AbstractPromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping("/create")
    public ResponseEntity<PromotionResponseDTO> createPromotion(@RequestBody CreatePromotionDTO createPromotionDTO) {
        PromotionResponseDTO responseDTO = promotionService.createPromotion(createPromotionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PromotionResponseDTO> updatePromotion(@PathVariable Long id, @RequestBody UpdatePromotionDTO updatePromotionDTO) {
        PromotionResponseDTO responseDTO = promotionService.updatePromotion(id, updatePromotionDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponseDTO> getPromotionById(@PathVariable Long id) {
        PromotionResponseDTO responseDTO = promotionService.getPromotionById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<PromotionResponseDTO> getPromotionByCode(@PathVariable String code) {
        PromotionResponseDTO responseDTO = promotionService.getPromotionByCode(code);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PromotionResponseDTO>> getAllPromotions() {
        List<PromotionResponseDTO> responseDTOs = promotionService.getAllPromotions();
        return ResponseEntity.ok(responseDTOs);
    }

    @PostMapping("/apply/{promotionCode}")
    public ResponseEntity<Double> applyPromotionToBooking(@PathVariable String promotionCode, @RequestBody BaseBookingRequestDTO bookingDTO) {
        double discountedPrice = promotionService.applyPromotionToBooking(bookingDTO, promotionCode);
        return ResponseEntity.ok(discountedPrice);
    }

    @PostMapping("/assign")
    public ResponseEntity<UserPromotionResponseDTO> assignPromotionToUser(@RequestBody CreateUserPromotionDTO createUserPromotionDTO) {
        UserPromotionResponseDTO responseDTO = promotionService.assignPromotionToUser(createUserPromotionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update-assignment/{id}")
    public ResponseEntity<UserPromotionResponseDTO> updateAssignedPromotionToUser(@PathVariable Long id, @RequestBody UpdateUserPromotionDTO updateUserPromotionDTO) {
        UserPromotionResponseDTO responseDTO = promotionService.updateAssignedPromotionToUser(id, updateUserPromotionDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/remove-assignment/{id}")
    public ResponseEntity<Void> removePromotionFromUser(@PathVariable Long id) {
        promotionService.removePromotionFromUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPromotionResponseDTO>> getUserPromotions(@PathVariable Long userId) {
        List<UserPromotionResponseDTO> responseDTOs = promotionService.getUserPromotions(userId);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/user-promotion/{id}")
    public ResponseEntity<UserPromotionResponseDTO> getUserPromotionById(@PathVariable Long id) {
        UserPromotionResponseDTO responseDTO = promotionService.getUserPromotionById(id);
        return ResponseEntity.ok(responseDTO);
    }
}