package br.com.sga.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.enumeration.TiposUsuarios;
import br.com.sga.monitoramento.model.Departamento;
import br.com.sga.services.ControladorMensagens;
import br.com.sga.services.SessionContext;

@ManagedBean(name = "cadastroDepartamentoBean")
@ViewScoped
public class CadastraDepartamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Departamento departamento = new Departamento();
	private List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
	private DepartamentoDAO dpDAO = new DepartamentoDAO();
	private boolean permissao = false;
	@EJB
	ControladorMensagens controladorMensagens;

	public String alterar() {
		return "alteraDepartamento.xhtml";
	}

	public String remove() {
		return "removeDepartamento.xhtml";
	}
	
	public String cancela() {
		return "cadastro_departamento?faces-redirect=true";
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<Departamento> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public DepartamentoDAO getDpDAO() {
		return dpDAO;
	}

	public void setDpDAO(DepartamentoDAO dpDAO) {
		this.dpDAO = dpDAO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PostConstruct
	public void iniciar() {
		listaDepartamentos = listaDepartamento();
	}

	private List<Departamento> listaDepartamento() {
		DepartamentoDAO dp = new DepartamentoDAO();
		return dp.listaDepartamentos();
	}

	public String salvar() {
		try {
			dpDAO.persist(departamento);
			controladorMensagens.addMsgInfo("Derpartamento incluido com sucesso");
			departamento = new Departamento();
			return "cadastro_departamento.xhtml";
		} catch (Exception e) {
			controladorMensagens.addMsgInfo("erro.inclusao.departamento");
		}
		return null;
	}
	
	
	public boolean isPermissao() {
		if(TiposUsuarios.analistaSuporte.getValor() == SessionContext.getInstance().getUsuarioLogado().getTipo()) {
			return true;
		}else {
			return false;
		}

	}
	 
	public void setPermissao(boolean permissao) {
		this.permissao = permissao;
	}

}
