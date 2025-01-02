package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import junit.framework.TestCase;
import vxs.lojavirtual.aplication.LojaVirtualMentoriaAplication;
import vxs.lojavirtual.model.PessoaFisica;
import vxs.lojavirtual.model.PessoaJuridica;
import vxs.lojavirtual.repository.PessoaRepository;
import vxs.lojavirtual.service.PessoaUserService;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaAplication.class)
public class TestePessoaUsuario extends TestCase {

	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Test
	public void testCardPessoaFisica() {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		
		pessoaJuridica.setCnpj("45839937000193");
		pessoaJuridica.setNome("Vitor Xavier");
		pessoaJuridica.setEmail("vitorxavier6677@gmail.com");
		pessoaJuridica.setTelefone("46999011022");
		pessoaJuridica.setInscEstadual("3949394");
		pessoaJuridica.setInscMunicipal("3141242123");
		pessoaJuridica.setNomeFantasia("vxs informatica");
		pessoaJuridica.setRazaoSocial("VITOR xavier MEI");
		
		pessoaRepository.save(pessoaJuridica);
		
		/*
		PessoaFisica pessoaFisica = new PessoaFisica();
		
		pessoaFisica.setCpf("13703136405");
		pessoaFisica.setNome("Vitor Xavier");
		pessoaFisica.setEmail("vitorxavier6677@gmail.com");
		pessoaFisica.setTelefone("46999011022");
		
		pessoaFisica.setEmpresa_id(pessoaFisica);
		*/
		
	}
}
