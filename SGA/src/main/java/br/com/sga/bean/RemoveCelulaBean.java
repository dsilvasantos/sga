package br.com.sga.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.model.Celula;
import br.com.sga.services.ControladorMensagens;

@ViewScoped
@ManagedBean
public class RemoveCelulaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CelulaDAO celulaDAO = new CelulaDAO();
	private Celula celula = new Celula();
	
	@EJB
	ControladorMensagens controladorMensagens;

	public CelulaDAO getCelulaDAO() {
		return celulaDAO;
	}

	public void setCelulaDAO(CelulaDAO celulaDAO) {
		this.celulaDAO = celulaDAO;
	}

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}
	
	@PostConstruct
	public void inica(){
		String idCelula = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("CelulaId");
		
		this.celula = this.celulaDAO.retornaCelula(Integer.parseInt(idCelula));
	}
	
	public String cancela() {
		return "cadastro_celula?faces-redirect=true";
	}
	
	public String exclui(){
		try{
			this.celulaDAO.remove(celula);
			this.controladorMensagens.addMsgInfo("Celula excluída com sucesso !!");
			return "cadastro_celula.xhtml";
		}catch(Exception e){
			this.controladorMensagens.addMsgErro("Erro ao deletar Célula !!");
			e.printStackTrace();
		}
		return null;
	}
}
