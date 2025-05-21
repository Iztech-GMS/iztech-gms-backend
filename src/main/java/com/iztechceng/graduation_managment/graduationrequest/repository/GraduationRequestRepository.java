package com.iztechceng.graduation_managment.graduationrequest.repository;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GraduationRequestRepository extends JpaRepository<GraduationRequest, Long> {

    @Query("SELECT gr FROM GraduationRequest gr WHERE gr.approver.id = ?1")
    List<GraduationRequest> findByApprover(Long userId);
}
