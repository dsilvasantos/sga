package br.com.sga.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.DAO.DepartamentoDAO;

@ManagedBean(name = "departamento")
@ViewScoped
public class DepartamentoController {


	public List<String> getDepartamentos() {
		return DepartamentoDAO.getInstance().recuperar();
	}
}
