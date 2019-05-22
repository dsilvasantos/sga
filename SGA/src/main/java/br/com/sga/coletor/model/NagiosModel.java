package br.com.sga.coletor.model;

public class NagiosModel {

	private String host;
	private String servico;
	private String tipoAlerta;
	private String message;
	
	public NagiosModel() {
		// TODO Auto-generated constructor stub
	}

	

	public NagiosModel(String host, String servico, String tipoAlerta,String message) {
		super();
		this.host = host;
		this.servico = servico;
		this.tipoAlerta = tipoAlerta;
		this.message = message;
	}



	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public String getTipoAlerta() {
		return tipoAlerta;
	}



	public void setTipoAlerta(String tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}


}
