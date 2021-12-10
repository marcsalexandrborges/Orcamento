package net.atos.api.orcamento.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;


import net.atos.api.orcamento.domain.ItemVO;
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
	
	public List<OrcamentoVO> porItem(ItemVO item) {
		List<OrcamentoEntity> orcamentoEntity = orcamentoRepository.findByItem(item)
				.orElseThrow(()->
				     new NotFoundException("Nenhum orcamento para o item informado"));
		
		return orcamentoEntity.stream()
				.map(OrcamentoFactory::new)
				.map(OrcamentoFactory::toVO)
				.collect(Collectors.toList()); 
				
	}

}
