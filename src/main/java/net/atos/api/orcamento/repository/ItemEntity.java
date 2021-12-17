package net.atos.api.orcamento.repository;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_ORCAMENTO_ITEM")
public class ItemEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2896849459134680393L;

	@EmbeddedId
	private ItemPK id;	

	@Column(name = "COD_ITEM")
	@NotNull(message = "Codigo do item não pode ser nulo")
	private Integer codigoItem;
	
	@Column(name = "PRECO_UNITARIO")
	@NotNull(message = "Campo preço unitario não pode ser nulo")
	private Double precoUnitario;
	
	@Column(name = "DESCRICAO_ITEM")
	@NotNull(message = "Campo descricao não pode ser nulo")
	private String descricao;
	
	@Column(name = "QTD_ITENS")
	@NotNull(message = "Campo quantidade não pode ser nulo")
	private Integer quantidade;
	
	@Column(name = "VALOR_ITENS")
	private Double valorItens;
	
	@PrePersist
	public void valorTotalItem() {
		this.setValorItens(this.getValorTotalItens());		
	}
	
	public Double getValorTotalItens() {
		Double total = 0.0;
		total = this.getPrecoUnitario() * this.getQuantidade();
		return total;		
	}
	
}
                    