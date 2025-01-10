package vxs.lojavirtual.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vxs.lojavirtual.model.PessoaFisica;
import vxs.lojavirtual.model.PessoaJuridica;

@Repository
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica, Long> {

	

	

}
