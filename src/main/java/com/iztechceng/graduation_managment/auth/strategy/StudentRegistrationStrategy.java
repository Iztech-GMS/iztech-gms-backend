package com.iztechceng.graduation_managment.auth.strategy;

import com.iztechceng.graduation_managment.auth.model.dto.request.RegisterRequest;
import com.iztechceng.graduation_managment.user.model.entity.Advisor;
import com.iztechceng.graduation_managment.user.model.entity.Student;
import com.iztechceng.graduation_managment.user.model.enums.GraduationStatus;
import com.iztechceng.graduation_managment.user.model.enums.RoleName;
import com.iztechceng.graduation_managment.user.repository.AdvisorRepository;
import com.iztechceng.graduation_managment.user.repository.RoleRepository;
import com.iztechceng.graduation_managment.user.repository.StudentRepository;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentRegistrationStrategy implements RegistrationStrategy {
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AdvisorRepository advisorRepository;

    @Override
    public boolean supports(String email) {
        return email.endsWith("@student.iztech.com");
    }

    @Override
    public void register(RegisterRequest request, String fullName) {
        Student student = Student.builder()
                .name(fullName)
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .graduationStatus(GraduationStatus.ENROLLED)
                .roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
                .advisor(advisorRepository.findAll().getFirst()) // TODO: Change this to a proper advisor selection
                .dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
                .secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
                .studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
                .department("Computer Engineering") // TODO: Change this to a proper department selection
                .build();
        studentRepository.save(student);
    }
}
