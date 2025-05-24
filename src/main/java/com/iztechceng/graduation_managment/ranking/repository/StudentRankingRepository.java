package com.iztechceng.graduation_managment.ranking.repository;

import com.iztechceng.graduation_managment.user.model.entity.Student;
import com.iztechceng.graduation_managment.user.model.enums.GraduationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRankingRepository extends JpaRepository<Student, Long> {
    List<Student> findByGraduationStatusOrderByGpaDesc(GraduationStatus status);
    List<Student> findByGraduationStatusAndDepartmentOrderByGpaDesc(GraduationStatus status, String department);
    List<Student> findTop3ByGraduationStatusOrderByGpaDesc(GraduationStatus status);

} 