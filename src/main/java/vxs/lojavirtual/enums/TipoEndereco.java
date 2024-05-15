package vxs.lojavirtual.enums;

public enum TipoEndereco {

	COBRANCA("Cobrança"),
	ENTREGA("Entrega");
	
	private String descrição;
	
	private TipoEndereco(String descricao) {
		this.descrição = descricao;
	}

	public String getDescrição() {
		return descrição;
	}


	@Override
	public String toString() {
		return this.descrição;
	}
	
	
	
}
