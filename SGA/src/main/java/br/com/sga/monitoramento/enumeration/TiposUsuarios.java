package br.com.sga.monitoramento.enumeration;

public enum TiposUsuarios {
	
	analistaProducao(1),
	analistaSuporte(2),
	desenvolvedor(3);
	
	private final int valor;

	public int getValor() {
		return valor;
	}

	private TiposUsuarios(int valor) {
		this.valor = valor;
	}

}
