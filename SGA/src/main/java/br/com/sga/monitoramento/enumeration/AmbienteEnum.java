package br.com.sga.monitoramento.enumeration;

public enum AmbienteEnum {
	PRODUCAO("Produ��o", "192.168.1.255", "sga", "sga.1234", 9990);

	private String ambiente;
	private String host;
	private String usuario;
	private String senha;
	private int porta;

	private AmbienteEnum(String ambiente, String host, String usuario, String senha, int porta) {
		this.ambiente = ambiente;
		this.host = host;
		this.usuario = usuario;
		this.senha = senha;
		this.porta = porta;
	}

	public final String getAmbiente() {
		return this.ambiente;
	}

	public final String getHost() {
		return this.host;
	}

	public final String getUsuario() {
		return this.usuario;
	}

	public final String getSenha() {
		return this.senha;
	}

	public final int getPorta() {
		return this.porta;
	}

	public AmbienteEnum getAmbiente(int opcao) {
		switch (opcao) {
		case 1:
			return PRODUCAO;
		default:
			return PRODUCAO;
		}
	}
}
