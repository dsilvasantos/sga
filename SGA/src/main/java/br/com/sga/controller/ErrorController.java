package br.com.sga.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import br.com.sga.coletor.controller.ColetorControllerBean;
import br.com.sga.monitoramento.DAO.ErroDAO;
import br.com.sga.monitoramento.enumeration.TiposUsuarios;
import br.com.sga.monitoramento.model.Erro;
import br.com.sga.services.SessionContext;

@ManagedBean(name = "erro")
@RequestScoped
public class ErrorController {
	
	public static Logger LOGGER = Logger.getLogger(ErrorController.class);
	private List<Erro> alertas = new ArrayList<Erro>();
	
	private static Erro erro;
	
	public void sendErro(Erro erroChamado) {
		erro = erroChamado;
	}
	
	private String solucao;
	
	
	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	private boolean permissao;
	
	public List<Erro> recuperarErros() {
		int idUsuario = SessionContext.getInstance().getUsuarioLogado().getId();
		ErroDAO erroDao = new ErroDAO();
		return erroDao.recupearErroIDUsuario(idUsuario);
	}
	
	public boolean isPermissao() {
		if(TiposUsuarios.desenvolvedor.getValor() == SessionContext.getInstance().getUsuarioLogado().getTipo()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void setPermissao(boolean permissao) {
		this.permissao = permissao;
	}
	
	public boolean solucionar() {
		erro.setSolucao(solucao);
		erro.setStatus("Solucionado");
		erro.setDataSolucao(new Date());
		ErroDAO erroDAO = new ErroDAO();
		erroDAO.merge(erro);
		
		ColetorControllerBean coletorController =  new ColetorControllerBean();
		String key = coletorController.limparErro(erro);
		if (null != key) {
			FacesContext.getCurrentInstance().addMessage("Chamado Sucesso",
					new FacesMessage("Sucesso.", "O chamado foi solucionado com sucesso."));
		} else {
			LOGGER.error("Erro não pode ser nulo ou não foi reconhecido.");
			FacesContext.getCurrentInstance().addMessage("Chamado Falha",
					new FacesMessage("Falha.", "O chamado não foi solucionado. "));
		}

		return true;
	}
}
