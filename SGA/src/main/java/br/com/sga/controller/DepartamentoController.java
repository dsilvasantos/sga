package br.com.sga.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.model.Departamento;


@ManagedBean(name = "departamento")
@SessionScoped
public class DepartamentoController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Departamento departamento = new Departamento();
	private List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
	
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	@PostConstruct
	public void iniciar() {
		listaDepartamentos = listaDepartamento();
	}
	
	public String cancela() {
		return "cadastro_departamento?faces-redirect=true";
	}

	public List<String> getDepartamentos() {
		DepartamentoDAO departamentoDAO = new DepartamentoDAO();
		return departamentoDAO.recuperar();
	}
	
	public void salvar(Departamento departamento){
		FacesMessage face = null;
		if(departamento != null && departamento.getNome().length() > 1 && departamento.getLocalizacao().length() >1 ){
		DepartamentoDAO dp = new DepartamentoDAO();
		dp.persist(departamento);	
		face = new FacesMessage("Departamento cadastro com Sucesso !");
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, face);
		}
			new FacesMessage("Erro ao cadastrar Departamento !");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, face);
	
	}		
	
	public String remove(Departamento dp){
		this.departamento = listaDepartamentos.get(listaDepartamentos.indexOf(dp));
		return "removeDepartamento.xhtml";
	}
	
	public String alterar(Departamento dp){
		this.departamento = listaDepartamentos.get(listaDepartamentos.indexOf(dp));
		return "alteraDepartamento.xhtml";
	}
	
	private List<Departamento> listaDepartamento(){
		DepartamentoDAO dp = new DepartamentoDAO();
		return dp.listaDepartamentos();
	}

	public List<Departamento> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}
	
	public String salvaAlteracao() {
		try{
			DepartamentoDAO daoDepartamento = new DepartamentoDAO();
			daoDepartamento.merge(departamento);
			FacesMessage face = new FacesMessage("Departamento alterado com sucesso !");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, face);
			listaDepartamentos = daoDepartamento.listaDepartamentos();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String excluiDepartamento(){
		try{
			DepartamentoDAO dp = new DepartamentoDAO();
			dp.remove(departamento);
			FacesMessage face = new FacesMessage("Departamento excluído com sucesso !");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, face);
			listaDepartamentos.remove(departamento);
			listaDepartamentos = dp.listaDepartamentos();
			return "cadastro_departamento.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
