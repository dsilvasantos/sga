package br.com.sga.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jboss.logging.Logger;

import br.com.sga.coletor.service.ColetorService;
import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.enumeration.TiposUsuarios;
import br.com.sga.monitoramento.model.Celula;
import br.com.sga.monitoramento.model.Departamento;
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
	private static final Logger LOGGER = Logger.getLogger(CadastraAplicacaoBean.class);
	@EJB
	ControladorMensagens controladorMensagens;
	
	
	public Aplicacao getAplicacao() {
		return aplicacao;
	}




	public void setAplicacao(Aplicacao aplicacao) {
		this.aplicacao = aplicacao;
	}

	
	public List<Aplicacao> getListaAplicacao() {
		return listaAplicacao;
	}


	public void setListaAplicacao(List<Aplicacao> listaAplicacao) {
		this.listaAplicacao = listaAplicacao;
	}


	public AplicacaoDAO getAplicacaoDAO() {
		return aplicacaoDAO;
	}

	public void setAplicacaoDAO(AplicacaoDAO aplicacaoDAO) {
		this.aplicacaoDAO = aplicacaoDAO;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PostConstruct
	public void iniciar() {
		listaAplicacao = listaAplicacao();
	}

	private List<Aplicacao> listaAplicacao() {
		AplicacaoDAO ad = new AplicacaoDAO();
		List<Aplicacao> list = new ArrayList<Aplicacao>();
		CelulaDAO celulaDAO =  new CelulaDAO();
		Aplicacao apl = null;
		//LOGGER.info(message);
		for(Aplicacao a : ad.listaAplicacao()) 
		{
			apl = new Aplicacao();
			apl.setNome(a.getNome());
			apl.setId(a.getId());
			apl.setCelula(a.getCelula());
			apl.setStatus(a.getStatus());
			if(a.getStatus() == 1)
			{
				apl.setStatusTexto("Ativa");
			}
			else 
			{
				apl.setStatusTexto("Desativada");
			}
			list.add(apl);
		}
		return list;
	}


	public String salvar(){
		try{
			aplicacao.setStatus(1);
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
