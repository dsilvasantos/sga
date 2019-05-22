package br.com.sga.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.enumeration.TiposUsuarios;
import br.com.sga.monitoramento.model.Celula;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.services.ControladorMensagens;
import br.com.sga.services.SessionContext;

@ManagedBean
@ViewScoped
public class CadastraAplicacaoBean implements Serializable{ 
	
	private static final long serialVersionUID = 1L;
	
	private Aplicacao aplicacao = new Aplicacao();
	private AplicacaoDAO aplicacaoDAO = new AplicacaoDAO();
	private List<Aplicacao> listaAplicacao = new ArrayList<Aplicacao>();
	private boolean permissao = false;
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




	public List<Aplicacao> getListaAplicacao() {
		return listaAplicacao;
	}




	public void setListaAplicacao(List<Aplicacao> listaAplicacao) {
		this.listaAplicacao = listaAplicacao;
	}




	public String salvar(){
		try{
			this.aplicacaoDAO.persist(aplicacao);
			this.controladorMensagens.addMsgInfo("Aplicação cadastrada com sucesso !");
			aplicacao = new Aplicacao();
			return "cadastro_aplicacao.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

}
