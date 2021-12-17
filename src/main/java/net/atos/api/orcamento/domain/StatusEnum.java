package net.atos.api.orcamento.domain;

public enum StatusEnum {
	VALIDO(1, "Válido"),
	INVALIDO(2, "Inválido"),
	EXPIRADO(3, "Expirado");

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
	
	public static StatusEnum get(Integer codigo) {
		if (null != codigo) {
			for (final StatusEnum status : StatusEnum.values()) {
				if(status.getCodigo().equals(codigo)) {
					return status;
				}
			}
		}
		
		return INVALIDO;
	}
}