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
public class RemoveAplicacaoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AplicacaoDAO aplicacaoDAO = new AplicacaoDAO();
	private Aplicacao aplicacao = new Aplicacao();
	
	@EJB
	ControladorMensagens controladorMensagens;

	public AplicacaoDAO getAplicacaoDAO() {
		return aplicacaoDAO;
	}

	public void setAplicacaoDAO(AplicacaoDAO aplicacaoDAO) {
		this.aplicacaoDAO = aplicacaoDAO;
	}

	public Aplicacao getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(Aplicacao aplicacao) {
		this.aplicacao = aplicacao;
	}
	
	
	@PostConstruct
	public void inica(){
		String idAplicacao = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("AplicacaoId");
		
		this.aplicacao = this.aplicacaoDAO.recupearAplicacaoID(Integer.parseInt(idAplicacao));
	}
	
	public String cancela() {
		return "cadastro_aplicacao?faces-redirect=true";
	}
	
	public String exclui(){
		try{
			this.aplicacaoDAO.remove(aplicacao);
			this.controladorMensagens.addMsgInfo("Aplicação excluída com sucesso !!");
			return "cadastro_aplicacao.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
