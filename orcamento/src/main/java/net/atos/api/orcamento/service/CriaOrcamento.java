package net.atos.api.orcamento.service;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.repository.OrcamentoRepository;

	@Service
	public class CriaOrcamento  {
		
		private Validator validator;
		
		private OrcamentoRepository orcamentoRepository;
		
		public CriaOrcamento(Validator validator, OrcamentoRepository repository) {
			this.validator = validator;
			this.orcamentoRepository = repository;
		}
	
		@Transactional
		public void criar(OrcamentoVO orcamento) {

			Set<ConstraintViolation<OrcamentoVO>> 
			validateMessages = this.validator.validate(orcamento);

			if (!validateMessages.isEmpty()) {
				throw new ConstraintViolationException("Orçamento Inválido",validateMessages);
			}

			if (!orcamento.getDataEmissao().isEqual(LocalDate.now())) {
				throw new BadRequestException("A data de emissão do orcamento deve ser atual.");			
			}
			
			if (orcamento.getNumeroOrcamento() == null) {
				throw new BadRequestException("O numero do orcamento não pode ser nulo.");
			}
	
		
	}

}