package vxs.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vxs.lojavirtual.expections.ExceptionMentoriaJava;
import vxs.lojavirtual.model.Acesso;
import vxs.lojavirtual.model.CategoriaProduto;
import vxs.lojavirtual.model.dto.CategoriaProdutoDTO;
import vxs.lojavirtual.repository.CategoriaProdutoRepository;

@RestController
public class CategoriaProdutoController {

	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	
	@ResponseBody
	@GetMapping(value = "/buscarCategoriaPorDesc/{desc}")
	public ResponseEntity<List<CategoriaProduto>> buscarCategoriaPorDesc(@PathVariable("desc") String desc) {

		List<CategoriaProduto> categoriaProduto = categoriaProdutoRepository.buscarCategoriaDesc(desc.toUpperCase());

		return new ResponseEntity<List<CategoriaProduto>>(categoriaProduto, HttpStatus.OK);

	}
	
	@ResponseBody /* poder dar um retorno da APi */
	@PostMapping(value = "/deleteCategoria") /* Mapeando o APi para receber o JSON */
	public ResponseEntity<?> deleteCategoria(@RequestBody CategoriaProduto categoriaProduto) {

		categoriaProdutoRepository.deleteById(categoriaProduto.getId());

		return new ResponseEntity("Categoria removida", HttpStatus.OK);

	}
	
	@ResponseBody
	@PostMapping(value = "/salvarCategoria")
	public ResponseEntity<CategoriaProdutoDTO> salvarCategoria(@RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoriaJava {
		
		if(categoriaProduto.getEmpresa() == null || ( categoriaProduto.getEmpresa().getId() == null  || categoriaProduto.getEmpresa().getId() <= 0)) {
			throw new ExceptionMentoriaJava("A empresa deve ser informada.");
		}
		
		if (categoriaProduto.getId() == null && categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc().toUpperCase())) {
			throw new ExceptionMentoriaJava("Não pode cadastrar categoria com o mesmo nome");
		}
		
		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);
		
		CategoriaProdutoDTO categoriaProdutoDTO = new CategoriaProdutoDTO();
		categoriaProdutoDTO.setId(categoriaSalva.getId());
		categoriaProdutoDTO.setNomeDesc(categoriaSalva.getNomeDesc());
		categoriaProdutoDTO.setEmpresa(categoriaSalva.getEmpresa().getId().toString());
		
		
		return new ResponseEntity<CategoriaProdutoDTO>(categoriaProdutoDTO, HttpStatus.OK);
	}
	
}
