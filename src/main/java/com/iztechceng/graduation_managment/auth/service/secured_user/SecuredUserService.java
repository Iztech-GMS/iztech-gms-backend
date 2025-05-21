package com.iztechceng.graduation_managment.auth.service.secured_user;


import com.iztechceng.graduation_managment.auth.model.dto.request.secured_user.SecuredUserRequest;
import com.iztechceng.graduation_managment.auth.model.dto.request.secured_user.SecuredUserResponse;
import com.iztechceng.graduation_managment.auth.model.entity.SecuredUser;
import com.iztechceng.graduation_managment.auth.model.mappers.SecuredUserMapper;
import com.iztechceng.graduation_managment.auth.repository.SecuredUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecuredUserService {

    private final SecuredUserRepository securedUserRepository;

    public SecuredUserResponse create(SecuredUserRequest request) {
        SecuredUser user = SecuredUserMapper.toEntity(request);
        return SecuredUserMapper.toResponse(securedUserRepository.save(user));
    }

    public List<SecuredUserResponse> getAll() {
        return securedUserRepository.findAll().stream()
                .map(SecuredUserMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<SecuredUserResponse> getById(Long id) {
        return securedUserRepository.findById(id)
                .map(SecuredUserMapper::toResponse);
    }

    public SecuredUserResponse update(Long id, SecuredUserRequest request) {
        SecuredUser user = securedUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return SecuredUserMapper.toResponse(securedUserRepository.save(user));
    }

    public void delete(Long id) {
        securedUserRepository.deleteById(id);
    }


    public boolean confirmUserAccount(String email) {
        return securedUserRepository.findByEmail(email).isPresent();
    }

    public boolean isEmailExists(String email) {
        return securedUserRepository.findByEmail(email).isPresent();
    }

}
