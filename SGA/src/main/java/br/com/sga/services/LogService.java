package br.com.sga.services;

import java.util.ArrayList;
import java.util.List;

import org.jboss.as.cli.scriptsupport.CLI;
import org.jboss.dmr.ModelNode;

import br.com.sga.monitoramento.enumeration.LogLevel;
import br.com.sga.monitoramento.model.Server;
import br.com.sga.monitoramento.model.Log;

public class LogService {

	public LogService() {
	}
	
	private ServicosCLI service = new ServicosCLI();

	public List<Log> getLogs(Server server, int linhas) {
		
		String comando = "/host=" + server.getHost() + "/server=" + server.getNome()
				+ "/subsystem=logging/log-file=server.log:read-log-file(tail=true,lines=" + linhas + ")";

		List<ModelNode> lista = service.executeCommandList(comando);
		List<Log> logs = new ArrayList<Log>();
		for (ModelNode l : lista) {
			if (l.toString().contains(LogLevel.INFO.getLevel())) {
				logs.add(new Log(LogLevel.INFO, l.toString().replace("\"", ""), "blue"));
			} else if (l.toString().contains(LogLevel.WARN.getLevel())) {
				logs.add(new Log(LogLevel.WARN, l.toString().replace("\"", ""), "orange"));
			} else if (l.toString().contains(LogLevel.ERROR.getLevel())) {
				logs.add(new Log(LogLevel.ERROR, l.toString().replace("\"", ""), "red"));
			} else if (l.toString().contains(LogLevel.DEBUG.getLevel())) {
				logs.add(new Log(LogLevel.DEBUG, l.toString().replace("\"", ""), "black"));
			}
		}
		return logs;
	}
}
