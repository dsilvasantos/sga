package br.com.sga.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.model.Departamento;


@ManagedBean(name = "departamento")
@ViewScoped
public class DepartamentoController {
	
	private Departamento departamento = new Departamento();
	
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<String> getDepartamentos() {
		DepartamentoDAO departamentoDAO = new DepartamentoDAO();
		return departamentoDAO.recuperar();
	}
	
	public void salvar(Departamento departamento){
		DepartamentoDAO dp = new DepartamentoDAO();
		dp.persist(departamento);	
	}
	
	public void remove(Departamento departamento){
			DepartamentoDAO dp = new DepartamentoDAO();
			dp.remove(departamento);	
	}
	
	public  void alterar(Departamento departamento){
		DepartamentoDAO dp = new DepartamentoDAO();
		dp.merge(departamento);
	}
}
