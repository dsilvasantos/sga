package br.com.sga.services;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Stateless
public class ControladorMensagens implements Serializable {

	private static final long serialVersionUID = 1L;

	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");

	public void adicionaMensagem(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addMsgFatal(String msg) {
//		String summary = bundle.getString(msg);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, null);
		adicionaMensagem(message);
	}

	public void addMsgErro(String msg) {
//		String summary = bundle.getString(msg);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
		adicionaMensagem(message);
		
	}

	public void addMsgAlerta(String msg) {
//		String summary = bundle.getString(msg);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
		adicionaMensagem(message);
	}

	public void addMsgInfo(String msg) {
		//String summary = bundle.getString(msg);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
		adicionaMensagem(message);
	}

}
