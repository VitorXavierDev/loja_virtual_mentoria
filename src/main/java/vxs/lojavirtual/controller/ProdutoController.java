package vxs.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vxs.lojavirtual.expections.ExceptionMentoriaJava;
import vxs.lojavirtual.model.Acesso;
import vxs.lojavirtual.model.Produto;
import vxs.lojavirtual.repository.AcessoRepository;
import vxs.lojavirtual.repository.ProdutoRepository;
import vxs.lojavirtual.service.AcessoService;

@Controller
@RestController
public class ProdutoController {

	@Autowired
	private AcessoService acessoService;

	@Autowired
	private ProdutoRepository produtoRepository;

	@ResponseBody /* poder dar um retorno da APi */
	@PostMapping(value = "/salvarProduto") /* Mapeando o APi para receber o JSON */
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Validated Produto produto) throws ExceptionMentoriaJava {

		if (produto.getId() == null) {
			
			List<Produto> produtos = produtoRepository.buscarProdutoNome(produto.getDescricao().toUpperCase());
			if (!produtos.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe Produto com esse nome " + produto.getNome());
			}
		}

		Produto produtoSalvo = produtoRepository.save(produto);

		return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.OK);

	}

	@ResponseBody /* poder dar um retorno da APi */
	@PostMapping(value = "/deleteProduto") /* Mapeando o APi para receber o JSON */
	public ResponseEntity<String> deleteProduto(@RequestBody Produto produto) {

		produtoRepository.deleteById(produto.getId());

		return new ResponseEntity<String>("Produto removido", HttpStatus.OK);

	}

	@ResponseBody
	@DeleteMapping(value = "/deleteProdutoPorid/{id}")
	public ResponseEntity<String> deleteProdutoPorid(@PathVariable("id") Long id) {

		produtoRepository.deleteById(id);

		return new ResponseEntity<String>("Produto removido", HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "/obterProduto/{id}")
	public ResponseEntity<Produto> obterProduto(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

		Produto produto = produtoRepository.findById(id).orElse(null);

		if (produto == null) {

			throw new ExceptionMentoriaJava("Não encontrou o produto com código" + id);
		}

		return new ResponseEntity<Produto>(produto, HttpStatus.OK);

	}

	// @Secured({"ROLE_GERENTE", "ROLE_ADMIN"})
	@ResponseBody
	@GetMapping(value = "/buscarProdNome/{nome}")
	public ResponseEntity<List<Produto>> buscarProdNome(@PathVariable("nome") String nome) {

		List<Produto> produto = produtoRepository.buscarProdutoNome(nome.toUpperCase());

		return new ResponseEntity<List<Produto>>(produto, HttpStatus.OK);

	}
}
