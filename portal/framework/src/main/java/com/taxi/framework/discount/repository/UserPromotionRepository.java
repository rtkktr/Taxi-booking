package com.taxi.framework.discount.repository;

import com.taxi.framework.discount.model.UserPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserPromotionRepository extends JpaRepository<UserPromotion, Long> {

    @Query("SELECT up FROM UserPromotion up WHERE up.userId = :userId")
    List<UserPromotion> findByUserId(@Param("userId") Long userId);
}