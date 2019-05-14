package br.com.sga.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.DAO.UsuarioDAO;
import br.com.sga.monitoramento.model.Usuario;
import br.com.sga.services.ControladorMensagens;

@ManagedBean(name = "usuario")
@ViewScoped
public class UsuarioController {
	
	ControladorMensagens cm = new ControladorMensagens();
	private Usuario user = new Usuario();
	private UsuarioDAO userDAO = new UsuarioDAO();
	
	public Usuario getUsuario(){
		if(this.user != null){
			
		}
		return user;
	}
	
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	
	 public String envia(){
		 if("admin".equals(this.user.getLogin()) && "admin".equals(this.user.getSenha())){
			 return "inicio.xhtml";
		 }
		 	cm.addMsgErro("login.incorreto");
		 return "login.xhtml";
	 }
	 
	 public Usuario retornaUsuario(Integer idUsuario){
		 Usuario usuario = userDAO.retornaUsuario(idUsuario);
		 return usuario;
		 
	 }
	
	public Usuario bucaUsuario(Integer idUsuario){
		Usuario usuario = userDAO.buscaUsuario(idUsuario);
		return usuario;
	}
	
}
