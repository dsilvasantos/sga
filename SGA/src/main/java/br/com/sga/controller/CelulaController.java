package br.com.sga.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.enumeration.TiposUsuarios;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.services.AmbienteServices;
import br.com.sga.services.AplicacaoCLI;
import br.com.sga.services.SessionContext;

@ManagedBean(name = "celula")
@ViewScoped
public class CelulaController {
	
	private boolean permissao = false;

	private CelulaDAO celulaDao = new CelulaDAO();
	private AplicacaoDAO aplicacaoDao = new AplicacaoDAO();
	
	private AplicacaoCLI aplicacaoCLI = new AplicacaoCLI();
	
	private String departamento;
	private String celula;


	private List<Aplicacao> aplicacoes = new ArrayList<>();

	public List<String> getCelulas(){
		if(departamento != null) {
		return celulaDao.recupear(departamento);
		}
		return new ArrayList<>();
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	public void carregarAplicacoes() {
		AmbienteServices ambiente = new AmbienteServices();
		ambiente.selecionarAmbiente(1);
		
		aplicacoes = aplicacaoDao.recupear(celula);
		for(Aplicacao aplicacao : aplicacoes) {
			aplicacaoCLI.recuperarServer(aplicacao);
		}
	}

	public List<Aplicacao> getAplicacoes() {
		return aplicacoes;
	}
	
	public void setCelula(String celula) {
		this.celula = celula;
	}

	public String getCelula() {
		return celula;
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
