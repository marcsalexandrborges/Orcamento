package net.atos.api.orcamento.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrcamentoRepository extends PagingAndSortingRepository<OrcamentoEntity, Long> {
	
	public Page<OrcamentoEntity> findByDataEmissaoBetween(LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

}
