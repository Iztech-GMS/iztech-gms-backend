package com.iztechceng.graduation_managment.auth.service.secured_user;
import com.iztechceng.graduation_managment.auth.repository.SecuredUserRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final SecuredUserRepository securedUserRepository;

    @Override
    public boolean confirmUserAccount(String email) {
        return securedUserRepository.findByEmail(email).isPresent();
    }

}
