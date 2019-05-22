package br.com.sga.coletor.model;

public enum TipoRecurso {
	METASPACE("metaspace"),
	HEAP("heap"),
	THREAD("thread"),
	DATASOURCE("datasource"),
	STATUS("status"),
	VERSAOJAVA("versao-java"),
	VERSAOJBOSS("versao-jboss");
	
	
	private final String valor;

	public String getValor() {
		return valor;
	}

	private TipoRecurso(String valor) {
		this.valor = valor;
	}


}
