package br.com.sga.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.DAO.UsuarioDAO;

import br.com.sga.monitoramento.model.Usuario;

@ManagedBean(name = "cadastroUsuarioBean")
@ViewScoped
public class CadastroUsuarioBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private UsuarioDAO userDAO = new UsuarioDAO();
	private List<Usuario> listaUsuario = new ArrayList<Usuario>();
	
	public String inicia(){
		return null;
	}
	
	public String remove(){
		return "remove_usuario.xhtml";
	}
	
	public String altera(){
		return "altera_usuario.xhtml";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public UsuarioDAO getUserDAO() {
		return userDAO;
	}
	
	public void setUserDAO(UsuarioDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	
	
}
