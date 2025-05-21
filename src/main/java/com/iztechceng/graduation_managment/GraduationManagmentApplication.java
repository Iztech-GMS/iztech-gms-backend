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
}
