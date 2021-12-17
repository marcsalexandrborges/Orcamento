package net.atos.api.orcamento.events;

import net.atos.api.orcamento.domain.OrcamentoVO;

public class OrcamentoCreatedEvent {
	
	private OrcamentoVO orcamento;
	
	public OrcamentoCreatedEvent(OrcamentoVO orc) {
		this.orcamento = orc;		
	}

	public OrcamentoVO getNotaFiscal() {
		return orcamento;
	}
	

}