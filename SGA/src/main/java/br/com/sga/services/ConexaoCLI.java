package br.com.sga.services;

import org.jboss.as.cli.scriptsupport.CLI;


public class ConexaoCLI {

	public static CLI cli;


	public void ConnectCli() {
		String domain = LoadBundle.getValue(server.getNomeAmbiente() + "-host",PropertiesFile.MONITOR);
		String username = LoadBundle.getValue(server.getNomeAmbiente()
				+ "-username",PropertiesFile.MONITOR);
		String password = LoadBundle.getValue(server.getNomeAmbiente()
				+ "-password",PropertiesFile.MONITOR);
		int porta = Integer.parseInt(LoadBundle.getValue("porta",PropertiesFile.MONITOR));
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
