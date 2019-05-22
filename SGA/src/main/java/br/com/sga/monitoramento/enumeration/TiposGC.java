package br.com.sga.monitoramento.enumeration;

public enum TiposGC {
	
	minGC("Coletas Menores"),
	fullGC("Full GC"),
	other("N/D");
	
	private final String valor;

	public String getValor() {
		return valor;
	}

	private TiposGC(String valor) {
		this.valor = valor;
	}

}
