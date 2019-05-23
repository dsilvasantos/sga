package br.com.sga.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.model.Celula;
import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.services.ControladorMensagens;

@ViewScoped
@ManagedBean

public class EditarAplicacaoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Aplicacao aplicacao = new Aplicacao();
	private AplicacaoDAO aplicacaoDAO = new AplicacaoDAO();
	
	@EJB
	ControladorMensagens controladorMensagens;
	
	
	public Aplicacao getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(Aplicacao aplicacao) {
		this.aplicacao = aplicacao;
	}

	public AplicacaoDAO getAplicacaoDAO() {
		return aplicacaoDAO;
	}

	public void setAplicacaoDAO(AplicacaoDAO aplicacaoDAO) {
		this.aplicacaoDAO = aplicacaoDAO;
	}

	@PostConstruct
	public void inicia(){
		String idAplicacao = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("AplicacaoId");
		
		this.aplicacao = this.aplicacaoDAO.recupearAplicacaoID(Integer.parseInt(idAplicacao));
	}
	
	public String cancela() {
		return "cadastro_aplicacao?faces-redirect=true";
	}
	
	public String salvaAlteracao(){
		try{
		this.aplicacaoDAO.merge(aplicacao);
		this.controladorMensagens.addMsgInfo("Aplicação alterada com sucesso !");
		return "cadastro_aplicacao.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
