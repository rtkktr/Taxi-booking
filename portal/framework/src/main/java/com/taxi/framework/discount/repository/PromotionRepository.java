package com.taxi.framework.discount.repository;

import com.taxi.framework.discount.model.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("SELECT p FROM Promotion p WHERE p.promoCode = :promoCode")
    Optional<Promotion> findByPromoCode(@Param("promoCode") String promoCode);
}