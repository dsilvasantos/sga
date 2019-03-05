package br.com.sga.monitoramento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="recursos_aplicacao")
public class RecursosAplicacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "ID_APLICACAO",nullable=false)
	private int aplicacao;
	
	@Column(name = "ID_RECURSOS",nullable=false)
	private int recursos;
	
	@Column(name = "quantidade_minima",nullable=false)
	private int quantidadeMinima;
	
	@Column(name = "quantidade_maxima",nullable=false)
	private int quantidadeMaxima;
	
	@Column(name = "quantidade_critica",nullable=false)
	private int quantiodadeCritica;
	
	@Column(name = "valor",nullable=false)
	private String valor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(int aplicacao) {
		this.aplicacao = aplicacao;
	}

	public int getRecursos() {
		return recursos;
	}

	public void setRecursos(int recursos) {
		this.recursos = recursos;
	}

	public int getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(int quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}

	public int getQuantidadeMaxima() {
		return quantidadeMaxima;
	}

	public void setQuantidadeMaxima(int quantidadeMaxima) {
		this.quantidadeMaxima = quantidadeMaxima;
	}

	public int getQuantiodadeCritica() {
		return quantiodadeCritica;
	}

	public void setQuantiodadeCritica(int quantiodadeCritica) {
		this.quantiodadeCritica = quantiodadeCritica;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public RecursosAplicacao(int id, int aplicacao, int recursos, int quantidadeMinima, int quantidadeMaxima,
			int quantiodadeCritica, String valor) {
		super();
		this.id = id;
		this.aplicacao = aplicacao;
		this.recursos = recursos;
		this.quantidadeMinima = quantidadeMinima;
		this.quantidadeMaxima = quantidadeMaxima;
		this.quantiodadeCritica = quantiodadeCritica;
		this.valor = valor;
	}

	public RecursosAplicacao() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
