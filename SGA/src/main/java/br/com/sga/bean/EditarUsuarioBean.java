package br.com.sga.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sga.controller.UsuarioLogadoMBImpl;
import br.com.sga.monitoramento.DAO.UsuarioDAO;
import br.com.sga.monitoramento.model.Usuario;
import br.com.sga.services.ControladorMensagens;

@ViewScoped
@ManagedBean
public class EditarUsuarioBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private UsuarioDAO userDAO = new UsuarioDAO();
	private UsuarioLogadoMBImpl userCtrl = new UsuarioLogadoMBImpl();
	
	@EJB
	ControladorMensagens controladorMensagens;

	
	public Usuario getUser() {
		return usuario;
	}
	public void setUser(Usuario user) {
		this.usuario = user;
	}
	public UsuarioDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UsuarioDAO userDAO) {
		this.userDAO = userDAO;
	}
	public UsuarioLogadoMBImpl getUserCtrl() {
		return userCtrl;
	}
	public void setUserCtrl(UsuarioLogadoMBImpl userCtrl) {
		this.userCtrl = userCtrl;
	}
	
	
	public String cancela() {
		return "cadastro_usuario?faces-redirect=true";
	}
	
	public String salvaAlteracao() {
		try{
			UsuarioDAO userDAO = new UsuarioDAO();
			userDAO.merge(usuario);
			controladorMensagens.addMsgInfo("Usuario Alterado com sucesso");
			return "cadastro_usuario.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
