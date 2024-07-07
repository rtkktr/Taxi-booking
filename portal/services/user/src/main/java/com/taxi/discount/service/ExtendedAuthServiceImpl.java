package com.taxi.discount.service;

import com.taxi.discount.dto.ExtendedUserSigninDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("AuthService")
public class ExtendedAuthServiceImpl implements ExtendedAuthService {
    @Override
    public ExtendedUserSigninDTO signIn(ExtendedUserSigninDTO dto) {
        return dto;
    }
}
