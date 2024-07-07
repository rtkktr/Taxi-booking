package com.taxi.discount.controller;

import com.taxi.discount.dto.ExtendedUserSigninDTO;
import com.taxi.framework.user.controller.AbstractAuthController;
import com.taxi.discount.service.ExtendedAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class AuthControllerImplement extends AbstractAuthController<ExtendedUserSigninDTO> {

    @Autowired
    private ExtendedAuthServiceImpl extendedAuthService;

    public AuthControllerImplement(ExtendedAuthServiceImpl extendedAuthService) {
        super(extendedAuthService);
    }
}
