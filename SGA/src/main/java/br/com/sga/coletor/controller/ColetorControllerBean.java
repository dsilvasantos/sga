package br.com.sga.coletor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import br.com.sga.coletor.model.Alertas;
import br.com.sga.coletor.model.TipoAlerta;
import br.com.sga.coletor.model.TipoRecurso;
import br.com.sga.coletor.service.ColetorRoles;
import br.com.sga.coletor.service.ColetorService;
import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.DAO.ErroDAO;
import br.com.sga.monitoramento.DAO.RecursosDAO;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Erro;
import br.com.sga.monitoramento.model.Recursos;

@ManagedBean(name = "coletor")
@ViewScoped
public class ColetorControllerBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean permissao = false;
	public static Logger LOGGER = Logger.getLogger(ColetorControllerBean.class);
	private List<Erro> alertas = new ArrayList<Erro>();
	private List<Erro> coletas = new ArrayList<Erro>();
	private boolean exibirResultados = false;
	private boolean exibirColetas = false;
	String user;
	private boolean coletorStatus = ColetorController.isStart();

	public ColetorControllerBean() {

	}

	public void statusColetor() {
		if (coletorStatus) {
			LOGGER.info("Usuário [" + user + "] solicitou o START do Coletor JBoss.");
			ColetorController.startTimer();
		} else {
			LOGGER.info("Usuário [" + user + "] solicitou o STOP do Coletor JBoss.");
			ColetorController.cancelTimer();
		}
	}

	public void carregarAlertas() {
		exibirResultados = true;
		exibirColetas = false;
		alertas.clear();

		for (Erro valor : ColetorService.alertas.values()) {
			alertas.add(valor);
		}
	}

	public void carregarColeta() {
		exibirColetas = true;
		exibirResultados = false;
		coletas.clear();

		for (Erro valor : ColetorService.coletas.values()) {
			coletas.add(valor);
		}
	}

	public void sendClean(Erro erro) {

		String key;
		LOGGER.info("Usuário [" + user + "] solicitou clean do alrame: ");

		key = recuperarAplicacao(erro.getAplicacao()) + "_" + recuperarRecurso(erro.getRecurso());
		ColetorService.alertas.remove(key);
		erro.setDataSolucao(new Date());
		erro.setStatus("Removido");
		erro.setSolucao("Sem solução");
		ErroDAO erroDAO = new ErroDAO();
		erroDAO.merge(erro);
		carregarColeta();
		if (null != key) {
			carregarAlertas();
			FacesContext.getCurrentInstance().addMessage("Teste Sucesso",
					new FacesMessage("Sucesso.", "A limpeza do alerta foi executada com sucesso."));
		} else {
			LOGGER.error("Tipo de recurso não pode ser nulo ou não foi reconhecido.");
			FacesContext.getCurrentInstance().addMessage("Teste Falha",
					new FacesMessage("Falha.", "Tipo de recurso naõ identificado. "));
		}
	}

	public List<Erro> getAlertas() {
		return alertas;
	}

	public void setPermissao(boolean permissao) {
		this.permissao = permissao;
	}

	public boolean isPermissao() {
		return permissao;
	}

	public boolean isExibirResultados() {
		return exibirResultados;
	}

	public void setExibirResultados(boolean exibirResultados) {
		this.exibirResultados = exibirResultados;
	}

	public List<Erro> getColetas() {
		return coletas;
	}

	public void setColetas(List<Erro> coletas) {
		this.coletas = coletas;
	}

	public boolean isExibirColetas() {
		return exibirColetas;
	}

	public void setExibirColetas(boolean exibirColetas) {
		this.exibirColetas = exibirColetas;
	}

	public boolean isColetorStatus() {
		return coletorStatus;
	}

	public void setColetorStatus(boolean coletorStatus) {
		this.coletorStatus = coletorStatus;
	}

	public String recuperarAplicacao(int id) {

		AplicacaoDAO aplicacaoDAO = new AplicacaoDAO();
		Aplicacao aplicacao = aplicacaoDAO.recupearAplicacaoID(id);
		return aplicacao.getNome();

	}

	public String recuperarRecurso(int id) {

		RecursosDAO recursosDAO = new RecursosDAO();
		Recursos recursos = recursosDAO.recupear(id);
		return recursos.getNome();
	}
}
