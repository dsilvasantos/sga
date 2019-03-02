package br.com.sga.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.TreeNode;

import br.com.sga.monitoramento.model.Aplicacao;

@ManagedBean(name="monitoramento")
@ViewScoped
public class MonitoramentoController {
	  private TreeNode root;
	     
	    private Aplicacao selectedDocument;
	         
	    @ManagedProperty("#{server}")
	    private ServerController service;
	     
	    @PostConstruct
	    public void init() {
	        root = service.createAplicacoes();
	    }
	 
	    public TreeNode getRoot() {
	        return root;
	    }
	 
	    public void setService(ServerController service) {
	        this.service = service;
	    }
	 
	    public Aplicacao getSelectedDocument() {
	        return selectedDocument;
	    }
	 
	    public void setSelectedDocument(Aplicacao selectedDocument) {
	        this.selectedDocument = selectedDocument;
	    }
}
