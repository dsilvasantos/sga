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
	
	@Column(name = "data_abertura",nullable=false,columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAbertura;
	
	
	@Column(name = "data_solucao",nullable=true,columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataSolucao;
	
	@Column(name = "status",nullable=false)
	private String status;
	
	@Column(name = "solucao",nullable=false)
	private String solucao;
	
	@Column(name = "id_recursos",nullable=false)
	private int recurso;
	
	@Column(name = "id_usuario",nullable=true)
	private int usuario;

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

	public int getRecurso() {
		return recurso;
	}

	public void setRecurso(int recurso) {
		this.recurso = recurso;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public Erro(int id, String prioridade, String descricao, int aplicacao, Date dataAbertura, Date dataSolucao,
			String status,int recurso) {
		super();
		this.id = id;
		this.prioridade = prioridade;
		this.descricao = descricao;
		this.aplicacao = aplicacao;
		this.dataAbertura = dataAbertura;
		this.dataSolucao = dataSolucao;
		this.status = status;
		this.recurso = recurso;
	}
	
	public Erro(String prioridade, String descricao, int aplicacao, Date dataAbertura,String status,int recurso) {
		super();
		this.prioridade = prioridade;
		this.descricao = descricao;
		this.aplicacao = aplicacao;
		this.dataAbertura = dataAbertura;
		this.status = status;
		this.recurso = recurso;
	}

	public Erro() {
		super();
	}

	
}
