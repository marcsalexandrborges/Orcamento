package net.atos.api.orcamento.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.service.CriaOrcamento;

@RestController
@RequestMapping("/v1/orcamento")
//@Tag(name = "Orcamento")
public class OrcamentoController {

	private List<CriaOrcamento> criaOrcamentoStrategies;

	public OrcamentoController(List<CriaOrcamento> strategies) {
		super();

		this.criaOrcamentoStrategies = strategies;

	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON }, consumes = { MediaType.APPLICATION_JSON })
	@Operation(description = "Cria um orcamento")
	public ResponseEntity<OrcamentoVO> criaOrcamento(@Valid @RequestBody OrcamentoVO orcamento) {

		CriaOrcamento criaOrcamento = criaOrcamentoStrategies.stream()
				.findFirst()
				.orElseThrow(() -> new BadRequestException("Orcamento não Existe."));

		OrcamentoVO createdOrcamento = criaOrcamento.criar(orcamento);

		URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
				.buildAndExpand(createdOrcamento.getId()).toUri();

		return ResponseEntity.created(uri).body(createdOrcamento);
	}

//	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
//	@Operation(description = "Consulta uma nota fiscal por id")
//	public ResponseEntity<OrcamentoVO> getNotaFiscalPorId(@PathVariable("id") Long id) {
//
//		OrcamentoVO notaFiscalEncontrada = busca.porId(id);
//
//		return ResponseEntity.ok(notaFiscalEncontrada);
//	}


}
