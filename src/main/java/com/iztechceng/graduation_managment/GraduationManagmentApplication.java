package com.iztechceng.graduation_managment;

import com.iztechceng.graduation_managment.auth.model.entity.SecuredUser;
import com.iztechceng.graduation_managment.auth.repository.SecuredUserRepository;
import com.iztechceng.graduation_managment.user.model.User;
import com.iztechceng.graduation_managment.user.model.entity.Role;
import com.iztechceng.graduation_managment.user.model.enums.RoleName;
import com.iztechceng.graduation_managment.user.repository.RoleRepository;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
			SecuredUserRepository securedUserRepository, RoleRepository roleRepository
			, UserRepository userRepository, PasswordEncoder passwordEncoder) {

		return args -> {
			if(!securedUserRepository.findById(Long.valueOf("1")).isPresent()) {
				mockRoles(roleRepository);
				mockSecuredUserList(securedUserRepository);
				mockAuthorizedUsers(passwordEncoder, userRepository, roleRepository);
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
				.email("ahmetyılmaz@secretary.iztech.com")
				.firstName("Ahmet")
				.lastName("Yılmaz")
				.build();

		SecuredUser user5 = SecuredUser.builder()
				.email("mehmetpolat@dean.iztech.com")
				.firstName("Mehmet")
				.lastName("Polat")
				.build();


		securedUserRepository.saveAll(List.of(user1, user2, user3, user4, user5));

		System.out.println("✅ SecuredUser mock data inserted.");

	}

	private void mockAuthorizedUsers(PasswordEncoder passwordEncoder, UserRepository userRepository,
									 RoleRepository roleRepository) {
		User user1 = User.builder()
				.name("Ali Veli")
				.email("aliveli@affairs.iztech.com")
				.phoneNumber("1234567890")
				.password(passwordEncoder.encode("affairs123"))
				.roles(Set.of(roleRepository.findByRoleName(RoleName.ROLE_STUDENTAFFAIRS).orElseThrow()))
				.build();

		User user2 = User.builder()
				.name("Ahmet Yılmaz")
				.email("ahmetyılmaz@secretary.iztech.com")
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

		userRepository.saveAll(List.of(user1, user2, user3));

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
}
