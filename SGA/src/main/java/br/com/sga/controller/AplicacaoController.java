package br.com.sga.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.model.Aplicacao;


@ManagedBean(name = "aplicacaoController")
@ViewScoped
public class AplicacaoController {
	
	private Aplicacao aplicacao = new Aplicacao();
	
	public Aplicacao getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(Aplicacao aplicacao) {
		this.aplicacao = aplicacao;
	}

	public void salvar(Aplicacao aplicacao){
		FacesMessage face = null;
		if(aplicacao != null){
		AplicacaoDAO ap = new AplicacaoDAO();
		ap.persist(aplicacao);	
		face = new FacesMessage("Aplicacao cadastrada com Sucesso !");
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, face);
		}

	}		
	
	public void remove(Aplicacao aplicacao){
			AplicacaoDAO ap = new AplicacaoDAO();
			ap.remove(aplicacao);	
	}
	
	public  void alterar(Aplicacao aplicacao){
		AplicacaoDAO ap = new AplicacaoDAO();
		ap.merge(aplicacao);
	}
}
