package vxs.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vxs.lojavirtual.expections.ExceptionMentoriaJava;
import vxs.lojavirtual.model.PessoaFisica;
import vxs.lojavirtual.model.PessoaJuridica;
import vxs.lojavirtual.repository.PessoaRepository;
import vxs.lojavirtual.service.PessoaUserService;
import vxs.lojavirtual.util.ValidaCnpj;
import vxs.lojavirtual.util.ValidaCpf;

@RestController
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	
	
	@ResponseBody
	@PostMapping(value = "/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj (@RequestBody @Valid PessoaJuridica pessoaJuridica){
		
		if (pessoaJuridica == null) {
			throw new ExceptionMentoriaJava("Pessoa Juridica não pode ser NULL");
		}
		
		if(pessoaJuridica.getId() == null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
			throw new ExceptionMentoriaJava("Já existe uma pessoa cadastrado com esse CNPJ" + pessoaJuridica.getCnpj());
		}
		
		if(pessoaJuridica.getId() == null && pessoaRepository.existeInscricaoEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
			throw new ExceptionMentoriaJava("Já existe uma pessoa cadastrado com essa Inscrição Estadual" + pessoaJuridica.getInscEstadual());
		}
		
		if (!ValidaCnpj.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoriaJava("CNPJ invalido, verifique novamente" + pessoaJuridica.getCnpj());
		}
		
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPf (@RequestBody PessoaFisica pessoaFisica){
		
		if (pessoaFisica == null) {
			throw new ExceptionMentoriaJava("Pessoa Fisica não pode ser NULL");
		}
		
		if(pessoaFisica.getId() == null && pessoaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
			throw new ExceptionMentoriaJava("Já existe uma pessoa cadastrado com esse CPF" + pessoaFisica.getCpf());
		}
		
		
		if (!ValidaCpf.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("CPF invalido, verifique novamente" + pessoaFisica.getCpf());
		}
		
		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);
		
		
		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
	}
}
