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

import br.com.sga.coletor.service.ColetorService;
import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.DAO.RecursosAplicacaoDAO;
import br.com.sga.monitoramento.enumeration.TiposUsuarios;
import br.com.sga.monitoramento.model.Celula;
import br.com.sga.monitoramento.model.Departamento;
import br.com.sga.monitoramento.model.RecursosAplicacao;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.services.ControladorMensagens;
import br.com.sga.services.SessionContext;

@ManagedBean
@ViewScoped
public class ConsultaRecursoAplicacaoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private RecursosAplicacao recursoAplicacao = new RecursosAplicacao();
	private RecursosAplicacaoDAO recursoAplicacaoDAO = new RecursosAplicacaoDAO();
	private List<RecursosAplicacao> listaRecursoAplicacao = new ArrayList<RecursosAplicacao>();
	String idRecursoAplicacao, idRecursoAplicacaoFinal;
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
	
	@PostConstruct
	public void iniciar() {
		
		idRecursoAplicacao = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
		
				.get("RecursoAplicacaoId");
	
		idRecursoAplicacaoFinal = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				
				.get("RecursoAplicacaoIdFinal");
		
		
		if(idRecursoAplicacao == null) 
		{
			
		}
		else 
		{
			listaRecursoAplicacao = listaRecursoAplicacao();
		}
		
		if(idRecursoAplicacaoFinal == null) 
		{
			
		}
		else 
		{
			recursoAplicacao = this.recursoAplicacaoDAO.recuperaRecursoAplicacao(Integer.parseInt(idRecursoAplicacaoFinal));
		}
		
		
		}
	
	private List<RecursosAplicacao> listaRecursoAplicacao() 
	{ 
		return this.recursoAplicacaoDAO.listaRecursosAplicacao(Integer.parseInt(idRecursoAplicacao));
	}
	
	public String salvaAlteracao(){
		try{
		this.recursoAplicacaoDAO.merge(recursoAplicacao);
		this.controladorMensagens.addMsgInfo("Recurso da aplica��o alterado com sucesso !");
		return "cadastro_aplicacao.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String exclui(){
		try{
		this.recursoAplicacaoDAO.remove(recursoAplicacao);
		this.controladorMensagens.addMsgInfo("Recurso da aplica��o exclu�do com sucesso !");
		return "cadastro_aplicacao.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
