package br.com.sga.services;

import java.util.List;

import org.jboss.as.cli.scriptsupport.CLI;

public class CelulaCLIServices {
	public static CLI cli;
	private ServicosCLI service = new ServicosCLI();

	public boolean verficarCelula(String server) {
		List<String> listaCelula = service.readList("ls server-group");
		if (listaCelula.contains(server)) {
			return true;
		} else {
			return false;
		}
	}
}
