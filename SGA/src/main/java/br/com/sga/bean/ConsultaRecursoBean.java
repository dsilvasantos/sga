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
import br.com.sga.monitoramento.DAO.RecursosDAO;
import br.com.sga.monitoramento.enumeration.TiposUsuarios;
import br.com.sga.monitoramento.model.Celula;
import br.com.sga.monitoramento.model.Departamento;
import br.com.sga.monitoramento.model.Recursos;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.services.ControladorMensagens;
import br.com.sga.services.SessionContext;

@ManagedBean
@ViewScoped
public class ConsultaRecursoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Recursos recursos = new Recursos();
	private RecursosDAO recursosDAO = new RecursosDAO();
	private List<Recursos> listaRecursos = new ArrayList<Recursos>();
	private boolean permissao = false;
	private static final Logger LOGGER = Logger.getLogger(CadastraAplicacaoBean.class);
	@EJB
	ControladorMensagens controladorMensagens;
	public Recursos getRecursos() {
		return recursos;
	}
	public void setRecursos(Recursos recursos) {
		this.recursos = recursos;
	}
	public RecursosDAO getRecursosDAO() {
		return recursosDAO;
	}
	public void setRecursosDAO(RecursosDAO recursosDAO) {
		this.recursosDAO = recursosDAO;
	}
	public List<Recursos> getListaRecursos() {
		return listaRecursos;
	}
	public void setListaRecursos(List<Recursos> listaRecursos) {
		this.listaRecursos = listaRecursos;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PostConstruct
	public void iniciar() {
		
		String idAplicacao = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("AplicacaoId");
		
		this.listaRecursos = this.recursosDAO.listaRecursoID(Integer.parseInt(idAplicacao));
		
		//listaRecursos = listaRecursos();
	}

	private List<Recursos> listaRecursos() {
		RecursosDAO ad = new RecursosDAO();
		return ad.listaRecursos();
	}

}