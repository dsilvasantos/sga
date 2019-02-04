package br.com.sga.monitoramento.enumeration;

public enum StatusServer {
	
	running("running"),
	restartrequired("restart-required");
	
	private final String valor;

	public String getValor() {
		return valor;
	}

	private StatusServer(String valor) {
		this.valor = valor;
	}

}
