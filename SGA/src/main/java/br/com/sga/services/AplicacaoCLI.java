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
		Aplicacao aplicacao = new Aplicacao();
		String host = getHost(nome);
		if(host.isEmpty()) {
			return null;
		}
		aplicacao.setHost(host);
		aplicacao.setNome(nome);
		aplicacao.setStatus(service.readAttribute("/host="+aplicacao.getHost()+"/server="+aplicacao.getNome()+":read-attribute(name=server-state)"));
		aplicacao.setImagem(getImagem(aplicacao.getStatus()));
		aplicacao.setDescricaoImagem(aplicacao.getStatus());
		return aplicacao;
	}
	
	public boolean startAplicacao(Aplicacao aplicacao) throws Exception {
		String cmdStart = "/host=" + aplicacao.getHost() + "/server-config=" + aplicacao.getNome() + ":start()";
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
		String comando = "/host=" + aplicacao.getHost() + "/server=" + aplicacao.getNome()
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
		String cmdStop = "/host=" + aplicacao.getHost() + "/server-config=" + aplicacao.getNome() + ":kill()";
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
		cmdStop = "/host=" + aplicacao.getHost() + "/server-config=" + aplicacao.getNome() + ":destroy()";
		
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
			return "../../imagens/ok-16.png";
		}else if(status.equals(StatusServer.restartrequired.getValor())){
			return "../../imagens/warning-16.png";
		}else{
			return "../../imagens/error-16.png";
		}
	}
	
	public String getHost(String aplicacao) {
		String cmdGetHost = "ls /host";
		List<String> hosts = service.readList(cmdGetHost);
		for(String host:hosts) {
			String cmdGetAplicacao = "ls /host=" + host + "/server";
			List<String> aplicacoes = service.readList(cmdGetAplicacao);
			if(aplicacoes.contains(aplicacao)) {
				return host;
			}
		}
		return null;
	}
}
