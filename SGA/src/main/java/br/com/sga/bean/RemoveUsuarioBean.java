package br.com.sga.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class RemoveUsuarioBean implements Serializable   {
	
	/*

	
	private static final long serialVersionUID = 1L;
	
	private Usuario user = new Usuario();
	private UsuarioDAO userDAO = new UsuarioDAO();
	private UsuarioController userCtrl = new UsuarioController();
	
	@EJB
	ControladorMensagens controladorMensagens;

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
		
		this.user = userCtrl.retornaUsuario(Integer.parseInt(idUsuario));
	}
	
	public String cancela() {
		return "cadastro_usuario?faces-redirect=true";
	}
	
	public String exclui(){
		try{
			this.userDAO.remove(user);
			controladorMensagens.addMsgInfo("Usuario excluído com sucesso !");
			return "cadastro_usuario.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	*/
}
