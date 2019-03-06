package br.com.sga.monitoramento.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="erros")
public class Erro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "prioridade",nullable=false)
	private String prioridade;
	
	@Column(name = "descricao",nullable=false)
	private String descricao;
	
	@Column(name = "id_aplicacao",nullable=false)
	private int aplicacao;
	
	@Column(name = "data_abertura",nullable=false)
	private Date dataAbertura;
	
	@Column(name = "data_solucao",nullable=true)
	private Date dataSolucao;
	
	@Column(name = "status",nullable=false)
	private String status;
	
	@Column(name = "solucao",nullable=false)
	private String solucao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(int aplicacao) {
		this.aplicacao = aplicacao;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataSolucao() {
		return dataSolucao;
	}

	public void setDataSolucao(Date dataSolucao) {
		this.dataSolucao = dataSolucao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	public Erro(int id, String prioridade, String descricao, int aplicacao, Date dataAbertura, Date dataSolucao,
			String status) {
		super();
		this.id = id;
		this.prioridade = prioridade;
		this.descricao = descricao;
		this.aplicacao = aplicacao;
		this.dataAbertura = dataAbertura;
		this.dataSolucao = dataSolucao;
		this.status = status;
	}
	
	public Erro(String prioridade, String descricao, int aplicacao, Date dataAbertura,String status) {
		super();
		this.prioridade = prioridade;
		this.descricao = descricao;
		this.aplicacao = aplicacao;
		this.dataAbertura = dataAbertura;
		this.status = status;
	}

	public Erro() {
		super();
	}

	
}
