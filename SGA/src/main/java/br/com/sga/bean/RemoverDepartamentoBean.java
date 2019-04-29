package br.com.sga.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sga.services.ControladorMensagens;
import br.com.sga.controller.DepartamentoController;
import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.model.Departamento;

@ViewScoped
@ManagedBean
public class RemoverDepartamentoBean implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Departamento departamento = new Departamento();
	private DepartamentoController dp = new DepartamentoController();
	private DepartamentoDAO dpDAO = new DepartamentoDAO();
	
	@EJB
	ControladorMensagens controladorMensagens;
	
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public DepartamentoController getDp() {
		return dp;
	}

	public void setDp(DepartamentoController dp) {
		this.dp = dp;
	}

	public DepartamentoDAO getDpDAO() {
		return dpDAO;
	}

	public void setDpDAO(DepartamentoDAO dpDAO) {
		this.dpDAO = dpDAO;
	}

	public ControladorMensagens getControladorMensagens() {
		return controladorMensagens;
	}

	public void setControladorMensagens(ControladorMensagens controladorMensagens) {
		this.controladorMensagens = controladorMensagens;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PostConstruct
	public void iniciar() {
		String idDepartamento = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("DepartamentoId");

		departamento = dp.bucaDepartamento(Integer.parseInt(idDepartamento));
	}
	
	public String exclui() {
		try {
			dpDAO.remove(departamento);
			controladorMensagens.addMsgInfo("Departamento excluído com sucesso !");
			return "cadastro_departamento.xhtml";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String cancela() {
		return "cadastro_departamento?faces-redirect=true";
	}
}
