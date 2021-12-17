package net.atos.api.orcamento.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ItemVO {
	
	@NotNull(message = "Codigo do item não pode ser nulo")
	private Integer codigoItem;
	
	@NotNull(message = "Campo preço unitario não pode ser nulo")
	private Double precoUnitario;
	
	@NotNull(message = "Campo descricao não pode ser nulo")
	private String descricao;
	
	@NotNull(message = "Campo quantidade não pode ser nulo")
	private Integer quantidade;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Double valorItens;

}
