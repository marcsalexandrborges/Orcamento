package net.atos.api.orcamento.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrcamentoRepository extends PagingAndSortingRepository<OrcamentoEntity, Long> {
	
}
