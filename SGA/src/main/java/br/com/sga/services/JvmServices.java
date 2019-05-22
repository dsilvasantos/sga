package br.com.sga.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.sga.monitoramento.enumeration.TiposGC;
import br.com.sga.monitoramento.model.DeploymentsVersion;
import br.com.sga.monitoramento.model.GC;
import br.com.sga.monitoramento.model.Jvm;
import br.com.sga.monitoramento.model.Server;

public class JvmServices {

	private ServicosCLI service = new ServicosCLI();
	private final String FULL_GC_MARK = "PS_MarkSweep";
	private final String FULL_GC_COMPRESS = "MarkSweepCompact";
	private final String MIN_GC_COPY = "Copy";
	private final String MIN_GC_SCAVENGE = "PS_Scavenge";
	AmbienteServices ambiente = new AmbienteServices();

	public void getServerInformations(Server server) {
		// Informações do Server Group
		String cmd = "/host=" + server.getHost() + "/server=" + server.getNome();

		String commandServerGroup = cmd + ":read-attribute(name=server-group)";
		server.setServerGroup(service.readAttribute(commandServerGroup));

		// Informações do Socket Binding Group
		String commandSocketBinding = "ls socket-binding-group";
		server.setSocketBindingGroup((service.readList(commandSocketBinding).get(0)));

		// Informações do Profile
		String commandProfile = cmd + ":read-attribute(name=profile-name)";
		server.setProfile(service.readAttribute(commandProfile));

		// Informações do port offset
		String commandPortOffSet = cmd + "/socket-binding-group=" + server.getSocketBindingGroup()
				+ ":read-attribute(name=port-offset)";
		server.setPortOffset(Integer.parseInt(service.readAttribute(commandPortOffSet)));

		// Infomração da versão do Jboss
		String commandVersaoJboss = cmd + ":read-attribute(name=product-version)";
		server.setJbossVersion(service.readAttribute(commandVersaoJboss));

		// Informações do tempo de uptime
		String commandUptime = cmd + "/core-service=platform-mbean/type=runtime:read-attribute(name=uptime)";
		String uptime = service.readAttribute(commandUptime).replace("L", "");
		long time = Long.parseLong(uptime);
		if (time < 36000000) {
			server.setServerUptime(String.format("%01d:%02d", TimeUnit.MILLISECONDS.toHours(time),
					TimeUnit.MILLISECONDS.toMinutes(time) % 60));
		}
		if (time > 36000000 && time < 360000000) {
			server.setServerUptime(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
					TimeUnit.MILLISECONDS.toMinutes(time) % 60));
		} else if (time > 360000000) {
			server.setServerUptime(String.format("%03d:%02d", TimeUnit.MILLISECONDS.toHours(time),
					TimeUnit.MILLISECONDS.toMinutes(time) % 60));
		}

		String commandThread = "/host=" + server.getHost() + "/server=" + server.getNome()
				+ "/core-service=platform-mbean/type=threading:read-attribute(name=thread-count)";
		server.setThread(Integer.parseInt(service.readAttribute(commandThread)));

		server.setUsedMetaspace(service.executeCommandListValue(
				"/host=" + server.getHost() + "/server=" + server.getNome()
						+ "/core-service=platform-mbean/type=memory-pool/name=Metaspace:read-attribute(name=usage)",
				"used"));
		server.setMaxMetaspace(service.executeCommandListValue(
				"/host=" + server.getHost() + "/server=" + server.getNome()
						+ "/core-service=platform-mbean/type=memory-pool/name=Metaspace:read-attribute(name=usage)",
				"max"));

		// Lista de Deployments
		String commandDeployments = "ls /deployment";

		List<String> deploys = service.readList(commandDeployments);
		List<DeploymentsVersion> listDeploys = new ArrayList<DeploymentsVersion>();

		for (String d : deploys) {
			listDeploys.add(new DeploymentsVersion(d));
		}

		for (DeploymentsVersion lista : listDeploys) {

			if (lista.getNomeAplicacao().contains(".ear")) {

				String nomeApp = lista.getNomeAplicacao().replace(":", "\\:");
				String subdeploy = cmd + "/deployment=" + nomeApp + "/subdeployment";
				List<String> subdeployments = service.readList("ls " + subdeploy);
				for (String sub : subdeployments) {
					if (sub.contains(".war")) {
						String web = subdeploy + "=" + sub + "/subsystem=web:read-attribute(name=context-root)";
						lista.setContextoRaiz(service.readAttribute(web));
						lista.setProfile(server.getProfile());
						server.setVersion(listDeploys);
					}
				}

			} else if (lista.getNomeAplicacao().contains(".war")) {
				String web = cmd + "/deployment=" + lista.getNomeAplicacao()
						+ "/subsystem=web:read-attribute(name=context-root)";
				lista.setContextoRaiz(service.readAttribute(web));
				lista.setProfile(server.getProfile());
				server.setVersion(listDeploys);

			} else {
				lista.setContextoRaiz(null);
				lista.setProfile(server.getProfile());
				server.setVersion(listDeploys);
			}
		}

