package com.iztechceng.graduation_managment.user.repository;

import com.iztechceng.graduation_managment.user.model.entity.Role;
import com.iztechceng.graduation_managment.user.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);

}
