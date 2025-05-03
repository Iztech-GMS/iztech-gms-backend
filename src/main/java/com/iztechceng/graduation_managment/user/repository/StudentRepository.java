package com.iztechceng.graduation_managment.user.repository;

import com.iztechceng.graduation_managment.user.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Custom query methods can be defined here if needed
}
