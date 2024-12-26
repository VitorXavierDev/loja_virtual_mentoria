package com.example.demo;

import java.awt.PageAttributes.MediaType;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;
import vxs.lojavirtual.aplication.LojaVirtualMentoriaAplication;
import vxs.lojavirtual.controller.AcessoController;
import vxs.lojavirtual.model.Acesso;
import vxs.lojavirtual.repository.AcessoRepository;
import vxs.lojavirtual.service.AcessoService;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaAplication.class)
class LojaVirtualMentoriaApplicationTests extends TestCase {

	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private WebApplicationContext wac;
	
	
	@Autowired
	private AcessoController acessoController;
	
	@Test
	public void testRestApiCadastroAcesso() throws Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_COMPRADOR");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		
		
		ResultActions retornoApi = mockMvc
											.perform(MockMvcRequestBuilders.post("/salvarAcesso")
											.content(objectMapper.writeValueAsString(acesso))
											.accept(org.springframework.http.MediaType.APPLICATION_JSON)
											.contentType(org.springframework.http.MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API" + retornoApi.andReturn().getResponse().getContentAsString());
		
		/*Conveter o retorno de APi para o objeto
		 */
		
		Acesso objetoRetorno = objectMapper.
				readValue(retornoApi.andReturn().getResponse().getContentAsString(),
						Acesso.class);
		
		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
	}
	
	@Test
	public void testRestApiDeleteAcesso() throws Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_TESTE_DELETE");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
											.perform(MockMvcRequestBuilders.post("/deleteAcesso")
											.content(objectMapper.writeValueAsString(acesso))
											.accept(org.springframework.http.MediaType.APPLICATION_JSON)
											.contentType(org.springframework.http.MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API" + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Status de Retorno: " + retornoApi.andReturn().getResponse().getStatus());
		
		assertEquals("Acesso removido", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		/*Conveter o retorno de APi para o objeto
		 */
	
	}
	
	
	@Test
	public void testRestApiDeleteAcessoPorIDAcesso() throws Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_TESTE_DELETE_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
											.perform(MockMvcRequestBuilders.delete("/deleteAcessoPorid/" + acesso.getId())
											.content(objectMapper.writeValueAsString(acesso))
											.accept(org.springframework.http.MediaType.APPLICATION_JSON)
											.contentType(org.springframework.http.MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API" + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Status de Retorno: " + retornoApi.andReturn().getResponse().getStatus());
		
		assertEquals("Acesso removido", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		/*Conveter o retorno de APi para o objeto
		 */
	
	}
	
		@Test
		public void testRestApiObterAcessoID() throws Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_OBTER_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
											.perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
											.content(objectMapper.writeValueAsString(acesso))
											.accept(org.springframework.http.MediaType.APPLICATION_JSON)
											.contentType(org.springframework.http.MediaType.APPLICATION_JSON));
		

		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	
		Acesso acessoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);		
		
		assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
		
		assertEquals(acesso.getId(), acessoRetorno.getId());
	
	}
		
		@Test
		public void testRestApiBuscarPorDesc() throws Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_TESTE_OBTER_LIST");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
											.perform(MockMvcRequestBuilders.get("/buscarPorDesc/OBTER_LIST")
											.content(objectMapper.writeValueAsString(acesso))
											.accept(org.springframework.http.MediaType.APPLICATION_JSON)
											.contentType(org.springframework.http.MediaType.APPLICATION_JSON));
		

		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		List<Acesso> retornoApiList = objectMapper.
								readValue(retornoApi.andReturn()
										.getResponse().getContentAsString(),
										new TypeReference<List<Acesso>>() {
		});
		
		assertEquals(1, retornoApiList.size());
	
		assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());
		
		acessoRepository.deleteById(acesso.getId());
	
	}
	
	@Test
	public void testeCadastraAcesso() {
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ADMIN");
		
		assertEquals(true, acesso.getId() == null);
		
		/*Gravou no banco de dados*/
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		assertEquals(true, acesso.getId() > 0);
		
		/*validar dados salvos da forma correta*/
		assertEquals("ROLE_ADMIN", acesso.getDescricao());
		
		/*Teste de carregamento*/
		
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
		
		assertEquals(acesso.getId(), acesso2.getId());
		
		/*teste de delete*/
		
		acessoRepository.deleteById(acesso2.getId());
		
		acessoRepository.flush(); /*Roda  esse SQL de  delete no banco de dados*/
		
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
		
		assertEquals(true, acesso3 == null);
		
		/*Teste de query*/
		
		acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ALUNO");
		
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());
		
		assertEquals(1, acessos.size());
		
		acessoRepository.deleteById(acesso.getId());
		
	}

}
