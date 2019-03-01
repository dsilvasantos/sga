package br.com.sga.monitoramento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="aplicacao")
public class Aplicacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nome",nullable=false)
	private String nome;
	
	@Column(name = "status",nullable=false)
	private Integer status;
	
	@Transient
	private Server server;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Aplicacao() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Aplicacao(int id, String nome, Integer status, Server server) {
		super();
		this.id = id;
		this.nome = nome;
		this.status = status;
		this.server = server;
	}
	
	public Aplicacao(int id, String nome, Integer status) {
		super();
		this.id = id;
		this.nome = nome;
		this.status = status;
	}
}
