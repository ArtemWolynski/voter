package voter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import voter.model.CustomUserDetails;
import voter.repository.UserRepositorySpringDataJpa;

@SpringBootApplication
public class VoterApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(VoterApplication.class, args);
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder,
									  UserRepositorySpringDataJpa userRepositorySpringDataJpa) throws Exception {
		authenticationManagerBuilder.userDetailsService(s -> new CustomUserDetails(userRepositorySpringDataJpa.getUserByUserName(s))).passwordEncoder(passwordEncoder);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
