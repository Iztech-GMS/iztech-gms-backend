package com.iztechceng.graduation_managment.certification;

import com.iztechceng.graduation_managment.ranking.repository.StudentRankingRepository;
import com.iztechceng.graduation_managment.user.model.enums.CertificateType;
import com.iztechceng.graduation_managment.user.model.enums.GraduationStatus;
import com.iztechceng.graduation_managment.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.iztechceng.graduation_managment.user.model.entity.Student;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationDeterminerService {
    private final StudentRankingRepository studentRankingRepository;
    private final StudentRepository studentRepository;

    public void assignCertificates(){
        assignBeratCertificate();
        assignGraduationCertificate();
    }

    public List<CertificateType> getStudentCertificate(String email) {
        return studentRepository.findByEmail(email)
                .map(Student::getCertificates)
                .orElse(Collections.emptyList());
    }


    private void assignBeratCertificate() {
        studentRankingRepository.findTop3ByGraduationStatusOrderByGpaDesc(GraduationStatus.GRADUATED)
                .forEach(student -> {
                    student.addCertificate(CertificateType.BERAT_CERTIFICATE);
                    studentRankingRepository.save(student);
                });

    }

    private void assignGraduationCertificate() {
        studentRankingRepository.findByGraduationStatusOrderByGpaDesc(GraduationStatus.GRADUATED)
                .forEach(student -> {
                    if(student.getGpa() >= 3.5){
                        student.addCertificate(CertificateType.HIGH_HONOR_CERTIFICATE);
                    } else if(student.getGpa() >= 3.0) {
                        student.addCertificate(CertificateType.HONOR_CERTIFICATE);
                    } else {
                        student.addCertificate(CertificateType.GRADUATION_CERTIFICATE);
                    }
                    studentRankingRepository.save(student);
                });

    }
}
