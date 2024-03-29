package br.com.sga.coletor.service;

import java.util.MissingResourceException;

import org.jboss.logging.Logger;

import br.com.sga.coletor.model.Ambiente;
import br.com.sga.coletor.model.ColetorResult;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Server;

public class ColetorAnalyze {

	private ColetorRoles roles = new ColetorRoles();
	private static final Logger LOGGER = Logger.getLogger(ColetorAnalyze.class);

	public ColetorAnalyze() {
		// TODO Auto-generated constructor stub
	}

	public void processarAlertas(Ambiente ambiente, String nomeAmbiente, boolean sendAll)
			throws MissingResourceException {
		ColetorResult result = new ColetorResult();
		result.setNomeAmbiente(nomeAmbiente);
		for (Aplicacao s : ambiente.getAplicacaos()) {
			roles.tratarAlertas(s.getServer().getHost(), s.getServer());
		}
	}
}
