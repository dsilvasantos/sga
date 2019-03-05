package br.com.sga.coletor.model;

import java.sql.Date;

public class BDC {
	
	private java.sql.Date data;
	
	private String host;
	private String instanciaJVM;
	private String recurso;
	private String valor;
	
	public BDC() {
		// TODO Auto-generated constructor stub
	}
	
	public BDC(Date data, String host, String instanciaJVM, String recurso,
			String valor) {
		super();
		this.data = data;
		this.host = host;
		this.instanciaJVM = instanciaJVM;
		this.recurso = recurso;
		this.valor = valor;
	}

	public java.sql.Date getData() {
		return data;
	}

	public void setData(java.sql.Date data) {
		this.data = data;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getInstanciaJVM() {
		return instanciaJVM;
	}

	public void setInstanciaJVM(String instanciaJVM) {
		this.instanciaJVM = instanciaJVM;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	
	
	

}
