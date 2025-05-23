package com.iztechceng.graduation_managment.graduationrequest.repository;

import com.iztechceng.graduation_managment.graduationrequest.model.entity.GraduationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GraduationRequestRepository extends JpaRepository<GraduationRequest, Long> {

    @Query("SELECT gr FROM GraduationRequest gr WHERE gr.approver.id = ?1")
    List<GraduationRequest> findByApprover(Long userId);

    @Query("SELECT gr FROM GraduationRequest gr WHERE gr.student.id = ?1")
    List<GraduationRequest> findByStudent(Long userId);

    @Query("SELECT CASE WHEN COUNT(gr) > 0 THEN true ELSE false END " +
            "FROM GraduationRequest gr " +
            "WHERE gr.student.id = ?1 AND gr.status IN ('ADVISOR_PENDING', 'DEAN_PENDING', 'SECRETARY_PENDING', 'STUDENT_AFFAIRS_PENDING')")
    boolean isExistActiveRequest(Long studentId);

    @Query("SELECT CASE WHEN COUNT(gr) > 0 THEN true ELSE false END " +
            "FROM GraduationRequest gr " +
            "WHERE gr.student.id = ?1 AND gr.status IN ('GRADUATED')")
    boolean existsGraduatedRequestByStudentId(Long studentId);


}
