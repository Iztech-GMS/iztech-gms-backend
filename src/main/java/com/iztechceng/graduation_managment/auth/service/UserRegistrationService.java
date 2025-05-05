package com.iztechceng.graduation_managment.auth.service;

import com.iztechceng.graduation_managment.auth.model.dto.request.RegisterRequest;
import com.iztechceng.graduation_managment.auth.repository.SecuredUserRepository;
import com.iztechceng.graduation_managment.auth.strategy.RegistrationStrategy;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final SecuredUserRepository securedUserRepository;
    private final List<RegistrationStrategy> strategies;
    private final UserRepository userRepository;

    public void registerUser(RegisterRequest registerRequest) {

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Bu e-posta adresi zaten kayıtlı.");
        }

        String fullName = securedUserRepository.findByEmail(registerRequest.getEmail()).get().getFullName();

        strategies.stream()
                .filter(s -> s.supports(registerRequest.getEmail()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Bu e-posta için uygun kayıt stratejisi bulunamadı."))
                .register(registerRequest, fullName);
    }

    public String getUserFullName(String email) {
        return securedUserRepository.findByEmail(email)
                .map(user -> user.getFullName())
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı."));
    }

}




