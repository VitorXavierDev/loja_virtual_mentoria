package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vxs.lojavirtual.aplication.LojaVirtualMentoriaAplication;
import vxs.lojavirtual.controller.AcessoController;
import vxs.lojavirtual.model.Acesso;
import vxs.lojavirtual.repository.AcessoRepository;
import vxs.lojavirtual.service.AcessoService;

@SpringBootTest(classes = LojaVirtualMentoriaAplication.class)
class LojaVirtualMentoriaApplicationTests {

	@Autowired
	private AcessoService acessoService;
	
	//@Autowired
	//private AcessoRepository acessoRepository;
	
	@Autowired
	private AcessoController acessoController;
	
	@Test
	public void testeCadastraAcesso() {
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ADMIN");
		
		acessoController.salvarAcesso(acesso);
	}

}
