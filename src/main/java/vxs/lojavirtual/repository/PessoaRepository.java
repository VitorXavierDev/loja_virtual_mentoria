package vxs.lojavirtual.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.AssertFalse.List;
import vxs.lojavirtual.model.PessoaFisica;
import vxs.lojavirtual.model.PessoaJuridica;

@Repository
public interface PessoaRepository extends CrudRepository<PessoaJuridica, Long> {

	@Query(value = "select pj from PessoaJuridica pj where upper(trim(pj.nome)) like %?1%")
	public java.util.List<PessoaJuridica> pesquisaPorNomePJ(String nome);
	
	@Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
	public PessoaJuridica existeCnpjCadastrado(String cnpj);

	@Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
	public java.util.List<PessoaJuridica> existeCnpjCadastradoList(String cnpj);
	
	@Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
	public PessoaFisica existeCpfCadastrado(String cpf);
	
	@Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
	public java.util.List<PessoaFisica> existeCpfCadastradoList(String cpf);
	
	@Query(value = "select pj from PessoaJuridica pj where pj.inscEstadual = ?1")
	public PessoaJuridica existeInscricaoEstadualCadastrado(String inscEstadual);
	
	@Query(value = "select pj from PessoaJuridica pj where pj.inscEstadual = ?1")
	public java.util.List<PessoaJuridica> existeInscricaoEstadualCadastradoList(String inscEstadual);
}
