package br.com.sga.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import br.com.sga.monitoramento.DAO.UsuarioDAO;
import br.com.sga.monitoramento.model.Usuario;
import br.com.sga.services.ControladorMensagens;

@ViewScoped
@ManagedBean
public class EditarUsuarioBean implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private UsuarioDAO userDAO = new UsuarioDAO();
	private String senhaAlterada = new String();
	
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
	
	public String getSenhaAlterada() {
		return senhaAlterada;
	}
	public void setSenhaAlterada(String senhaAlterada) {
		this.senhaAlterada = senhaAlterada;
	}
	@PostConstruct
	public void inicia(){
		String idUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("UsuarioID");
		
		this.usuario = userDAO.retornaUsuario(Integer.parseInt(idUsuario));
	}
	
	public String cancela() {
		return "cadastro_usuario?faces-redirect=true";
	}
	
	public String salvaAlteracao() {
		try{
			if (this.usuario.getNome().length() < 2)
            {
				controladorMensagens.addMsgErro("Por favor, insira um nome Válido.");
				return null;
            } 
			if(!validaEmail(usuario.getEmail())){
		    	  controladorMensagens.addMsgErro("Email não é valido ! Insira por favor o e-mail corretamente.");
		    	  return null;
			}
			UsuarioDAO userDAO = new UsuarioDAO();
			
			if(!StringUtils.isEmpty(senhaAlterada))this.usuario.setSenha(senhaAlterada);
			userDAO.merge(usuario);
			controladorMensagens.addMsgInfo("Usuario Alterado com sucesso");
			return "cadastro_usuario.xhtml";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean validaEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			      return email.matches(regex);
	}
	
}