		server.setVersion(listDeploys);
	}

	public Jvm getJvmInformations(Server server) {

		Jvm jvm = new Jvm();

		// Recuperando valores dos scripts do GC
		String cmdGetScripts = "ls /host=" + server.getHost() + "/server=" + server.getNome()
				+ "/core-service=platform-mbean/" + "type=garbage-collector/name";

		List<String> scriptsGC = getScriptsGC(cmdGetScripts);
		List<GC> gc = new ArrayList<GC>();
		for (String script : scriptsGC) {
			GC item = new GC();
			String cmdGetCount = "/host=" + server.getHost() + "/server=" + server.getNome()
					+ "/core-service=platform-mbean/" + "type=garbage-collector/name=" + script
					+ ":read-attribute(name=collection-count)";

			String cmdGetTime = "/host=" + server.getHost() + "/server=" + server.getNome()
					+ "/core-service=platform-mbean/" + "type=garbage-collector/name=" + script
					+ ":read-attribute(name=collection-time)";

			item.setNomeScript(script);
			item.setCount(service.readAttribute(cmdGetCount).replace("L", ""));
			item.setTempo(service.readAttribute(cmdGetTime).replace("L", ""));

			if (script.equals(FULL_GC_COMPRESS) | script.equals(FULL_GC_MARK)) {
				item.setTipo(TiposGC.fullGC.getValor());
			} else if (script.equals(MIN_GC_COPY) | script.equals(MIN_GC_SCAVENGE)) {
				item.setTipo(TiposGC.minGC.getValor());
			} else {
				item.setTipo(TiposGC.other.getValor());
			}
			gc.add(item);
		}

		jvm.setInfoGC(gc);

		// Recuperando informações da memória Heap
		String cmdMemory = "ls /host=" + server.getHost() + "/server=" + server.getNome()
				+ "/core-service=platform-mbean/type=memory";
		List<String> memory = parseResult(cmdMemory);
		jvm.setHeapUsage(getMegaByte(memory.get(0)));
		jvm.setPercentUse(getPercent(memory.get(0), memory.get(1)));
		jvm.setMaxHeap(getMegaByte(memory.get(1)));

		return jvm;

	}

	/**
	 * Realiza o parse no resultado da consulta da JVM para extrair apenas o valor
	 * de used e max da Heap e a quantidade de objetos pendentes de finalização
	 * 
	 * @param cli
	 * @param cmd
	 * @return
	 */
	private List<String> parseResult(String cmd) {
		List<String> lista = service.readList(cmd);

		// Recuperando valor da heap utilizada
		String heap = lista.get(0);
		String startString = "used\" => ";
		String endString = "L,\"committed\"";
		Pattern p1 = Pattern.compile(startString);
		Matcher m1 = p1.matcher(heap);

		Pattern p2 = Pattern.compile(endString);
		Matcher m2 = p2.matcher(heap);

		m1.find();
		m2.find();
		int start = m1.end();
		int end = m2.start();
		String heapUsed = heap.substring(start, end);

		// Recuperando valor maximo da heap
		startString = "max\" => ";
		endString = "L}";
		Pattern p3 = Pattern.compile(startString);
		Matcher m3 = p3.matcher(heap);

		Pattern p4 = Pattern.compile(endString);
		Matcher m4 = p4.matcher(heap);

		m3.find();
		m4.find();
		start = m3.end();
		end = m4.start();
		String heapMax = heap.substring(start, end);

		// Recuperando valor de objetos pendentes de finalização
		String objetosPendetes = lista.get(3);
		objetosPendetes = objetosPendetes.substring(objetosPendetes.indexOf("=") + 1);

		lista.clear();
		lista.add(heapUsed);
		lista.add(heapMax);
		lista.add(objetosPendetes);

		return lista;

	}

	private String getPercent(String used, String max) {
		Long v1 = Long.parseLong(used.trim());
		Long v2 = Long.parseLong(max.trim());
		Long total = v1 * 100 / v2;
		return total + "";

	}

	private String getMegaByte(String valor) {
		Long v1 = Long.parseLong(valor.trim());
		v1 = v1 / 1024 / 1024;
		return v1 + "";

	}

	private List<String> getScriptsGC(String cmd) {
		List<String> lista = service.readList(cmd);
		return lista;
	}

}
