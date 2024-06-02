package vxs.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vxs.lojavirtual.model.Acesso;
import vxs.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {

	@Autowired
	private AcessoRepository acessoRepository;
	
	public Acesso save(Acesso acesso) {
		
		/*Qualquer tipo de validação */
		
		return acessoRepository.save(acesso);
		
	}
	
}
