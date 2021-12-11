package net.atos.api.orcamento.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrcamentoVO {
	
	private Long id;

	@NotNull(message = "Campo item não pode ser nulo")
	@Size(min = 1, message = "Campo item não pode ser nulo")
	@Valid
	private List<ItemVO> itens;
	
	@NotNull(message = "Campo quantidade não pode ser nulo")
	private Integer quantidade;
	
	@NotNull(message="Campo valor do orçamento não pode ser nulo")
	@Positive
	private BigDecimal valor;
	
	@NotNull(message = "Campo data de emissão não pode ser nula")
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataEmissao;
	
	public void add(ItemVO item) {
		List<ItemVO> itensOrcamento = 
				Optional.ofNullable(this.getItens()).orElseGet(()->new ArrayList());		
		itensOrcamento.add(item);
		
		this.itens = itensOrcamento; 
	}

}
