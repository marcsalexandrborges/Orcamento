package net.atos.api.orcamento.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ItemVO {
	
	@NotNull(message = "Codigo do item não pode ser nulo")
	private Integer codigoItem;
	
	@NotNull(message = "Campo preço unitario não pode ser nulo")
	private Double precoUnitario;
	
}
