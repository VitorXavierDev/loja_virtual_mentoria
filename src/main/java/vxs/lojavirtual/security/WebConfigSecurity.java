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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpSessionListener;
import vxs.lojavirtual.expections.CustomAccessDeniedHandler;
import vxs.lojavirtual.expections.CustomAuthenticationEntryPoint;
import vxs.lojavirtual.service.ImplementacaoUserDetailsService;

@Configuration
@EnableMethodSecurity
public class WebConfigSecurity implements HttpSessionListener {

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	private final JWTTokenAutenticacaoService jwtTokenService;

	public WebConfigSecurity(JWTTokenAutenticacaoService jwtTokenService, ImplementacaoUserDetailsService implementacaoUserDetailsService) {
		this.jwtTokenService = jwtTokenService;
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
	
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		AuthenticationManager authManager = authenticationManager(http);
		
		   http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable())
           .authorizeHttpRequests(auth -> auth
               .requestMatchers("/", "/index", "/login").permitAll()
               .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
               .anyRequest().authenticated()
           )
           .exceptionHandling(exception -> exception
                   .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // Handler 401
                   .accessDeniedHandler(new CustomAccessDeniedHandler()) // Handler 403
               )
           .logout(logout -> logout
               .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
               .logoutSuccessUrl("/index")
           )
           .addFilterAfter(new JWTLoginFilter("/login", authManager, jwtTokenService), UsernamePasswordAuthenticationFilter.class)
           .addFilterBefore(new JwtApiAutenticacaoFilter(jwtTokenService), UsernamePasswordAuthenticationFilter.class);

       return http.build();
	}

}
