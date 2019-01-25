package br.com.sga.services;

import br.com.sga.monitoramento.enumeration.AmbienteEnum;

public class AmbienteServices {
		
	
	public ConexaoCLI conexaoCLI  = new ConexaoCLI();
	
	public void selecionarAmbiente(int opcao) {
		AmbienteEnum ambiente = AmbienteEnum.getAmbiente(opcao);
		conexaoCLI.ConnectCli(ambiente);
	}

}
