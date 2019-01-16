package br.com.sga.monitoramento.model;

public class Celula {

	private String nome;
	private String descricao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Celula() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Celula(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}

}
