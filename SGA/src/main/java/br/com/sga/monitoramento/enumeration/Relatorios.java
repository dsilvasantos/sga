package br.com.sga.monitoramento.enumeration;

public enum Relatorios {
	
	erroAplicacao("Coletas Menores"),
	fullGC("Full GC"),
	other("N/D");
	
	private final String valor;

	public String getValor() {
		return valor;
	}

	private Relatorios(String valor) {
		this.valor = valor;
	}

}
