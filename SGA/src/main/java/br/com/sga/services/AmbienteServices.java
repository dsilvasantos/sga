package br.com.sga.services;

import javax.inject.Inject;

import br.com.sga.monitoramento.enumeration.AmbienteEnum;

public class AmbienteServices {
	
	public AmbienteEnum ambiente;
	
	@Inject
	public ConexaoCLI conexaoCLI;
	
	public void selecionarAmbiente(int opcao) {
		ambiente = ambiente.getAmbiente(opcao);
		conexaoCLI.ConnectCli(ambiente);
	}

}
