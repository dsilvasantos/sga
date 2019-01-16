package br.com.sga.monitoramento.model;

public class Departamento {

	private String nome;
	private String localizacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public Departamento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Departamento(String nome, String localizacao) {
		super();
		this.nome = nome;
		this.localizacao = localizacao;
	}

}
