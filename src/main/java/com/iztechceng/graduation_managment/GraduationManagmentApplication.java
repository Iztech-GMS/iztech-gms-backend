package com.iztechceng.graduation_managment;

import com.iztechceng.graduation_managment.auth.model.entity.SecuredUser;
import com.iztechceng.graduation_managment.auth.repository.SecuredUserRepository;
import com.iztechceng.graduation_managment.systemsetting.model.entity.SystemSetting;
import com.iztechceng.graduation_managment.systemsetting.repository.SystemSettingsRepository;
import com.iztechceng.graduation_managment.user.model.User;
import com.iztechceng.graduation_managment.user.model.entity.Advisor;
import com.iztechceng.graduation_managment.user.model.entity.Role;
import com.iztechceng.graduation_managment.user.model.entity.Student;
import com.iztechceng.graduation_managment.user.model.enums.GraduationStatus;
import com.iztechceng.graduation_managment.user.model.enums.RoleName;
import com.iztechceng.graduation_managment.user.repository.AdvisorRepository;
import com.iztechceng.graduation_managment.user.repository.RoleRepository;
import com.iztechceng.graduation_managment.user.repository.StudentRepository;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
@Slf4j
public class GraduationManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraduationManagmentApplication.class, args);
	}

	@Bean
	CommandLineRunner mockAdvisorsAndStudents(
			SecuredUserRepository securedUserRepository,SystemSettingsRepository systemSettingsRepository, RoleRepository roleRepository
			, UserRepository userRepository, PasswordEncoder passwordEncoder,StudentRepository studentRepository,AdvisorRepository advisorRepository) {

		return args -> {
			if(!securedUserRepository.findById(Long.valueOf("1")).isPresent()) {
				mockRoles(roleRepository);
				mockSecuredUserList(securedUserRepository);
				mockAuthorizedUsers(passwordEncoder, userRepository, roleRepository, advisorRepository);
				mockSettings(systemSettingsRepository);
				mockCompSciStudents(passwordEncoder, userRepository, roleRepository, advisorRepository,studentRepository);
				mockChemistryStudents(passwordEncoder, userRepository, roleRepository, advisorRepository,studentRepository);
				mockArchStudents(passwordEncoder, userRepository, roleRepository, advisorRepository,studentRepository);
				insufficientStudents(passwordEncoder, userRepository, roleRepository, advisorRepository, studentRepository);
			}

			};

		}

	//Sistemde register hakkı bulunan emailler = SecuredUser
	private void mockSecuredUserList(SecuredUserRepository securedUserRepository) {

		SecuredUser user1 = SecuredUser.builder()
				.email("samettenekeci@advisor.iztech.com")
				.firstName("Samet")
				.lastName("Tenekeci")
				.build();

		SecuredUser user2 = SecuredUser.builder()
				.email("mertkarahan@student.iztech.com")
				.firstName("Mert")
				.lastName("Karahan")
				.build();

		SecuredUser user3 = SecuredUser.builder()
				.email("aliveli@affairs.iztech.com")
				.firstName("Ali")
				.lastName("Veli")
				.build();

		SecuredUser user4 = SecuredUser.builder()
				.email("ahmetyilmaz@secretary.iztech.com")
				.firstName("Ahmet")
				.lastName("Yilmaz")
				.build();

		SecuredUser user5 = SecuredUser.builder()
				.email("mehmetpolat@dean.iztech.com")
				.firstName("Mehmet")
				.lastName("Polat")
				.build();

		SecuredUser user6 = SecuredUser.builder()
				.email("hamdizengin@student.iztech.com")
				.firstName("Hamdi")
				.lastName("Zengin")
				.build();



		securedUserRepository.saveAll(List.of(user1, user2, user3, user4, user5,user6));

		System.out.println("✅ SecuredUser mock data inserted.");

	}

	private void insufficientStudents(PasswordEncoder passwordEncoder, UserRepository userRepository,
							  RoleRepository roleRepository, AdvisorRepository advisorRepository
			,StudentRepository studentRepository) {

		Student student1 = Student.builder()
				.name("Insufficient GPA")
				.email("insufgpa@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(1.8)
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();

		Student student2 = Student.builder()
				.name("Insufficient Mandatory Course")
				.email("insufmandatory@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(2.4)
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(false)
				.build();

		Student student3 = Student.builder()
				.name("Insufficient Credit")
				.email("insufcredit@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(2.4)
				.totalEarnedCredits(230)
				.isMandatoryCourseCompleted(true)
				.build();

		studentRepository.saveAll(List.of(student1, student2, student3));
		System.out.println("✅ Insufficient Students mock data inserted.");

	}



	private void mockChemistryStudents(PasswordEncoder passwordEncoder, UserRepository userRepository,
							  RoleRepository roleRepository, AdvisorRepository advisorRepository
			,StudentRepository studentRepository) {

		Student student16 = Student.builder()
				.name("Emir Altun")
				.email("emiraltun@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student17 = Student.builder()
				.name("Ayse Kilic")
				.email("aysekilic@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student18 = Student.builder()
				.name("Mehmet Turan")
				.email("mehmetturan@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student19 = Student.builder()
				.name("Zeynep Bozkurt")
				.email("zeynepbozkurt@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student20 = Student.builder()
				.name("Ali Gunes")
				.email("aligunes@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student21 = Student.builder()
				.name("Elif Polat")
				.email("elifpolat@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student22 = Student.builder()
				.name("Kerem Simsek")
				.email("keremsimsek@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student23 = Student.builder()
				.name("Derya Arslan")
				.email("deryaarslan@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student24 = Student.builder()
				.name("Berk Tunc")
				.email("berktunc@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student25 = Student.builder()
				.name("Merve Demirtas")
				.email("mervedemirtas@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student26 = Student.builder()
				.name("Can Ersoy")
				.email("canersoy@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student27 = Student.builder()
				.name("Naz Bayraktar")
				.email("nazbayraktar@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student28 = Student.builder()
				.name("Ozan Kurt")
				.email("ozankurt@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student29 = Student.builder()
				.name("Melis Tas")
				.email("melistas@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student30 = Student.builder()
				.name("Hakan Dag")
				.email("hakandag@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Chemical Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();

		studentRepository.saveAll(List.of(student16, student17, student18, student19, student20,
				student21, student22, student23, student24, student25,
				student26, student27, student28, student29, student30));

		System.out.println("✅ Chem Eng Students mock data inserted.");

	}

	private void mockArchStudents(PasswordEncoder passwordEncoder, UserRepository userRepository,
							  RoleRepository roleRepository, AdvisorRepository advisorRepository
			,StudentRepository studentRepository) {


		Student student31 = Student.builder()
				.name("Selin Ersoy")
				.email("selinersoy@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student32 = Student.builder()
				.name("Tolga Yildirim")
				.email("tolgayildirim@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student33 = Student.builder()
				.name("Cansu Karaca")
				.email("cansukaraca@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student34 = Student.builder()
				.name("Baran Ozdemir")
				.email("baranozdemir@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student35 = Student.builder()
				.name("Ece Kocak")
				.email("ecekocak@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student36 = Student.builder()
				.name("Furkan Erdem")
				.email("furkanerdem@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student37 = Student.builder()
				.name("Gizem Sahin")
				.email("gizemsahin@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student38 = Student.builder()
				.name("Harun Yalcin")
				.email("harunyalcin@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student39 = Student.builder()
				.name("Ilayda Acar")
				.email("ilaydaacar@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student40 = Student.builder()
				.name("Kaan Kaplan")
				.email("kaankaplan@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student41 = Student.builder()
				.name("Lara Guler")
				.email("laraguler@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student42 = Student.builder()
				.name("Mert Tan")
				.email("merttan@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student43 = Student.builder()
				.name("Nehir Durmaz")
				.email("nehirdurmaz@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student44 = Student.builder()
				.name("Onur Avci")
				.email("onuravci@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student45 = Student.builder()
				.name("Pelin Uslu")
				.email("pelinuslu@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Architecture")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();

		studentRepository.saveAll(List.of(student31, student32, student33, student34, student35,
				student36, student37, student38, student39, student40,
				student41, student42, student43, student44, student45));

		System.out.println("✅ Arch Students mock data inserted.");

	}



	private void mockCompSciStudents(PasswordEncoder passwordEncoder, UserRepository userRepository,
							  RoleRepository roleRepository, AdvisorRepository advisorRepository
			,StudentRepository studentRepository) {


		Student student1 = Student.builder()
				.name("Emir Dagdelen")
				.email("emirdagdelen@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student2 = Student.builder()
				.name("Ayse Yilmaz")
				.email("ayseyilmaz@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student3 = Student.builder()
				.name("Mehmet Kaya")
				.email("mehmetkaya@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student4 = Student.builder()
				.name("Zeynep Demir")
				.email("zeynepdemir@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student5 = Student.builder()
				.name("Ali Aydin")
				.email("aliaydin@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student6 = Student.builder()
				.name("Elif Cetin")
				.email("elifcetin@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student7 = Student.builder()
				.name("Kerem Sari")
				.email("keremsari@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student8 = Student.builder()
				.name("Derya Bulut")
				.email("deryabulut@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student9 = Student.builder()
				.name("Berk Koc")
				.email("berkkoc@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student10 = Student.builder()
				.name("Merve Ozkan")
				.email("merveozkan@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student11 = Student.builder()
				.name("Can Aslan")
				.email("canaslan@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student12 = Student.builder()
				.name("Naz Aksoy")
				.email("nazaksoy@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student13 = Student.builder()
				.name("Ozan Sevim")
				.email("ozansevim@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student14 = Student.builder()
				.name("Melis Ergin")
				.email("melisergin@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();


		Student student15 = Student.builder()
				.name("Hakan Uysal")
				.email("hakanuysal@student.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("student123"))
				.graduationStatus(GraduationStatus.GRADUATED)
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENT).orElseThrow()))
				.advisor(advisorRepository.findAll().getFirst())
				.dean(userRepository.findByRolesRoleName(RoleName.ROLE_DEAN).getFirst())
				.secretary(userRepository.findByRolesRoleName(RoleName.ROLE_SECRETARY).getFirst())
				.studentAffairs(userRepository.findByRolesRoleName(RoleName.ROLE_STUDENTAFFAIRS).getFirst())
				.department("Computer Engineering")
				.gpa(generateRandomGPA())
				.totalEarnedCredits(240)
				.isMandatoryCourseCompleted(true)
				.build();

		studentRepository.saveAll(List.of(student1, student2, student3, student4, student5,
				student6, student7, student8, student9, student10,
				student11, student12, student13, student14, student15));

		System.out.println("✅ Comp Sci Students mock data inserted.");

	}

	private double generateRandomGPA() {
		double gpa = 2 + (4 - 2) * Math.random(); // 2 ile 4 arası
		return Math.round(gpa * 100.0) / 100.0;  // virgülden sonra 2 basamak
	}



	private void mockAuthorizedUsers(PasswordEncoder passwordEncoder, UserRepository userRepository,
									 RoleRepository roleRepository, AdvisorRepository advisorRepository) {
		User user1 = User.builder()
				.name("Ali Veli")
				.email("aliveli@affairs.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("affairs123"))
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENTAFFAIRS).orElseThrow()))
				.build();

		User user2 = User.builder()
				.name("Ahmet Yilmaz")
				.email("ahmetyilmaz@secretary.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("secretary123"))
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_SECRETARY).orElseThrow()))
				.build();

		User user3 = User.builder()
				.name("Mehmet Polat")
				.email("mehmetpolat@dean.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("dean123"))
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_DEAN).orElseThrow()))
				.build();
    
		Advisor advisor1 = Advisor.builder()
				.name("Samet Tenekeci")
				.email("samettenekeci@advisor.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("advisor123"))
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_ADVISOR).orElseThrow()))
				.build();



		userRepository.saveAll(List.of(user1, user2, user3));
		advisorRepository.save(advisor1);

	}

	private void mockRoles(RoleRepository roleRepository) {
		if (roleRepository.findByRoleName(RoleName.ROLE_ADVISOR).isEmpty()) {
			roleRepository.save(Role.builder()
					.roleName(RoleName.ROLE_ADVISOR)
					.build());
		}

		if (roleRepository.findByRoleName(RoleName.ROLE_STUDENT).isEmpty()) {
			roleRepository.save(Role.builder()
					.roleName(RoleName.ROLE_STUDENT)
					.build());
		}
		if (roleRepository.findByRoleName(RoleName.ROLE_DEAN).isEmpty()) {
			roleRepository.save(Role.builder()
					.roleName(RoleName.ROLE_DEAN)
					.build());
		}
		if (roleRepository.findByRoleName(RoleName.ROLE_SECRETARY).isEmpty()) {
			roleRepository.save(Role.builder()
					.roleName(RoleName.ROLE_SECRETARY)
					.build());
		}
		if (roleRepository.findByRoleName(RoleName.ROLE_STUDENTAFFAIRS).isEmpty()) {
			roleRepository.save(Role.builder()
					.roleName(RoleName.ROLE_STUDENTAFFAIRS)
					.build());
		}


		System.out.println("✅ Mock roles inserted.");
	}

	private void mockSettings(SystemSettingsRepository systemSettingsRepository) {
		if (systemSettingsRepository.findById(1L).isEmpty()) {
			systemSettingsRepository.save(SystemSetting.builder().graduationRequestEnabled(true).build());
			System.out.println("✅ Settings inserted.");
		}
	}

}
