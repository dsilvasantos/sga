package br.com.sga.monitoramento.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Ambiente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "ambiente",nullable=false)
	private String ambiente;
	
	@Column(name = "host",nullable=false)
	private String host;
	
	@Column(name = "usuario",nullable=false)
	private String usuario;
	
	@Column(name = "senha",nullable=false)
	private String senha;
	
	@Column(name = "porta",nullable=false)
	private int porta;
	
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getAmbiente() {
		return ambiente;
	}
	
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public Ambiente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ambiente(String ambiente, String host, String usuario, String senha, int porta) {
		super();
		this.ambiente = ambiente;
		this.host = host;
		this.usuario = usuario;
		this.senha = senha;
		this.porta = porta;
	}
}
