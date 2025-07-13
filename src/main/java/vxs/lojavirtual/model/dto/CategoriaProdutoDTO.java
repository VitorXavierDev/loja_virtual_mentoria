package vxs.lojavirtual.model.dto;

import java.io.Serializable;

public class CategoriaProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nomeDesc;
	
	private String Empresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDesc() {
		return nomeDesc;
	}

	public void setNomeDesc(String nomeDesc) {
		this.nomeDesc = nomeDesc;
	}

	public String getEmpresa() {
		return Empresa;
	}

	public void setEmpresa(String empresa) {
		Empresa = empresa;
	}




	
	
	

}
