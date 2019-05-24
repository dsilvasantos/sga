package br.com.sga.monitoramento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="recursos_aplicacao")
public class RecursosAplicacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	@Column(name = "quantidade_minima",nullable=false)
	private int quantidadeMinima;
	
	@Column(name = "quantidade_maxima",nullable=false)
	private int quantidadeMaxima;
	
	@Column(name = "quantidade_critica",nullable=false)
	private int quantiodadeCritica;
	
	@Column(name = "valor",nullable=false)
	private String valor;

	@ManyToOne
	@JoinColumn(name = "ID_RECURSOS")
	private Recursos recursos;
	
	@ManyToOne
	@JoinColumn(name = "ID_APLICACAO")
	private Aplicacao aplicacao;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public Aplicacao getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(Aplicacao aplicacao) {
		this.aplicacao = aplicacao;
	}
	

	public Recursos getRecursos() {
		return recursos;
	}

	public void setRecursos(Recursos recursos) {
		this.recursos = recursos;
	}
	
	public RecursosAplicacao(int id, int quantidadeMinima, int quantidadeMaxima,
			int quantiodadeCritica, String valor) {
		super();
		this.id = id;
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
