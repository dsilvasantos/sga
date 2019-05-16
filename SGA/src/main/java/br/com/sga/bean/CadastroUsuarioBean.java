
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

@ViewScoped
@ManagedBean(name = "cadastroUsuarioBean")
public class CadastroUsuarioBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private List<Usuario> listaUsuario = new ArrayList<Usuario>();
	private UsuarioDAO userDAO = new UsuarioDAO();
	
	private String celula;
	
	@EJB
	ControladorMensagens controladorMensagens;
	
	/*public List<Usuario> getUsuario() {
		if(celula != null){
			userDAO.recupearPorCelula(celula);
		}
		return new ArrayList<>();
	}*/

	public Usuario getUsuario() {
		return this.usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getCelula() {
		return celula;
	}

	public void setCelula(String celula) {
		this.celula = celula;
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
	
	@PostConstruct
	public void iniciar() {
		this.listaUsuario = listaUsuario();
	}
	
	public String remove(){
		return "removeUsuario.xhtml";
	}
	
	public String altera(){
		return "alteraUsuario.xhtml";
	}
	
	public String cancela() {
		return "cadastro_usuario?faces-redirect=true";
	}
	
	public String salvar(){
		try{
			userDAO.persist(usuario);
			controladorMensagens.addMsgInfo("Usuário cadastrado com sucesso !");
			usuario = new Usuario();
			return "cadastro_usuario.xhtml";
		}catch(Exception e){
			controladorMensagens.addMsgErro("Erro ao incluir usuário !");
		}
		return null;
	}
	
	
	
}
