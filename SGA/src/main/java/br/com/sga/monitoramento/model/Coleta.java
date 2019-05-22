package br.com.sga.monitoramento.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="coleta")
public class Coleta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "aplicacao",nullable=false)
	private int aplicacao;
	
	@Column(name = "recurso",nullable=false)
	private int recurso;
	
	@Column(name = "valor",nullable=false)
	private int valor;
	

	@Column(name = "data",nullable=false, columnDefinition="DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

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

	public int getRecurso() {
		return recurso;
	}

	public void setRecurso(int recurso) {
		this.recurso = recurso;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Coleta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coleta(int aplicacao, int recurso, int valor) {
		super();
		this.aplicacao = aplicacao;
		this.recurso = recurso;
		this.valor = valor;
		this.data = new Date();
	}
	
	
}
