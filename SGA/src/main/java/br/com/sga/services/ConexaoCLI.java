package br.com.sga.services;

import org.jboss.as.cli.scriptsupport.CLI;

import br.com.sga.monitoramento.enumeration.AmbienteEnum;


public class ConexaoCLI {

	public static CLI cli;
	
	

	public void ConnectCli(AmbienteEnum ambiente) {
		String domain = ambiente.getHost();
		String username = ambiente.getUsuario();
		String password = ambiente.getSenha();
		int porta = ambiente.getPorta();
		getConnection(domain, porta, username, password);
	}

	public static void getConnection(String domain, int porta, String username, String password)
			throws IllegalStateException {
		try {
			cli = CLI.newInstance();
			cli.connect(domain, porta, username, password.toCharArray());
		} catch (IllegalStateException e) {
			throw e;
		}

	}

	public static void disconnect(CLI cli) {
		cli.disconnect();
	}

	public static CLI getCli() {
		return cli;
	}
}
