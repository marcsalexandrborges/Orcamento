package net.atos.api.orcamento.service;

import java.time.LocalDate;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.domain.StatusEnum;
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
		
		if(!orcamentoEntity.getDataEmissao().isAfter(LocalDate.now().minusDays(7L))) {
			orcamentoEntity.setStatus(StatusEnum.EXPIRADO.getDescricao());
			orcamentoRepository.save(orcamentoEntity);
		} else {
			orcamentoEntity.setStatus(StatusEnum.VALIDO.getDescricao());
			orcamentoRepository.save(orcamentoEntity);
		}
		
		return new OrcamentoFactory(orcamentoEntity).toVO();
	}
}
