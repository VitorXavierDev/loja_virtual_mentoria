package vxs.lojavirtual.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vxs.lojavirtual.model.PessoaFisica;
import vxs.lojavirtual.model.PessoaJuridica;

@Repository
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica, Long> {

	@Query(value = "select pf from PessoaFisica pf where upper(trim(pf.nome)) like %?1%")
	public java.util.List<PessoaFisica> pesquisaPorNomePF(String nome);

	@Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
	public java.util.List<PessoaFisica> pesquisaPorCpfPF(String cpf);
	

}
