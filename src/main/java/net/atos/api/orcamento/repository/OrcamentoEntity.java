package net.atos.api.orcamento.repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
@Entity
@Table(name = "TB_ORCAMENTO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class OrcamentoEntity implements Serializable {
	
	private static final long serialVersionUID = -6306260709157057159L;
	
	@Id
	@Column(name = "ID_ORCAMENTO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_orcamento")	
	@SequenceGenerator(name = "sq_orcamento",sequenceName = "sequence_orcamento", allocationSize = 1, initialValue = 1)
	private Long id;

	@NotNull(message = "Campo item não pode ser nulo")
	@Size(min = 1, message = "Campo item não pode ser nulo")
	@Valid
	@OneToMany(mappedBy = "id.orcamento", cascade = CascadeType.ALL)
	private List<ItemEntity> itens;
	
	@Column(name = "VALOR_ORCAMENTO")
	@Positive
	private Double valor;
	
	@Column(name = "DT_EMISSAO")
	private LocalDate dataEmissao;
	
	@Column(name = "ORCAMENTO_STATUS")
	private String status;
	 
	public void add(ItemEntity item) {
		List<ItemEntity> itensOrcamento = 
				Optional.ofNullable(this.getItens()).orElseGet(()->new ArrayList());		
		itensOrcamento.add(item);
		
		this.itens = itensOrcamento; 
	}
	
	@PrePersist
	public void somaValorOrcamento() {
		Double valorOrcamento = 0.0;
		for (ItemEntity itemEntity : this.getItens()) {
			valorOrcamento +=  itemEntity.getValorTotalItens();
		}		
		this.setValor(valorOrcamento);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrcamentoEntity other = (OrcamentoEntity) obj;
		return Objects.equals(id, other.id);
	}

}
