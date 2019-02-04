package br.com.sga.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.sga.monitoramento.enumeration.TiposGC;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.GC;
import br.com.sga.monitoramento.model.Jvm;

public class JvmServices {

	private ServicosCLI service = new ServicosCLI();
	private final String FULL_GC_MARK = "PS_MarkSweep";
	private final String FULL_GC_COMPRESS = "MarkSweepCompact";
	private final String MIN_GC_COPY = "Copy";
	private final String MIN_GC_SCAVENGE = "PS_Scavenge";
	AmbienteServices ambiente = new AmbienteServices();

	public Jvm getJvmInformations(Aplicacao aplicacao) {

		Jvm jvm = new Jvm();

		// Recuperando valores dos scripts do GC
		String cmdGetScripts = "ls /host=" + ambiente.getAmbiente().getHost() + "/aplicacao=" + aplicacao.getNome()
				+ "/core-service=platform-mbean/" + "type=garbage-collector/name";

		List<String> scriptsGC = getScriptsGC(cmdGetScripts);
		List<GC> gc = new ArrayList<GC>();
		for (String script : scriptsGC) {
			GC item = new GC();
			String cmdGetCount = "/host=" + ambiente.getAmbiente().getHost() + "/aplicacao=" + aplicacao.getNome()
					+ "/core-service=platform-mbean/" + "type=garbage-collector/name=" + script
					+ ":read-attribute(name=collection-count)";

			String cmdGetTime = "/host=" + ambiente.getAmbiente().getHost() + "/aplicacao=" + aplicacao.getNome()
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
		String cmdMemory = "ls /host=" + ambiente.getAmbiente().getHost() + "/aplicacao=" + aplicacao.getNome()
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
