package vxs.lojavirtual.aplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "vxs.lojavirtual.model")
@SpringBootApplication
public class LojaVirtualMentoriaAplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualMentoriaAplication.class, args);
	}
}
