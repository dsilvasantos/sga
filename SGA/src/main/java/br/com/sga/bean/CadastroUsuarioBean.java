package br.com.sga.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.DAO.UsuarioDAO;
import br.com.sga.monitoramento.enumeration.TiposUsuarios;
import br.com.sga.monitoramento.model.Celula;

import br.com.sga.monitoramento.model.Usuario;
import br.com.sga.services.ControladorMensagens;
import br.com.sga.services.SessionContext;

@ViewScoped
@ManagedBean(name = "cadastroUsuarioBean")
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
	private List<Usuario> listaUsuario = new ArrayList<Usuario>();
	private UsuarioDAO userDAO = new UsuarioDAO();
	private boolean permissao = false;
	private List<Celula> celulasSelecionadas = new ArrayList<Celula>();

	@EJB
	ControladorMensagens controladorMensagens;

	public Usuario getUsuario() {
		return this.usuario;
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

	@PostConstruct
	public void iniciar() {
		this.listaUsuario = listaUsuario();
	}

	public String remove() {
		return "removeUsuario.xhtml";
	}

	public String altera() {
		return "alteraUsuario.xhtml";
	}

	public String cancela() {
		return "cadastro_usuario?faces-redirect=true";
	}

	public String salvar() {
		try {
			if (this.usuario.getNome().length() < 2)
            {
				controladorMensagens.addMsgErro("Por favor, insira um nome V�lido.");
				return null;
            } 
			if(!validaEmail(usuario.getEmail())){
		    	  controladorMensagens.addMsgErro("Email n�o � valido ! Insira por favor o e-mail corretamente.");
		    	  return null;
			}
			userDAO.persist(usuario);
			usuario.setStatus(1);
			controladorMensagens.addMsgInfo("Usu�rio cadastrado com sucesso !");
			usuario = new Usuario();
			return "cadastro_usuario.xhtml";
		} catch (Exception e) {
			e.printStackTrace();
			controladorMensagens.addMsgErro("Erro ao incluir usu�rio !");
		}
		return null;
	}

	public boolean isPermissao() {
		if (TiposUsuarios.analistaSuporte.getValor() == SessionContext.getInstance().getUsuarioLogado().getTipo()) {
			return true;
		} else {
			return false;
		}

	}

	public void setPermissao(boolean permissao) {
		this.permissao = permissao;
	}

	public List<Celula> getCelulasSelecionadas() {
		return celulasSelecionadas;
	}

	public void setCelulasSelecionadas(List<Celula> celulasSelecionadas) {
		this.celulasSelecionadas = celulasSelecionadas;
	}

	private boolean validaEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			      return email.matches(regex);
	}
	
}
