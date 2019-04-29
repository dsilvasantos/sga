package br.com.sga.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.model.Departamento;


@ManagedBean(name = "departamento")
@RequestScoped
public class DepartamentoController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Departamento departamento = new Departamento();
	private List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
	private DepartamentoDAO dpDAO = new DepartamentoDAO();
	
	public DepartamentoDAO getDpDAO() {
		return dpDAO;
	}

	public void setDpDAO(DepartamentoDAO dpDAO) {
		this.dpDAO = dpDAO;
	}

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
	

	public List<String> getDepartamentos() {
		DepartamentoDAO departamentoDAO = new DepartamentoDAO();
		return departamentoDAO.recuperar();
	}

	public String remove(Departamento dp){
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
	
	public Departamento retornaDepartamento(Integer idDepartamento) {
		 Departamento departamento = dpDAO.retornaDepartamento(idDepartamento);
		 return departamento;
	}
	
	public Departamento bucaDepartamento(Integer idDepartamento){
		Departamento departamento = dpDAO.buscaDepartamento(idDepartamento);
		return departamento;
	}
	
}
