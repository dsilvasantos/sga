package br.com.sga.coletor.model;

import java.util.List;

import br.com.sga.monitoramento.model.Aplicacao;
public class Ambiente {
	
	private String nomeAmbiente;
	private List<Aplicacao> aplicacoes;
	
	
	public Ambiente() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Ambiente(String nomeAmbiente) {
		super();
		this.nomeAmbiente = nomeAmbiente;
	}



	public String getNomeAmbiente() {
		return nomeAmbiente;
	}
	
	public void setNomeAmbiente(String nomeAmbiente) {
		this.nomeAmbiente = nomeAmbiente;
	}

	public List<Aplicacao> getAplicacaos() {
		return aplicacoes;
	}

	public void setAplicacaos(List<Aplicacao> aplicacoes) {
		this.aplicacoes = aplicacoes;
	}
}
