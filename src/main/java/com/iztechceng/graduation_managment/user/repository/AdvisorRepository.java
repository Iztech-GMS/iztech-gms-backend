package com.iztechceng.graduation_managment.user.repository;

import com.iztechceng.graduation_managment.user.model.entity.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvisorRepository extends JpaRepository<Advisor, Long> {
    // Custom query methods can be defined here if needed
}
