package com.pniemirowko.cloud.identity.provider;

import com.pniemirowko.cloud.identity.provider.entity.AppUser;
import com.pniemirowko.cloud.identity.provider.repositroy.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class CloudIdentityProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudIdentityProviderApplication.class, args);
	}

	// TODO temporary until we create real DB
	@Bean
	CommandLineRunner initUsers(UserRepository userRepository,
								PasswordEncoder passwordEncoder) {

		return args -> {

			if (userRepository.findUserByUsername("admin").isEmpty()) {

				AppUser admin = AppUser.builder()
						.username("admin")
						.password(passwordEncoder.encode("admin123"))
						.roles(Set.of("ADMIN"))
						.build();

				userRepository.save(admin);
			}

			if (userRepository.findUserByUsername("user").isEmpty()) {

				AppUser user = AppUser.builder()
						.username("user")
						.password(passwordEncoder.encode("user123"))
						.roles(Set.of("USER"))
						.build();

				userRepository.save(user);
			}
		};
	}

}
