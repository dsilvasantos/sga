package br.com.sga.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class EditarUsuarioBean implements Serializable{
	
	/*
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private UsuarioDAO userDAO = new UsuarioDAO();
	private UsuarioController userCtrl = new UsuarioController();
	
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
		
		this.usuario = userCtrl.retornaUsuario(Integer.parseInt(idUsuario));
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
	
	*/
}
