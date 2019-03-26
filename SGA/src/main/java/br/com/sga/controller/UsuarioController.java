package br.com.sga.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sga.monitoramento.model.Usuario;
import br.com.sga.services.ControladorMensagens;

@ManagedBean
@ViewScoped
public class UsuarioController {
	
	ControladorMensagens cm = new ControladorMensagens();
	private Usuario user = new Usuario();
	
	
	
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
	
}
