package com.iztechceng.graduation_managment.auth.strategy;

import com.iztechceng.graduation_managment.auth.model.dto.request.RegisterRequest;
import com.iztechceng.graduation_managment.user.model.entity.Advisor;
import com.iztechceng.graduation_managment.user.model.enums.RoleName;
import com.iztechceng.graduation_managment.user.repository.AdvisorRepository;
import com.iztechceng.graduation_managment.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdvisorRegistrationStrategy implements RegistrationStrategy {

    private final AdvisorRepository advisorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(String email) {
        return email.endsWith("@advisor.iztech.com");
    }

    @Override
    public void register(RegisterRequest request,String fullName) {
        Advisor advisor = Advisor.builder()
                .name(fullName)
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_ADVISOR).orElseThrow()))
                .build();
        advisorRepository.save(advisor);
    }

}
