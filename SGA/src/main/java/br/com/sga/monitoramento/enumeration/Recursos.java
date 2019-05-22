package br.com.sga.monitoramento.enumeration;

public enum Recursos {

	STATUS(100);
	
	
	
	private final int valor;

	public int getValor() {
		return valor;
	}

	private Recursos(int level) {
		this.valor = level;
	}
}
