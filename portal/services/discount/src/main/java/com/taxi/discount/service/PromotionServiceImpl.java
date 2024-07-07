package com.taxi.discount.service;

import com.taxi.discount.repository.PromotionRepositoryImpl;
import com.taxi.discount.repository.UserPromotionRepositoryImpl;
import com.taxi.framework.discount.repository.PromotionRepository;
import com.taxi.framework.discount.repository.UserPromotionRepository;
import com.taxi.framework.discount.service.AbstractPromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl extends AbstractPromotionServiceImpl {

    @Autowired
    public PromotionServiceImpl(PromotionRepositoryImpl promotionRepository, UserPromotionRepositoryImpl userPromotionRepository) {
        super(promotionRepository, userPromotionRepository);
    }
}