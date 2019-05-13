package br.com.sga.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import br.com.sga.controller.UsuarioController;
import br.com.sga.monitoramento.DAO.UsuarioDAO;
import br.com.sga.monitoramento.model.Usuario;

@SuppressWarnings("serial")
public class EditarUsuarioBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario user = new Usuario();
	private UsuarioDAO userDAO = new UsuarioDAO();
	private UsuarioController userCtrl = new UsuarioController();
	
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public UsuarioDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UsuarioDAO userDAO) {
		this.userDAO = userDAO;
	}
	public UsuarioController getUserCtrl() {
		return userCtrl;
	}
	public void setUserCtrl(UsuarioController userCtrl) {
		this.userCtrl = userCtrl;
	}
	
	@PostConstruct
	public void inicia(){
		String idUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("UsuarioID");
		
		//this.user = userCtrl.retornaDepartamento(Integer.parseInt(idDepartamento));
	}

}
