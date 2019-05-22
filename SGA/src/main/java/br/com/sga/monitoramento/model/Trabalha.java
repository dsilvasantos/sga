package br.com.sga.monitoramento.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "trabalha")
public class Trabalha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "ID_CELULA")
	private Celula celula;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario user;
	
	@Column(name = "data_ini", nullable = false)
	private Date data_ini;
	
	@Column(name = "data_fim", nullable = false)
	private Date data_fim;

	
	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Date getDate_ini() {
		return data_ini;
	}

	public void setDate_ini(Date date_ini) {
		this.data_ini = date_ini;
	}

	public Date getDate_fim() {
		return data_fim;
	}

	public void setDate_fim(Date date_fim) {
		this.data_fim = date_fim;
	}
	
}
