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
public class EditarCelulaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Celula celula = new Celula();
	private CelulaDAO celulaDAO = new CelulaDAO();
	
	@EJB
	ControladorMensagens controladorMensagens;
	
	public Celula getCelula() {
		return celula;
	}
	public void setCelula(Celula celula) {
		this.celula = celula;
	}
	public CelulaDAO getCelulaDAO() {
		return celulaDAO;
	}
	public void setCelulaDAO(CelulaDAO celulaDAO) {
		this.celulaDAO = celulaDAO;
	}
	
	@PostConstruct
	public void inicia(){
		String idCelula = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("CelulaId");
		
		this.celula = this.celulaDAO.retornaCelula(Integer.parseInt(idCelula));
	}
	
	public String cancela() {
		return "cadastro_celula?faces-redirect=true";
	}
	
	public String salvaAlteracao(){
		try{
		this.celulaDAO.merge(celula);
		this.controladorMensagens.addMsgInfo("Celula alterada com sucesso !");
		return "cadastro_celula.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
