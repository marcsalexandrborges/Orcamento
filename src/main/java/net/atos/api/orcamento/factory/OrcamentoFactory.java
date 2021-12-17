package net.atos.api.orcamento.factory;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import net.atos.api.orcamento.domain.ItemVO;
import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.repository.ItemEntity;
import net.atos.api.orcamento.repository.ItemPK;
import net.atos.api.orcamento.repository.OrcamentoEntity;

public class OrcamentoFactory {
	
	private OrcamentoVO orcamentoVO;
	private OrcamentoEntity orcamentoEntity;
	
	public OrcamentoFactory(OrcamentoVO orcamento) {
		this.orcamentoEntity = this.transformaEntity(orcamento);
		this.orcamentoVO = orcamento;	
	}
	
	public OrcamentoFactory(OrcamentoEntity orcEntity) {
		this.orcamentoEntity = orcEntity;
		this.orcamentoVO = this.transformaVO(orcEntity);		
	}

	private OrcamentoVO transformaVO(OrcamentoEntity orcEntity) {
		OrcamentoVO orcVO = new OrcamentoVO();
		orcVO.setId(orcEntity.getId());
		orcVO.setDataEmissao(orcEntity.getDataEmissao());
		orcVO.setValor(orcEntity.getValor());
		orcVO.setStatus(orcEntity.getStatus());
		
		AtomicInteger numeroItem = new AtomicInteger(); 		
		List<ItemEntity> itens = Optional.ofNullable(orcEntity.getItens()).orElseGet(ArrayList::new);
		itens.stream().forEach(item -> 
				this.construirItemVO(orcVO, numeroItem, item));
		
		return orcVO;
	}

	private void construirItemVO(OrcamentoVO orcVO, AtomicInteger numeroItem, ItemEntity item) {
		ItemVO itemVO = new ItemVO();
		itemVO.setCodigoItem(item.getCodigoItem());
		itemVO.setPrecoUnitario(item.getPrecoUnitario());
		itemVO.setDescricao(item.getDescricao());
		itemVO.setQuantidade(item.getQuantidade());
		itemVO.setValorItens(item.getValorItens());
		
		orcVO.add(itemVO);
	}

	private OrcamentoEntity transformaEntity(OrcamentoVO orcamento) {
		OrcamentoEntity orcEntity = new OrcamentoEntity();
		orcEntity.setDataEmissao(orcamento.getDataEmissao());
		orcEntity.setValor(orcamento.getValor());
		orcEntity.setStatus(orcamento.getStatus());
		
		AtomicInteger numeroItem = new AtomicInteger(); 
		orcamento.getItens().stream().forEach(item -> 
				this.construirItemEntity(orcEntity, numeroItem, item));
		
		return orcEntity;
	}

	private void construirItemEntity(OrcamentoEntity orcEntity, AtomicInteger numeroItem, ItemVO item) {
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setId(new ItemPK());
		itemEntity.getId().setItem(numeroItem.incrementAndGet());
		itemEntity.getId().setOrcamento(orcEntity);
		itemEntity.setCodigoItem(item.getCodigoItem());
		itemEntity.setPrecoUnitario(item.getPrecoUnitario());
		itemEntity.setDescricao(item.getDescricao());
		itemEntity.setQuantidade(item.getQuantidade());
		itemEntity.setValorItens(item.getValorItens());
		
		orcEntity.add(itemEntity);
	}
	
	public OrcamentoEntity toEntity() {		
		return this.orcamentoEntity;
	}
	
	public OrcamentoVO toVO() {		
		return this.orcamentoVO;
	}

}
