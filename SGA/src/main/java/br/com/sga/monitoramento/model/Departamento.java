package br.com.sga.monitoramento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="departamento")
public class Departamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nome",nullable=false)
	private String nome;
	
	@Column(name = "localizacao",nullable=true)
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Departamento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Departamento(int id, String nome, String localizacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.localizacao = localizacao;
	}

}
