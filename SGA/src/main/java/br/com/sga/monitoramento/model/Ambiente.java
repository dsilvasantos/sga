package br.com.sga.monitoramento.model;

public class Ambiente {

	private String ambiente;
	private String host;
	private String usuario;
	private String senha;
	private int porta;

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public Ambiente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ambiente(String ambiente, String host, String usuario, String senha, int porta) {
		super();
		this.ambiente = ambiente;
		this.host = host;
		this.usuario = usuario;
		this.senha = senha;
		this.porta = porta;
	}
}
