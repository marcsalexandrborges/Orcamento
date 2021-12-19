package net.atos.api.orcamento.domain;

public enum StatusEnum {
	VALIDO(1, "Válido"),
	EXPIRADO(2, "Expirado");

	private Integer codigo;
	
	private String descricao;
	
	private StatusEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
}