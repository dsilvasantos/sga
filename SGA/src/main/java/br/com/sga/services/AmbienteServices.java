package br.com.sga.services;

import br.com.sga.monitoramento.enumeration.AmbienteEnum;

public class AmbienteServices {
		
	
	public ConexaoCLI conexaoCLI  = new ConexaoCLI();
	private static AmbienteEnum ambiente;
	
	public void selecionarAmbiente(int opcao) {
		ambiente = AmbienteEnum.getAmbiente(opcao);
		conexaoCLI.ConnectCli(ambiente);
	}

	public AmbienteEnum getAmbiente() {
		return ambiente;
	}
}
