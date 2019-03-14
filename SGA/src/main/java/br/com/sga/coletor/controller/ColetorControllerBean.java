package br.com.sga.coletor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jboss.logging.Logger;

import br.com.sga.coletor.model.Alertas;
import br.com.sga.coletor.service.ColetorService;
import br.com.sga.monitoramento.model.Erro;

@ManagedBean(name = "coletor")
@ViewScoped
public class ColetorControllerBean implements Serializable {

	/**
	 * 
	 */
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

	public void sendClean(Alertas alerta) {
		/*
		String key;
		TipoRecurso recurso;
		LOGGER.info("Usuário [" + user + "] solicitou clean do alrame: " + alerta.getKey());

		switch (alerta.getRecurso().toLowerCase()) {
		case "heap":
			recurso = TipoRecurso.HEAP;
			key = alerta.getKey().replace("_" + TipoRecurso.HEAP.getValor().toLowerCase(), "");
			break;
		case "status":
			recurso = TipoRecurso.STATUS;
			key = alerta.getKey().replace("_" + TipoRecurso.STATUS.getValor().toLowerCase(), "");
			break;
		case "datasource":
			recurso = TipoRecurso.DATASOURCE;
			key = alerta.getKey().replace("_" + TipoRecurso.DATASOURCE.getValor().toLowerCase(), "");
			break;
		case "thread":
			recurso = TipoRecurso.THREAD;
			key = alerta.getKey().replace("_" + TipoRecurso.THREAD.getValor().toLowerCase(), "");
			break;
		case "metaspace":
			recurso = TipoRecurso.METASPACE;
			key = alerta.getKey().replace("_" + TipoRecurso.METASPACE.getValor().toLowerCase(), "");
			break;
		case "versao-java":
			recurso = TipoRecurso.VERSAOJAVA;
			key = alerta.getKey().replace("_" + TipoRecurso.VERSAOJAVA.getValor().toLowerCase(), "");
			break;
		case "versao-jboss":
			recurso = TipoRecurso.VERSAOJBOSS;
			key = alerta.getKey().replace("_" + TipoRecurso.VERSAOJBOSS.getValor().toLowerCase(), "");
			break;
		default:
			recurso = null;
			key = null;
			break;
		}

		TipoAlerta tipoAlerta = TipoAlerta.OK;
		String valor = "";
		ColetorRoles roles = new ColetorRoles();
		if (null != recurso) {
			NagiosCheckResult clean = new NagiosCheckResult(key, recurso.getValor().toLowerCase(), State.OK, valor);
			ColetorService.sendCleanFromMonitor(clean);
			// roles.sendCleanFromMonitor(key, tipoAlerta, recurso, valor);
			carregarAlertas();
			FacesContext.getCurrentInstance().addMessage("Teste Sucesso",
					new FacesMessage("Sucesso.", "A limpeza do alerta foi executada com sucesso."));
		} else {
			LOGGER.error("Tipo de recurso não pode ser nulo ou não foi reconhecido.");
			FacesContext.getCurrentInstance().addMessage("Teste Falha",
					new FacesMessage("Falha.", "Tipo de recurso naõ identificado. "));
		}
*/
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

}
