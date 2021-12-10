package net.atos.api.orcamento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.atos.api.orcamento.domain.ItemVO;

@Repository
public interface IOrcamentoRepository extends CrudRepository<OrcamentoEntity, Long> {

	public Optional<List<OrcamentoEntity>> findByItem(ItemVO item);

}
