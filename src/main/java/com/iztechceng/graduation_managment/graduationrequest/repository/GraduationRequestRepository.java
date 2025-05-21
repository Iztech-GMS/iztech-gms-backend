package com.iztechceng.graduation_managment.graduationrequest.repository;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraduationRequestRepository extends JpaRepository<GraduationRequest, Long> {

}
