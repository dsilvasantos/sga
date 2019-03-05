package br.com.sga.coletor.model;

public enum TipoAlerta {
	OK("ok"),
	WARN("warn"),
	CRITICAL("critical");
	
	private final String valor;

	public String getValor() {
		return valor;
	}

	private TipoAlerta(String valor) {
		this.valor = valor;
	}


}
