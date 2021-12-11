package net.atos.api.orcamento.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.factory.OrcamentoFactory;
import net.atos.api.orcamento.repository.IOrcamentoRepository;
import net.atos.api.orcamento.repository.OrcamentoEntity;

@Service
public class BuscaOrcamento {
	
	private Validator validator;
	
	private IOrcamentoRepository orcamentoRepository;
	
	public BuscaOrcamento(Validator validator, IOrcamentoRepository repository) {
		this.validator = validator;
		this.orcamentoRepository = repository;
	}
		
	public OrcamentoVO porId(long id) {
		OrcamentoEntity orcamentoEntity = this.orcamentoRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Orçamento não encontrado com o id: = "+id));
		
		return new OrcamentoFactory(orcamentoEntity).toVO();
	}
	
	public Page<OrcamentoVO>  porPeriodoDataEmissao(LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
			
			Page<OrcamentoEntity> orcamentoEntity = 
					     orcamentoRepository.findByDataEmissaoBetween(dataInicio,dataFim, pageable);
			
			if(orcamentoEntity.isEmpty()) {
				throw new NotFoundException("Nenhuma nota fiscal para o periodo informado");	
			}
			
			
			return new PageImpl<>(orcamentoEntity.getContent().stream()
					.map(OrcamentoFactory::new)
					.map(OrcamentoFactory::toVO)
					.collect(Collectors.toList()),
					orcamentoEntity.getPageable(),
					orcamentoEntity.getTotalElements());		     
			
			 
			
		}

}
