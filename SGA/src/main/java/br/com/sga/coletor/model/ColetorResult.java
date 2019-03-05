package br.com.sga.coletor.model;

import java.util.ArrayList;
import java.util.List;
public class ColetorResult {
	
	private String nomeAmbiente;
	private List<String> alertaStatus = new ArrayList<String>();
	private List<String> alertaHeap  = new ArrayList<String>();
	private List<String> alertaThread  = new ArrayList<String>();
	private List<String> alertaHost = new ArrayList<String>();
	private List<String> alertaDatasource = new ArrayList<String>();
	
	
	public ColetorResult() {
		// TODO Auto-generated constructor stub
	}


	public String getNomeAmbiente() {
		return nomeAmbiente;
	}


	public void setNomeAmbiente(String nomeAmbiente) {
		this.nomeAmbiente = nomeAmbiente;
	}


	public List<String> getAlertaStatus() {
		return alertaStatus;
	}


	public void setAlertaStatus(List<String> alertaStatus) {
		this.alertaStatus = alertaStatus;
	}


	public List<String> getAlertaHeap() {
		return alertaHeap;
	}


	public void setAlertaHeap(List<String> alertaHeap) {
		this.alertaHeap = alertaHeap;
	}


	public List<String> getAlertaThread() {
		return alertaThread;
	}


	public void setAlertaThread(List<String> alertaThread) {
		this.alertaThread = alertaThread;
	}


	public List<String> getAlertaHost() {
		return alertaHost;
	}


	public void setAlertaHost(List<String> alertaHost) {
		this.alertaHost = alertaHost;
	}


	public List<String> getAlertaDatasource() {
		return alertaDatasource;
	}


	public void setAlertaDatasource(List<String> alertaDatasource) {
		this.alertaDatasource = alertaDatasource;
	}


	

}
