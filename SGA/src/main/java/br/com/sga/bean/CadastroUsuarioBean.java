package br.com.sga.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.DAO.UsuarioDAO;
import br.com.sga.monitoramento.model.Usuario;
import br.com.sga.services.ControladorMensagens;

@ManagedBean(name = "cadastroUsuarioBean")
@ViewScoped
public class CadastroUsuarioBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private List<Usuario> listaUsuario = new ArrayList<Usuario>();
	private UsuarioDAO userDAO = new UsuarioDAO();
	
	
	@EJB
	ControladorMensagens controladorMensagens;
	
	@PostConstruct
	public void iniciar() {
		this.listaUsuario = listaUsuario();
	}
	
	public String remove(){
		return "remove_usuario.xhtml";
	}
	
	public String altera(){
		return "alteraUsuario.xhtml";
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
	
	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	
	private List<Usuario> listaUsuario() {
		UsuarioDAO user = new UsuarioDAO();
		return user.listaUsuario();
	}
	
	public String salvar(){
		try{
			userDAO.persist(usuario);
			controladorMensagens.addMsgInfo("incluido.sucesso.usuario");
			usuario = new Usuario();
			return "cadastro_usuario.xhtml";
		}catch(Exception e){
			controladorMensagens.addMsgErro("erro.inclusao.usuario");
		}
		return null;
	}
	
	
}
