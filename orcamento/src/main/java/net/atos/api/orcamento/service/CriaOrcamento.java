package net.atos.api.orcamento.service;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.factory.OrcamentoFactory;
import net.atos.api.orcamento.repository.IOrcamentoRepository;
import net.atos.api.orcamento.repository.OrcamentoEntity;

	@Service
	public class CriaOrcamento implements ICriaOrcamento  {
		
		private Validator validator;
		
		private IOrcamentoRepository orcamentoRepository;
		
		public CriaOrcamento(Validator validator, IOrcamentoRepository repository) {
			this.validator = validator;
			this.orcamentoRepository = repository;
		}
	
		@Transactional
		public OrcamentoVO criar(@NotNull(message = "Orçamento não pode ser nulo") OrcamentoVO orcamento) {

			Set<ConstraintViolation<OrcamentoVO>> 
			validateMessages = this.validator.validate(orcamento);

			if (!validateMessages.isEmpty()) {
				throw new ConstraintViolationException("Orçamento Inválido",validateMessages);
			}

			if (!orcamento.getDataEmissao().isEqual(LocalDate.now())) {
				throw new BadRequestException("A data de emissão do orcamento deve ser atual.");			
			}
			
			OrcamentoEntity orcamentoEntity = new OrcamentoFactory(orcamento).toEntity();				

			orcamentoRepository.save(orcamentoEntity);		
			
			orcamento.setId(orcamentoEntity.getId());	
			
			return orcamento; 
	
		
	}
	
	@Override
	public boolean isValid(Integer numOrcamento) {
		return numOrcamento > 0;	
	}

}