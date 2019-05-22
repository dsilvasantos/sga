package br.com.sga.coletor.model;

public class Property {
	
	private String chave;
	private String valor;
	
	
	public Property() {
		// TODO Auto-generated constructor stub
	}


	public Property(String chave, String valor) {
		super();
		this.chave = chave;
		this.valor = valor;
	}


	public String getChave() {
		return chave;
	}


	public void setChave(String chave) {
		this.chave = chave;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}
	
	

}
