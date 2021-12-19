package net.atos.api.orcamento.controller;

import java.net.URI;

import javax.validation.Valid;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.service.BuscaOrcamento;
import net.atos.api.orcamento.service.CriaOrcamento;

@RestController
@RequestMapping("/v1/orcamento")
@Tag(name = "Orcamento")
public class OrcamentoController {

	private CriaOrcamento criaOrcamentoService;
	
	private BuscaOrcamento buscaOrcamentoService;

	public OrcamentoController(CriaOrcamento criaService, BuscaOrcamento buscaService) {
		super();
		
		this.criaOrcamentoService = criaService;
		this.buscaOrcamentoService = buscaService;

	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON }, consumes = { MediaType.APPLICATION_JSON })
	@Operation(description = "Cria um orcamento")
	public ResponseEntity<OrcamentoVO> criaOrcamento(@Valid @RequestBody OrcamentoVO orcamento) {

		OrcamentoVO createdOrcamento = criaOrcamentoService.criar(orcamento);

		URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
				.buildAndExpand(createdOrcamento.getId()).toUri();

		return ResponseEntity.created(uri).body(createdOrcamento);
	}
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Consulta uma orcamento por id")
	public ResponseEntity<OrcamentoVO> getOrcamentoPorId(@PathVariable("id") Long id) {

		OrcamentoVO orcamentoEncontrado = buscaOrcamentoService.porId(id);

		return ResponseEntity.ok(orcamentoEncontrado);
	}
}
