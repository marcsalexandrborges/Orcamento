package net.atos.api.orcamento.events;

import net.atos.api.orcamento.domain.OrcamentoVO;

public class OrcamentoCanceleEvent {
	
private OrcamentoVO orcamento;
	
	public OrcamentoCanceleEvent(OrcamentoVO orc) {
		this.orcamento = orc;		
	}

	public OrcamentoVO getOrcamento() {
		return orcamento;
	}

}
