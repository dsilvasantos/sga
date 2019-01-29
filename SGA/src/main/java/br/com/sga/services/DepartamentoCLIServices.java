package br.com.sga.services;

import java.util.List;

import org.jboss.as.cli.scriptsupport.CLI;

public class DepartamentoCLIServices {
	
	public static CLI cli;
	private ServicosCLI service = new ServicosCLI();

	public boolean verficarDepartamento(String server) {
		List<String> listaProfiles = service.readList("ls profile");
		if (listaProfiles.contains(server)) {
			return true;
		} else {
			return false;
		}
	}
}
