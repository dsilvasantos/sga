package br.com.sga.services;

import java.util.List;

import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

import br.com.sga.monitoramento.enumeration.StatusServer;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Server;

public class AplicacaoCLI {
	public static Logger LOGGER = Logger.getLogger(AplicacaoCLI.class.getName());

	private ServicosCLI service = new ServicosCLI();
	AmbienteServices ambiente = new AmbienteServices();

	public void recuperarServer(Aplicacao aplicacao) {
		String host = getHost(aplicacao.getNome());
		Server server = new Server();
		server.setHost(host);
		server.setStatus(service.readAttribute(
				"/host=" + server.getHost() + "/server=" + aplicacao.getNome() + ":read-attribute(name=server-state)"));
		server.setImagem(getImagem(server.getStatus()));
		server.setDescricaoImagem(server.getStatus());
		server.setNome(aplicacao.getNome());
		aplicacao.setServer(server);
	}

	public boolean startAplicacao(Server server) throws Exception {
		String cmdGetHost = "ls /host";
		List<String> hosts = service.readList(cmdGetHost);
		for (String host : hosts) {
			String cmdStart = "/host=" + host + "/server-config=" + server.getNome() + ":start()";
			String result = service.executeCommand(cmdStart);
			if (result.contains("success")) {
				LOGGER.info("Comando START executado com sucesso no CLI.");
				return true;
			}
		}
		return false;
	}

	public boolean verificarAplicacao(Server server) throws Exception {
		String cmdGetHost = "ls /host";
		List<String> hosts = service.readList(cmdGetHost);
		for (String host : hosts) {
			String comando = "/host=" + host + "/server=" + server.getNome()
					+ "/subsystem=logging/log-file=aplicacao.log:read-log-file(tail=true,lines=100)";
			List<ModelNode> lista = service.executeCommandList(comando);
			if (lista.toString().contains("JBAS015874")) {
				LOGGER.info("Identificada string de sucesso no log de inicialização do servidor.");
				return true;
			}
		}
		LOGGER.error("Não foi Identificada string de sucesso no log de inicialização do servidor.");
		return false;

	}

	public boolean killAplicacao(Server server) throws Exception {
		String cmdStop = "/host=" + server.getHost() + "/server-config=" + server.getNome() + ":kill()";
		String result = service.executeCommand(cmdStop);
		if (result.contains("success")) {
			LOGGER.info("Comando KILL executado com sucesso no CLI.");
			return true;
		} else {
			LOGGER.error("Comando KILL executado com falha no CLI. " + result);
			return false;
		}
	}

	public boolean stopAplicacao(Server server) throws Exception {
		String cmdStop = null;
		cmdStop = "/host=" + server.getHost() + "/server-config=" + server.getNome() + ":destroy()";

		String result = service.executeCommand(cmdStop);
		if (result.contains("success")) {
			LOGGER.info("Comando DESTROY executado com sucesso no CLI.");
			return true;
		} else {
			LOGGER.error("Comando DESTROY executado com falha no CLI. " + result);
			return false;
		}
	}

	public boolean destroyAplicacao(Server server) throws Exception {
		String cmdStop = "/host=" + server.getHost() + "/server-config=" + server.getNome() + ":destroy()";

		String result = service.executeCommand(cmdStop);
		if (result.contains("success")) {
			LOGGER.info("Comando DESTROY executado com sucesso no CLI.");
			return true;
		} else {
			LOGGER.error("Comando DESTROY executado com falha no CLI. " + result);
			return false;
		}
	}

	public String statusAplicacao(Server server) throws Exception {

		String cmdGetHost = "ls /host";
		List<String> hosts = service.readList(cmdGetHost);
		String result = null;
		for (String host : hosts) {
			String cmdStatus = "/host=" + host + "/server-config=" + server.getNome() + ":read-attribute(name=status)";
			result = service.readAttribute(cmdStatus);
			if (result.equals("STARTED")) {
				return result;
			}
		}
		return result;
	}

	public String statusAplicacaoStop(Server server) throws Exception {

		String cmdStatus = "/host=" + server.getHost() + "/server-config=" + server.getNome()
				+ ":read-attribute(name=status)";
		return service.readAttribute(cmdStatus);

	}

	/**
	 * Atribui uma imagem de acordo com o status identificado no server
	 * 
	 * @param status
	 * @return
	 */
	private String getImagem(String status) {
		if (status.equals(StatusServer.running.getValor())) {
			return "../imagens/ok-16.png";
		} else if (status.equals(StatusServer.restartrequired.getValor())) {
			return "../imagens/warning-16.png";
		} else {
			return "../imagens/error-16.png";
		}
	}

	public String getHost(String aplicacao) {
		String cmdGetHost = "ls /host";
		List<String> hosts = service.readList(cmdGetHost);
		for (String host : hosts) {
			String cmdGetAplicacao = "ls /host=" + host + "/server";
			List<String> aplicacoes = service.readList(cmdGetAplicacao);
			if (aplicacoes.contains(aplicacao)) {
				return host;
			}
		}
		return null;
	}
}
