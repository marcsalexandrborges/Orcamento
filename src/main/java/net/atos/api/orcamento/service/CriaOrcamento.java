package net.atos.api.orcamento.service;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.domain.StatusEnum;
import net.atos.api.orcamento.events.OrcamentoCreatedEvent;
import net.atos.api.orcamento.factory.OrcamentoFactory;
import net.atos.api.orcamento.repository.IOrcamentoRepository;
import net.atos.api.orcamento.repository.OrcamentoEntity;

	@Service
	public class CriaOrcamento implements ICriaOrcamento  {
		
		private Validator validator;
		
		private IOrcamentoRepository orcamentoRepository;
		
		private ApplicationEventPublisher eventPublisher;
		
		public CriaOrcamento(Validator validator, IOrcamentoRepository  repository, ApplicationEventPublisher pEventPublisher) {
			this.validator = validator;
			this.orcamentoRepository = repository;
			
			this.eventPublisher =  pEventPublisher;
		}
	
		@Transactional
		public OrcamentoVO criar(@NotNull(message = "Orçamento não pode ser nulo") OrcamentoVO orcamento) {

			Set<ConstraintViolation<OrcamentoVO>> 
			validateMessages = this.validator.validate(orcamento);

			if (!validateMessages.isEmpty()) {
				orcamento.setStatus(StatusEnum.INVALIDO.getDescricao());
				throw new ConstraintViolationException("Orçamento Inválido",validateMessages);
			}
			
			orcamento.setStatus(StatusEnum.VALIDO.getDescricao());
			
			OrcamentoEntity orcamentoEntity = new OrcamentoFactory(orcamento).toEntity();	
			
			
			if(!orcamentoEntity.getDataEmissao().isAfter(LocalDate.now().minusDays(7L))) {
				orcamento.setStatus(StatusEnum.EXPIRADO.getDescricao());
			}
								
			
			orcamentoRepository.save(orcamentoEntity);	
			
			var orcamentoCreatedEvent = new OrcamentoCreatedEvent(orcamento);
			
			this.eventPublisher.publishEvent(orcamentoCreatedEvent);
			
			
			orcamento = new OrcamentoFactory(orcamentoEntity).toVO();
			
			return orcamento; 
	
		
		}
	
		@Override
		public boolean isValid(Integer numOrcamento) {
			return numOrcamento > 0;	
		}
		

}