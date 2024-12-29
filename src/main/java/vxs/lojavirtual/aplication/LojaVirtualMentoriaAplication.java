package vxs.lojavirtual.aplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages = "vxs.lojavirtual.model")
@SpringBootApplication
@ComponentScan(basePackages = {"vxs.*"})
@EnableJpaRepositories(basePackages = {"vxs.lojavirtual.repository"})
@EnableTransactionManagement
public class LojaVirtualMentoriaAplication {

	public static void main(String[] args) {
		
		
		System.out.println(new BCryptPasswordEncoder().encode("123"));
		SpringApplication.run(LojaVirtualMentoriaAplication.class, args);
	}
}
