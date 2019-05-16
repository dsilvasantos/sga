package br.com.sga.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.model.Celula;
import br.com.sga.services.ControladorMensagens;

@ViewScoped
@ManagedBean
public class CadastroCelulaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Celula celula = new Celula();
	private CelulaDAO celulaDAO = new CelulaDAO();
	private List<Celula> listaCelula = new ArrayList<Celula>();
	
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<Celula> getListaCelula() {
		return listaCelula;
	}
	
	public void setListaCelula(List<Celula> listaCelula) {
		this.listaCelula = listaCelula;
	}
 
	
	private List<Celula> listaCelula() {
		CelulaDAO celula = new CelulaDAO();
		return celula.listaUsuario();
	}
	
	@PostConstruct
	public void iniciar() {
		this.listaCelula = listaCelula();
	}
	
	public String cancela(){
		return "cadastro_celula?faces-redirect=true";
	}
	
	public String salvar(){
		try{
			this.celulaDAO.persist(celula);
			this.controladorMensagens.addMsgInfo("Celula cadastrada com sucesso !");
			celula = new Celula();
			return "cadastro_celula.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
