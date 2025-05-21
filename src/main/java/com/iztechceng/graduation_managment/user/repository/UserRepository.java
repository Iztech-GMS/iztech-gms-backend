package com.iztechceng.graduation_managment.user.repository;

import com.iztechceng.graduation_managment.user.model.User;
import com.iztechceng.graduation_managment.user.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRolesRoleName(RoleName roleName);

}
