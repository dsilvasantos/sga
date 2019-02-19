package br.com.sga.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.model.Usuario;
import br.com.sga.services.ControladorMensagens;

@ManagedBean(name = "usuario")
@ViewScoped
public class UsuarioController {
	
	private ControladorMensagens cm = new ControladorMensagens();
	private Usuario user = new Usuario();
	
	
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	
	 public String envia(String nome, String senha){
		 if(this.user.getNome().equals("admin") && this.user.getSenha().equals("admin")){
			 return "inicio.xhtml";
		 }
		 	cm.addMsgErro("login.incorreto");
		 return "login.xhtml";
		 
	 }
	
}
