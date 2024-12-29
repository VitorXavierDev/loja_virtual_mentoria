package vxs.lojavirtual.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import jakarta.servlet.http.HttpSessionListener;
import vxs.lojavirtual.service.ImplementacaoUserDetailsService;

@Configuration
@EnableMethodSecurity
public class WebConfigSecurity implements HttpSessionListener {

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	public WebConfigSecurity(ImplementacaoUserDetailsService implementacaoUserDetailsService) {
		this.implementacaoUserDetailsService = implementacaoUserDetailsService;

	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);

		// Configuração do AuthenticationManager
		authenticationManagerBuilder.userDetailsService(implementacaoUserDetailsService)
				.passwordEncoder(passwordEncoder());

		// Retorna o AuthenticationManager configurado
		return authenticationManagerBuilder.getOrBuild();
	}

	private PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				// .csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						((authorize) -> authorize.requestMatchers(HttpMethod.GET, "/salvarAcesso", "/deleteAcesso")
								.permitAll().requestMatchers(HttpMethod.POST, "/salvarAcesso", "/deleteAcesso")
								.permitAll().anyRequest().permitAll())

				);
		return http.build();
	}

}
