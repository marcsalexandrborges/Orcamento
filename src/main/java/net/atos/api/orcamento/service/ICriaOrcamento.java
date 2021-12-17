package net.atos.api.orcamento.service;

import javax.validation.constraints.NotNull;

import net.atos.api.orcamento.domain.OrcamentoVO;

public interface ICriaOrcamento {
	
	public OrcamentoVO criar(@NotNull(message = "Orçamento não pode ser nulo") OrcamentoVO orcamento);
	
	public boolean isValid(Integer numOrcamento);

}
