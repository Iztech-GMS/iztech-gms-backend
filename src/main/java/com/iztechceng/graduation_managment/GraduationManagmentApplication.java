package com.iztechceng.graduation_managment;

import com.iztechceng.graduation_managment.auth.model.entity.SecuredUser;
import com.iztechceng.graduation_managment.auth.repository.SecuredUserRepository;
import com.iztechceng.graduation_managment.user.model.entity.Role;
import com.iztechceng.graduation_managment.user.model.enums.RoleName;
import com.iztechceng.graduation_managment.user.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@Slf4j
public class GraduationManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraduationManagmentApplication.class, args);
	}

	@Bean
	CommandLineRunner mockAdvisorsAndStudents(
			SecuredUserRepository securedUserRepository,RoleRepository roleRepository
	) {
		return args -> {
			if(!securedUserRepository.findById(Long.valueOf("1")).isPresent()) {
				mockRoles(roleRepository);
				mockSecuredUserList(securedUserRepository);
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


		securedUserRepository.saveAll(List.of(user1, user2));

		System.out.println("✅ SecuredUser mock data inserted.");

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

		System.out.println("✅ Mock roles inserted.");
	}


}
