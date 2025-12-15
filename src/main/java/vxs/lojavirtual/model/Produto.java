package vxs.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;

	@Column(nullable = false)
	private String tipoUnidade;

	@NotNull(message = "Nome do produto deve ser informado")
	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private Boolean ativo = Boolean.TRUE;
	
	@Column(nullable = false)
	private Double altura;

	@Column(columnDefinition = "text", length = 2000, nullable = false)
	private String descricao;

	/* Nota item Produto, associar */
	@Column(nullable = false)
	private Double peso; /* 1000,55 */

	@Column(nullable = false)
	private Double largura;

	@Column(nullable = false)
	private Double profundidade;

	@Column(nullable = false)
	private BigDecimal valorVenda = BigDecimal.ZERO;

	@Column(nullable = false)
	private Integer QtdEstoque = 0;

	private Integer QtdAlertaEstoque = 0;

	private String linkYoutube;

	private Boolean alertaQtaEstoque = Boolean.FALSE;

	private Integer qtdeClique = 0;
	
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private Pessoa empresa_id;

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Pessoa getEmpresa_id() {
		return empresa_id;
	}

	public void setEmpresa_id(Pessoa empresa_id) {
		this.empresa_id = empresa_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(String tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Integer getQtdEstoque() {
		return QtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		QtdEstoque = qtdEstoque;
	}

	public Integer getQtdAlertaEstoque() {
		return QtdAlertaEstoque;
	}

	public void setQtdAlertaEstoque(Integer qtdAlertaEstoque) {
		QtdAlertaEstoque = qtdAlertaEstoque;
	}

	public String getLinkYoutube() {
		return linkYoutube;
	}

	public void setLinkYoutube(String linkYoutube) {
		this.linkYoutube = linkYoutube;
	}

	public Boolean getAlertaQtaEstoque() {
		return alertaQtaEstoque;
	}

	public void setAlertaQtaEstoque(Boolean alertaQtaEstoque) {
		this.alertaQtaEstoque = alertaQtaEstoque;
	}

	public Integer getQtdeClique() {
		return qtdeClique;
	}

	public void setQtdeClique(Integer qtdeClique) {
		this.qtdeClique = qtdeClique;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

}
