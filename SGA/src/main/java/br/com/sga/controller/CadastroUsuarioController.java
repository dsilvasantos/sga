package br.com.sga.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.sga.monitoramento.DAO.UsuarioDAO;
import br.com.sga.monitoramento.model.Usuario;


@ManagedBean(name = "cadastroUsuario")
@ViewScoped
public class CadastroUsuarioController {

private Usuario usuario = new Usuario();
	
	public Usuario getCadastroUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuario() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		return usuarioDAO.recupearPorCelula();
	}
	
	public void salvar(Usuario usuario){
		FacesMessage face = null;
		if(usuario != null){
		UsuarioDAO dp = new UsuarioDAO();
		dp.persist(usuario);	
		face = new FacesMessage("Usuário cadastrado com Sucesso !");
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, face);
		}

	}		
	
	public void remove(Usuario usuario){
			UsuarioDAO us = new UsuarioDAO();
			us.remove(usuario);	
	}
	
	public  void alterar(Usuario usuario){
		UsuarioDAO us = new UsuarioDAO();
		us.merge(usuario);
	}

}
