package br.com.sga.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.DAO.RecursosAplicacaoDAO;
import br.com.sga.monitoramento.model.Celula;
import br.com.sga.monitoramento.model.RecursosAplicacao;
import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.services.ControladorMensagens;

@ViewScoped
@ManagedBean
public class EditarRecursoAplicacaoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private RecursosAplicacao recursoAplicacao = new RecursosAplicacao();
	private RecursosAplicacaoDAO recursoAplicacaoDAO = new RecursosAplicacaoDAO();
	private List<RecursosAplicacao> listaRecursoAplicacao = new ArrayList<RecursosAplicacao>();
	private boolean permissao = false;
	private static final Logger LOGGER = Logger.getLogger(CadastraAplicacaoBean.class);
	@EJB
	ControladorMensagens controladorMensagens;
	
	
	
	public RecursosAplicacao getRecursoAplicacao() {
		return recursoAplicacao;
	}



	public void setRecursoAplicacao(RecursosAplicacao recursoAplicacao) {
		this.recursoAplicacao = recursoAplicacao;
	}



	public RecursosAplicacaoDAO getRecursoAplicacaoDAO() {
		return recursoAplicacaoDAO;
	}



	public void setRecursoAplicacaoDAO(RecursosAplicacaoDAO recursoAplicacaoDAO) {
		this.recursoAplicacaoDAO = recursoAplicacaoDAO;
	}



	public List<RecursosAplicacao> getListaRecursoAplicacao() {
		return listaRecursoAplicacao;
	}



	public void setListaRecursoAplicacao(List<RecursosAplicacao> listaRecursoAplicacao) {
		this.listaRecursoAplicacao = listaRecursoAplicacao;
	}



	public String salvaAlteracao(){
		try{
		this.recursoAplicacaoDAO.merge(recursoAplicacao);
		this.controladorMensagens.addMsgInfo("Recurso da aplicação alterado com sucesso !");
		return "consultaRecursoAplicacao.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
