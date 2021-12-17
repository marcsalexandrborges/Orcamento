package net.atos.api.orcamento.controller;

import java.net.URI;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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
import net.atos.api.orcamento.config.PageableBinding;
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
	@Operation(description = "Consulta uma nota fiscal por id")
	public ResponseEntity<OrcamentoVO> getNotaFiscalPorId(@PathVariable("id") Long id) {

		OrcamentoVO orcamentoEncontrado = buscaOrcamentoService.porId(id);

		return ResponseEntity.ok(orcamentoEncontrado);
	}
	
	@PageableBinding
	@GetMapping(value = "/emissao-periodos/{dataEmissao}/{dataFim}", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Consulta orcamento por período")
	public ResponseEntity<Page<OrcamentoVO>> getNotaFiscaisPorPeriodo(
			@PathVariable("dataEmissao") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataEmissao,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFim,
			@ParameterObject @PageableDefault(sort = {
					"dataEmissao" }, direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {

		Page<OrcamentoVO> notasFiscaisEncontradas = this.buscaOrcamentoService.porPeriodoDataEmissao(dataEmissao,
				dataFim, pageable);

		return ResponseEntity.ok(notasFiscaisEncontradas);

	}
}
