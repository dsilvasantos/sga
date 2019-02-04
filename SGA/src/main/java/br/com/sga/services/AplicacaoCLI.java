package br.com.sga.services;

import java.util.List;

import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

import br.com.sga.monitoramento.enumeration.StatusServer;
import br.com.sga.monitoramento.model.Aplicacao;

public class AplicacaoCLI {
	public static Logger LOGGER = Logger.getLogger(AplicacaoCLI.class.getName());

	private ServicosCLI service = new ServicosCLI();
	AmbienteServices ambiente = new AmbienteServices();
	
	public Aplicacao recuperarAplicacao(String nome) {
		Aplicacao aplicao = new Aplicacao();
		aplicao.setNome(nome);
		aplicao.setStatus(service.readAttribute("/host="+ambiente.getAmbiente().getHost()+"/server="+aplicao.getNome()+":read-attribute(name=server-state)"));
		aplicao.setImagem(getImagem(aplicao.getStatus()));
		aplicao.setDescricaoImagem(aplicao.getStatus());
		return aplicao;
	}
	
	public boolean startAplicacao(Aplicacao aplicacao) throws Exception {
		String cmdStart = "/host=" + ambiente.getAmbiente().getHost() + "/aplicacao-config=" + aplicacao.getNome() + ":start()";
		String result = service.executeCommand(cmdStart);
		if (result.contains("success")) {
			LOGGER.info("Comando START executado com sucesso no CLI.");
			return true;
		} else {
			LOGGER.error("Comando START executado com falha no CLI. " + result);
			return false;
		}
	}

	public boolean verifyAplicacao(Aplicacao aplicacao) throws Exception {
		String comando = "/host=" + ambiente.getAmbiente().getHost() + "/aplicacao=" + aplicacao.getNome()
				+ "/subsystem=logging/log-file=aplicacao.log:read-log-file(tail=true,lines=100)";
		List<ModelNode> lista = service.executeCommandList(comando);
		if (lista.toString().contains("JBAS015874")) {
			LOGGER.info("Identificada string de sucesso no log de inicialização do servidor.");
			return true;
		} else {
			LOGGER.error("Não foi Identificada string de sucesso no log de inicialização do servidor.");
			return false;
		}
	}

	
	public boolean killAplicacao(Aplicacao aplicacao) throws Exception {
		String cmdStop = "/host=" + ambiente.getAmbiente().getHost() + "/aplicacao-config=" + aplicacao.getNome() + ":kill()";
		String result = service.executeCommand(cmdStop);
		if (result.contains("success")) {
			LOGGER.info("Comando KILL executado com sucesso no CLI.");
			return true;
		} else {
			LOGGER.error("Comando KILL executado com falha no CLI. " + result);
			return false;
		}
	}

	
	public boolean stopAplicacao(Aplicacao aplicacao) throws Exception {
		String cmdStop = null;
		cmdStop = "/host=" + ambiente.getAmbiente().getHost() + "/aplicacao-config=" + aplicacao.getNome() + ":destroy()";
		
		String result = service.executeCommand(cmdStop);
		if (result.contains("success")) {
			LOGGER.info("Comando DESTROY executado com sucesso no CLI.");
			return true;
		} else {
			LOGGER.error("Comando DESTROY executado com falha no CLI. " + result);
			return false;
		}
	}
	
	/**
	 * Atribui uma imagem de acordo com o status identificado no server
	 * @param status
	 * @return
	 */
	private String getImagem(String status){
		if(status.equals(StatusServer.running.getValor())){
			return "../../images/ok-16.png";
		}else if(status.equals(StatusServer.restartrequired.getValor())){
			return "../../images/warning-16.png";
		}else{
			return "../../images/error-16.png";
		}
	}
}
