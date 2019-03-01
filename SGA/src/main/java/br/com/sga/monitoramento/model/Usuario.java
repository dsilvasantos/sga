package br.com.sga.monitoramento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "login",nullable=false)
	private String login;
	
	@Column(name = "senha",nullable=false)
	private String senha;
	
	@Column(name = "nome",nullable=false)
	private String nome;
	
	@Column(name = "email",nullable=false)
	private String email;
			
	@Column(name = "status",nullable=false)
	private int status;
	
	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getSenha() {
		return senha;
	}



	public void setSenha(String senha) {
		this.senha = senha;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Usuario(int id, String login, String senha, String nome, String email, int status) {
		super();
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.status = status;
	}
	

}
