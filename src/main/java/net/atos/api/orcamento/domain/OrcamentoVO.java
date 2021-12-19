package net.atos.api.orcamento.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrcamentoVO {
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;

	@NotNull(message = "Campo item não pode ser nulo")
	@Size(min = 1, message = "Campo item não pode ser nulo")
	@Valid
	private List<ItemVO> itens;
	
	@Positive
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Double valor;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataEmissao;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String status;
	
	public void add(ItemVO item) {
		List<ItemVO> itensOrcamento = 
				Optional.ofNullable(this.getItens()).orElseGet(()->new ArrayList());		
		itensOrcamento.add(item);
		
		this.itens = itensOrcamento; 
	}

}
