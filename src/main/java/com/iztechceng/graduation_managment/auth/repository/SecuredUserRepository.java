package com.iztechceng.graduation_managment.auth.repository;

import com.iztechceng.graduation_managment.auth.model.entity.SecuredUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecuredUserRepository extends JpaRepository<SecuredUser, Long> {
    Optional<SecuredUser> findByEmail(String email);
}
