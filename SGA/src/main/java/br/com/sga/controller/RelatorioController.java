package br.com.sga.controller;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "relatorio")
@ViewScoped
public class RelatorioController {

	private Date dataInicial;
	
	private Date dataFinal;

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	


	
}
