package net.atos.api.orcamento.controller;

import java.util.List;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import net.atos.api.orcamento.service.CriaOrcamento;

@RestController
@RequestMapping("/v1/orcamento")
//@Tag(name = "Orcamento")
public class OrcamentoController {
	
	private List<CriaOrcamento> criaOrcamentoStrategies;

	
	public OrcamentoController(List<CriaOrcamento> strategies ) {
		super();
		
		this.criaOrcamentoStrategies = strategies;
		
	}


}
