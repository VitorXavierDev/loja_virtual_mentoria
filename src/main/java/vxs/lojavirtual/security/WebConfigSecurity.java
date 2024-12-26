package vxs.lojavirtual.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import jakarta.servlet.http.HttpSessionListener;

@Configuration
@EnableMethodSecurity
public class WebConfigSecurity implements HttpSessionListener{

	
	  @Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    
		  http
		  		//.csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(( (authorize) -> authorize
	               .requestMatchers(HttpMethod.GET, "/salvarAcesso", "/deleteAcesso").permitAll()
	              .requestMatchers(HttpMethod.POST, "/salvarAcesso", "/deleteAcesso").permitAll()
	            .anyRequest().permitAll())
	            		
	            );
	        return http.build();
	  } 	     
	     
	  
}
