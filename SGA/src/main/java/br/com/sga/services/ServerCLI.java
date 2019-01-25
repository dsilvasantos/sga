package br.com.sga.services;

import java.util.List;

import org.jboss.as.cli.scriptsupport.CLI;

public class ServerCLI {
    /*
	private static ServicosCLI service;

	public boolean startServer(Server server) throws Exception {
		String cmdStart = "/host=" + server.getNomeHost() + "/server-config=" + server.getNomeServer() + ":start()";
		String result = service.executeCommand(cmdStart);
		if (result.contains("success")) {
			LOGGER.info("Comando START executado com sucesso no CLI.");
			return true;
		} else {
			LOGGER.error("Comando START executado com falha no CLI. " + result);
			return false;
		}
	}

	public boolean verifyServer(Server server) throws Exception {
		String comando = "/host=" + server.getNomeHost() + "/server=" + server.getNomeServer()
				+ "/subsystem=logging/log-file=server.log:read-log-file(tail=true,lines=100)";
		List<ModelNode> lista = service.executeCommand(comando);
		if (lista.toString().contains("JBAS015874")) {
			LOGGER.info("Identificada string de sucesso no log de inicialização do servidor.");
			return true;
		} else {
			LOGGER.error("Não foi Identificada string de sucesso no log de inicialização do servidor.");
			return false;
		}
	}

	
	public boolean killServer(Server server) throws Exception {
		String cmdStop = "/host=" + server.getNomeHost() + "/server-config=" + server.getNomeServer() + ":kill()";
		String result = service.executeCommand(cmdStop);
		if (result.contains("success")) {
			LOGGER.info("Comando KILL executado com sucesso no CLI.");
			return true;
		} else {
			LOGGER.error("Comando KILL executado com falha no CLI. " + result);
			return false;
		}
	}

	
	public boolean stopServer(Server server) throws Exception {
		String cmdStop = null;

		if (server.getNomeHost().contains("ST-")) {
			cmdStop = ":shutdown(timeout=2)";

		} else {
			cmdStop = "/host=" + server.getNomeHost() + "/server-config=" + server.getNomeServer() + ":destroy()";
		}
		String result = service.executeCommand(cmdStop);
		if (result.contains("success")) {
			LOGGER.info("Comando DESTROY executado com sucesso no CLI.");
			return true;
		} else {
			LOGGER.error("Comando DESTROY executado com falha no CLI. " + result);
			return false;
		}
	}
*/
}
