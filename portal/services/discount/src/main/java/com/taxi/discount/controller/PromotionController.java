package com.taxi.discount.controller;

import com.taxi.discount.service.PromotionServiceImpl;
import com.taxi.framework.discount.controller.AbstractPromotionController;
import com.taxi.framework.discount.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/promotion")
@RestController
public class PromotionController extends AbstractPromotionController {

    public PromotionController(PromotionServiceImpl promotionService) {
        super(promotionService);
    }
}