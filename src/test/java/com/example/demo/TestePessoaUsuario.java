package com.example.demo;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import junit.framework.TestCase;
import vxs.lojavirtual.aplication.LojaVirtualMentoriaAplication;
import vxs.lojavirtual.controller.PessoaController;
import vxs.lojavirtual.enums.TipoEndereco;
import vxs.lojavirtual.model.Endereco;
import vxs.lojavirtual.model.PessoaFisica;
import vxs.lojavirtual.model.PessoaJuridica;
import vxs.lojavirtual.repository.PessoaRepository;
import vxs.lojavirtual.service.PessoaUserService;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaAplication.class)
public class TestePessoaUsuario extends TestCase {


	@Autowired
	private PessoaController pessoaController;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	@Test
	public void testCardPessoaJuridica() {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		
		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Vitor Xavier");
		pessoaJuridica.setEmail("vitorxavier6677@gmail.com");
		pessoaJuridica.setTelefone("46999011022");
		pessoaJuridica.setInscEstadual("3949394");
		pessoaJuridica.setInscMunicipal("3141242123");
		pessoaJuridica.setNomeFantasia("vxs informatica");
		pessoaJuridica.setRazaoSocial("Vitor Xavier Mei");
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Jd Dias");
		endereco1.setCep("556556565");
		endereco1.setComplemento("Casa cinza");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("389");
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setRuaLogra("Av. são joao sexto");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUf("PR");
		endereco1.setCidade("Curitiba");
		
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jd Maracana");
		endereco2.setCep("7878778");
		endereco2.setComplemento("Andar 4");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("555");
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setRuaLogra("Av. maringá");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUf("PR");
		endereco2.setCidade("Curitiba");
		
		pessoaJuridica.getEnderecos().add(endereco2);
		pessoaJuridica.getEnderecos().add(endereco1);


		pessoaJuridica = pessoaController.salvarPj(pessoaJuridica).getBody();
		
		assertEquals(true, pessoaJuridica.getId() > 0 );
		
		for (Endereco endereco : pessoaJuridica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}
		
		assertEquals(2, pessoaJuridica.getEnderecos().size());

	}
	
	
	@Test
	public void testCardPessoaFisica() {
		
		PessoaJuridica pessoaJuridica = pessoaRepository.existeCnpjCadastrado("45839937000193");
		
		PessoaFisica pessoaFisica = new PessoaFisica();
		
		pessoaFisica.setCpf("10869800426");
		pessoaFisica.setNome("Vitor Xavier");
		pessoaFisica.setEmail("vitorxavier@gmail.com");
		pessoaFisica.setTelefone("46999011022");
		pessoaFisica.setEmpresa_id(pessoaJuridica);
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Jd Dias");
		endereco1.setCep("556556565");
		endereco1.setComplemento("Casa cinza");
		endereco1.setNumero("389");
		endereco1.setPessoa(pessoaFisica);
		endereco1.setRuaLogra("Av. são joao sexto");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUf("PR");
		endereco1.setCidade("Curitiba");
		endereco1.setEmpresa(pessoaJuridica);
		
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jd Maracana");
		endereco2.setCep("7878778");
		endereco2.setComplemento("Andar 4");
		endereco2.setNumero("555");
		endereco2.setPessoa(pessoaFisica);
		endereco2.setRuaLogra("Av. maringá");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUf("PR");
		endereco2.setCidade("Curitiba");
		endereco2.setEmpresa(pessoaJuridica);
		
		pessoaFisica.getEnderecos().add(endereco2);
		pessoaFisica.getEnderecos().add(endereco1);


		pessoaFisica = pessoaController.salvarPf(pessoaFisica).getBody();
		
		assertEquals(true, pessoaFisica.getId() > 0 );
		
		for (Endereco endereco : pessoaFisica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}
		
		assertEquals(2, pessoaFisica.getEnderecos().size());

	}
}
