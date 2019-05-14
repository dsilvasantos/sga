package br.com.sga.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sga.controller.DepartamentoController;
import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.model.Departamento;
import br.com.sga.services.ControladorMensagens;

@ViewScoped
@ManagedBean
public class EditarDepartamentoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Departamento departamento = new Departamento();
	private DepartamentoDAO dpDAO = new DepartamentoDAO();
	private DepartamentoController dpCont = new DepartamentoController();
	
	@EJB
	ControladorMensagens controladorMensagens;
	
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public DepartamentoDAO getDpDAO() {
		return dpDAO;
	}

	public void setDpDAO(DepartamentoDAO dpDAO) {
		this.dpDAO = dpDAO;
	}

	public DepartamentoController getDpCont() {
		return dpCont;
	}

	public void setDpCont(DepartamentoController dpCont) {
		this.dpCont = dpCont;
	}

	@PostConstruct
	public void inicia(){
		String idDepartamento = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("DepartamentoId");
		
		this.departamento = dpCont.retornaDepartamento(Integer.parseInt(idDepartamento));
	}
	
	public String cancela() {
		return "cadastro_departamento?faces-redirect=true";
	}
	
	public String salvaAlteracao() {
		try{
			DepartamentoDAO daoDepartamento = new DepartamentoDAO();
			daoDepartamento.merge(departamento);
			controladorMensagens.addMsgInfo("Departamento Alterado com sucesso");
			return "cadastro_departamento?faces-redirect=true";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
