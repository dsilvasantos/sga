package br.com.sga.monitoramento.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum AmbienteEnum {
	PRODUCAO("Produção", "192.168.1.78", "sga", "sga.1234", 9999);

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

	public static AmbienteEnum getAmbiente(int opcao) {
		switch (opcao) {
		case 1:
			return PRODUCAO;
		default:
			return PRODUCAO;
		}
	}
	
	public static List<AmbienteEnum> getAmbientes() {
		List<AmbienteEnum> ambientes = new ArrayList<>();
		ambientes.add(PRODUCAO);
		return ambientes;
	}
}
