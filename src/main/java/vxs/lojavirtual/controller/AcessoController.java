package vxs.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vxs.lojavirtual.expections.ExceptionMentoriaJava;
import vxs.lojavirtual.model.Acesso;
import vxs.lojavirtual.repository.AcessoRepository;
import vxs.lojavirtual.service.AcessoService;

@Controller
@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoRepository acessoRepository;

	@ResponseBody /* poder dar um retorno da APi */
	@PostMapping(value = "/salvarAcesso") /* Mapeando o APi para receber o JSON */
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoriaJava {

		if (acesso.getId() == null) {

			List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
			if (!acessos.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe Acesso com a descrição " + acesso.getDescricao());
			}
		}

		Acesso acessoSalvo = acessoService.save(acesso);

		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);

	}

	@ResponseBody /* poder dar um retorno da APi */
	@PostMapping(value = "/deleteAcesso") /* Mapeando o APi para receber o JSON */
	public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) {

		acessoRepository.deleteById(acesso.getId());

		return new ResponseEntity("Acesso removido", HttpStatus.OK);

	}

	@ResponseBody
	@DeleteMapping(value = "/deleteAcessoPorid/{id}")
	public ResponseEntity<?> deleteAcessoPorid(@PathVariable("id") Long id) {

		acessoRepository.deleteById(id);

		return new ResponseEntity("Acesso removido", HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

		Acesso acesso = acessoRepository.findById(id).orElse(null);

		if (acesso == null) {

			throw new ExceptionMentoriaJava("Não encontrou o acesso com código" + id);
		}

		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);

	}

	// @Secured({"ROLE_GERENTE", "ROLE_ADMIN"})
	@ResponseBody
	@GetMapping(value = "/buscarPorDesc/{desc}")
	public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc) {

		List<Acesso> acesso = acessoRepository.buscarAcessoDesc(desc.toUpperCase());

		return new ResponseEntity<List<Acesso>>(acesso, HttpStatus.OK);

	}
}
