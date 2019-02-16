package br.com.sga.services;
import javax.ws.rs.Path;

import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.model.Server;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Celula;

@Path("/teste")
public class teste {

	@Path("/inicio")
	public void inicio() throws Exception {
		AmbienteServices ambiente = new AmbienteServices();
		ambiente.selecionarAmbiente(1);
		
		AplicacaoCLI aplicacaoCLI = new AplicacaoCLI();
		JvmServices jvmServices =  new JvmServices();
		Aplicacao aplicacao = new Aplicacao();
		aplicacao = aplicacaoCLI.recuperarAplicacao("custo");
		aplicacao.getServer().setJvm(jvmServices.getJvmInformations(aplicacao.getServer()));
		aplicacaoCLI.stopAplicacao(aplicacao.getServer());
		System.out.println();
	}
}
