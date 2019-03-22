package br.com.sga.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import br.com.sga.coletor.controller.ColetorControllerBean;
import br.com.sga.coletor.service.ColetorRoles;
import br.com.sga.coletor.service.ColetorService;
import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.DAO.ErroDAO;
import br.com.sga.monitoramento.DAO.RecursosDAO;
import br.com.sga.monitoramento.DAO.UsuarioDAO;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Erro;
import br.com.sga.monitoramento.model.Recursos;
import br.com.sga.monitoramento.model.Usuario;
import br.com.sga.services.AmbienteServices;
import br.com.sga.services.AplicacaoCLI;

@ManagedBean(name = "chamado")
@ViewScoped
public class ChamadoController {

	public static Logger LOGGER = Logger.getLogger(ChamadoController.class);

	String user;

	private CelulaDAO celulaDao = new CelulaDAO();

	private AplicacaoCLI aplicacaoCLI = new AplicacaoCLI();

	private String departamentoChamado;
	private String celulaChamado;
	private static Erro erro;
	private List<Usuario> usuarios = new ArrayList<>();

	UsuarioDAO usuarioDAO = new UsuarioDAO();

	public List<String> getCelulas() {
		if (departamentoChamado != null) {
			return celulaDao.recupear(departamentoChamado);
		}
		return new ArrayList<>();
	}

	public void sendErro(Erro erroChamado) {
		erro = erroChamado;
	}

	public void carregarFuncionarios() {
		usuarios = usuarioDAO.recupearPorCelula(celulaChamado);
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public boolean abrirChamado(Usuario usuario) {
		ColetorControllerBean coletor = new ColetorControllerBean();
		System.out.println(usuario);
		System.out.println(erro);

		String key;
		LOGGER.info("Usuário [" + user + "] solicitou clean do alrame: ");

		key = coletor.recuperarAplicacao(erro.getAplicacao()) + "_" + coletor.recuperarRecurso(erro.getRecurso());
		erro.setUsuario(usuario.getId());
		erro.setStatus("Designado");
		ColetorService.alertas.put(key, erro);
		ErroDAO erroDAO = new ErroDAO();
		erroDAO.merge(erro);
		coletor.carregarColeta();
		if (null != key) {
			coletor.carregarAlertas();
			FacesContext.getCurrentInstance().addMessage("Chamado Sucesso",
					new FacesMessage("Sucesso.", "O chamado foi designado com sucesso."));
		} else {
			LOGGER.error("Tipo de recurso não pode ser nulo ou não foi reconhecido.");
			FacesContext.getCurrentInstance().addMessage("Chamado Falha",
					new FacesMessage("Falha.", "O chamado não foi designado. "));
		}

		return true;
	}

	public String getDepartamentoChamado() {
		return departamentoChamado;
	}

	public void setDepartamentoChamado(String departamentoChamado) {
		this.departamentoChamado = departamentoChamado;
	}

	public String getCelulaChamado() {
		return celulaChamado;
	}

	public void setCelulaChamado(String celulaChamado) {
		this.celulaChamado = celulaChamado;
	}
}
