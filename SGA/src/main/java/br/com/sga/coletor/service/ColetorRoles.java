package br.com.sga.coletor.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;

import org.jboss.logging.Logger;

import br.com.sga.coletor.model.NagiosModel;
import br.com.sga.coletor.model.TipoAlerta;
import br.com.sga.coletor.model.TipoRecurso;
import br.com.sga.monitoramento.DAO.RecursosAplicacaoDAO;
import br.com.sga.monitoramento.enumeration.StatusServer;
import br.com.sga.monitoramento.model.Datasource;
import br.com.sga.monitoramento.model.RecursosAplicacao;
import br.com.sga.monitoramento.model.Server;

public class ColetorRoles {

	public static Logger LOGGER = Logger.getLogger(ColetorRoles.class);
	private ColetorSend send = new ColetorSend();
	private SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private RecursosAplicacaoDAO recursosAplicacaoDAO = RecursosAplicacaoDAO.getInstance();

	String ok = "1";
	String warning = "2";
	String critical = "3";
	String minGRafico = "0";
	String maxGrafico = "4";

	public ColetorRoles() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Identifica o tipo de alerta, armazena em um MAP global. Caso este alerta
	 * sofra mudança de STATUS, notificar ao Nagios e envia por e-mail. A
	 * notificação por e-mail pode ser desabilitada nas propriedades. A lista de
	 * alertas e enviar ao nagios e a enviar por e-mail tambem são globais e estão
	 * definidas na classe ColetorService
	 */
	private void identificarAlertas(TipoAlerta alerta, String recurso, String key, String value, boolean sendAll) {
		NagiosModel nagios = new NagiosModel(key, recurso, alerta.getValor(), value);
		key = key + "_" + recurso;

		if (sendAll) {
			send.sendInformations(nagios);
		}

		// Armazenando coleta
		String[] values = new String[4];
		values[0] = alerta.getValor();
		values[1] = value;
		values[2] = recurso.toString();
		values[3] = sdt.format(new Date());
		ColetorService.coletas.put(key, values);

		switch (alerta) {
		case CRITICAL:
			if (ColetorService.alertas.containsKey(key)) {
				String[] valores = ColetorService.alertas.get(key);
				if (valores[0].equals(TipoAlerta.WARN.getValor())) {
					ColetorService.listaEmail.add(nagios);
					if (!sendAll) {
						send.sendInformations(nagios);
					}
				}
				// atualizar o alerta
				values[0] = TipoAlerta.CRITICAL.getValor();
				values[1] = value;
				values[2] = recurso.toString();
				values[3] = sdt.format(new Date());
				ColetorService.alertas.put(key, values);

			} else {
				values[0] = TipoAlerta.CRITICAL.getValor();
				values[1] = value;
				values[2] = recurso.toString();
				values[3] = sdt.format(new Date());
				ColetorService.alertas.put(key, values);
				ColetorService.listaEmail.add(nagios);
				if (!sendAll) {
					send.sendInformations(nagios);
				}
			}
			break;

		case WARN:
			if (ColetorService.alertas.containsKey(key)) {
				// atualizar o alerta
				values[0] = TipoAlerta.WARN.getValor();
				values[1] = value;
				values[2] = recurso.toString();
				values[3] = sdt.format(new Date());
				ColetorService.alertas.put(key, values);
			} else {
				// inserir novo alerta no nagios
				values[0] = TipoAlerta.WARN.getValor();
				values[1] = value;
				values[2] = recurso.toString();
				values[3] = sdt.format(new Date());
				ColetorService.alertas.put(key, values);
				ColetorService.listaEmail.add(nagios);
				if (!sendAll) {
					send.sendInformations(nagios);
				}
			}
			break;
		case OK:
			if (ColetorService.alertas.containsKey(key)) {
				// remove alerta
				ColetorService.alertas.remove(key);
				ColetorService.listaEmail.add(nagios);
				if (!sendAll) {
					send.sendInformations(nagios);
				}
			}
			break;
		default:
			LOGGER.error("Falha na identificação do alerta por tipo de alerta.");
			break;
		}
	}

	/**
	 * Tratamento do status do Host Controller em caso positivo Inicia o tratamento
	 * de todos os alertas de cada servidor deste Host Controller Niveis de alerta
	 * definidos em propriedades
	 */
	public void tratarAlertas(String host, Server server, boolean sendAll) throws MissingResourceException {
		String key = server.getHost();
		String mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Host Controller " + server.getHost()
				+ " em funcionamento|status=" + ok + ";" + warning + ";" + critical + ";" + minGRafico + ";"
				+ maxGrafico + ";";
		mensagem += "\n Host Controller em execução.";
		identificarAlertas(TipoAlerta.OK, TipoRecurso.STATUS.toString().toLowerCase(), key, mensagem, sendAll);

		// Tratando Versao Jboss
		String versaoJboss = recursosAplicacaoDAO.recupear(server.getNome(), "versao-jboss").getValor();
		if (server.getJbossVersion().equals(versaoJboss)) {
			mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Versão do JBoss OK = " + server.getJbossVersion()
					+ "|versao-jboss=" + ok + ";" + warning + ";" + critical + ";" + minGRafico + ";" + maxGrafico
					+ ";";
			mensagem += "\n Versão do JBoss OK.";
			identificarAlertas(TipoAlerta.OK, TipoRecurso.VERSAOJBOSS.toString().toLowerCase(), key, mensagem, sendAll);
		} else {
			mensagem = TipoAlerta.CRITICAL.getValor().toUpperCase() + " Versão do JBoss está diferente = "
					+ server.getJbossVersion() + "|versao-jboss=" + critical + ";" + warning + ";" + critical + ";"
					+ minGRafico + ";" + maxGrafico + ";";
			mensagem += "\n Versão do JBoss está diferente.";
			identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.VERSAOJBOSS.toString().toLowerCase(), key, mensagem,
					sendAll);
		}

		// Tratando alertas dos Servers
		key = key + "_" + server.getNome();
		tratarAlertaStatus(server, key, sendAll);
		if (server.isAtivo()) {
			tratarrAlertaHeap(server, key, sendAll);
			tratarAlertaThread(server, key, sendAll);
			tratarAlertaDatasources(server, key, sendAll);
			tratarrAlertaMetaspace(server, key, sendAll);
		}
	}

	/**
	 * Tratamento da memória Heap de cada JVM Niveis de alerta definidos em
	 * propriedades
	 */
	private void tratarrAlertaHeap(Server server, String key, boolean sendAll) {
		RecursosAplicacao recursosAplicacao = recursosAplicacaoDAO.recupear(server.getNome(), "heap");
		String maxGrafico = "100";
		String minGrafico = "0";
		String mensagem;

		try {
			int NumWarning = recursosAplicacao.getQuantidadeMaxima();
			int NumCritical = recursosAplicacao.getQuantiodadeCritica();

			if (Integer.parseInt(server.getJvm().getPercentUseHeap()) > NumCritical) {
				mensagem = TipoAlerta.CRITICAL.getValor().toUpperCase() + " Uso da memória heap="
						+ server.getJvm().getPercentUseHeap() + "%";
				mensagem += "|heap=" + server.getJvm().getPercentUseHeap() + ";" + warning + ";" + critical + ";"
						+ minGrafico + ";" + maxGrafico + ";";
				mensagem += "\n Uso da memória Heap superior a " + critical + "%";
				identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.HEAP.toString().toLowerCase(), key, mensagem,
						sendAll);
			} else if (Integer.parseInt(server.getJvm().getPercentUseHeap()) > NumWarning) {
				mensagem = TipoAlerta.WARN.getValor().toUpperCase() + " Uso da memória heap="
						+ server.getJvm().getPercentUseHeap() + "%";
				mensagem += "|heap=" + server.getJvm().getPercentUseHeap() + ";" + warning + ";" + critical + ";"
						+ minGrafico + ";" + maxGrafico + ";";
				mensagem += "\n Uso da memória Heap superior a " + warning + "%";
				identificarAlertas(TipoAlerta.WARN, TipoRecurso.HEAP.toString().toLowerCase(), key, mensagem, sendAll);
			} else {
				mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Uso da memória heap="
						+ server.getJvm().getPercentUseHeap() + "%";
				mensagem += "|heap=" + server.getJvm().getPercentUseHeap() + ";" + warning + ";" + critical + ";"
						+ minGrafico + ";" + maxGrafico + ";";
				mensagem += "\n Uso da memória Heap inferior a " + warning + "%";
				identificarAlertas(TipoAlerta.OK, TipoRecurso.HEAP.toString().toLowerCase(), key, mensagem, sendAll);
			}
		} catch (Exception e) {
			identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.HEAP.toString().toLowerCase(), key, null, sendAll);
			LOGGER.error("Falha ao recuperar informações das Threads em: " + key);
		}

	}

	/**
	 * Metaspace não possui limite de tamanho definido, por isso não é possível
	 * calcular o percentual de uso, apenas o uso em MB Após ter uma definição de
	 * tamanho, deverá implementar o percentual de uso.
	 * 
	 * @param server
	 * @param key
	 * @param sendAll
	 */
	// TODO aguardar a definição do limite do metaspace para calcular o percentual
	// de uso, por enquanto, apenas é informado a quantidade de uso em MB
	private void tratarrAlertaMetaspace(Server server, String key, boolean sendAll) {
		RecursosAplicacao recursosAplicacao = recursosAplicacaoDAO.recupear(server.getNome(), "metaspace");
		String maxGrafico = "600";
		String minGrafico = "0";
		int warning = 0;
		int critical = 0;
		try {
			critical = recursosAplicacao.getQuantiodadeCritica();
			warning = recursosAplicacao.getQuantidadeMaxima();
		} catch (Exception e) {
			LOGGER.error("Falha ao recuperar informações das Metaspace em: " + key);
		}

		int metaSpaceUse = Integer.parseInt(server.getMetaspaceUsedMB());
		if (metaSpaceUse > critical) {
			String mensagem = TipoAlerta.CRITICAL.getValor().toUpperCase() + " Memória Metaspace="
					+ server.getMetaspaceUsedMB() + "MB";
			mensagem += "|metaspace=" + server.getMetaspaceUsedMB() + ";" + warning + ";" + critical + ";" + minGrafico
					+ ";" + maxGrafico + ";";
			mensagem += "\nValor do Metaspace superior a " + critical + "MB";
			identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.METASPACE.toString().toLowerCase(), key, mensagem,
					sendAll);
		} else if (metaSpaceUse > warning) {
			String mensagem = TipoAlerta.WARN.getValor().toUpperCase() + " Memória Metaspace="
					+ server.getMetaspaceUsedMB() + "MB";
			mensagem += "|metaspace=" + server.getMetaspaceUsedMB() + ";" + warning + ";" + critical + ";" + minGrafico
					+ ";" + maxGrafico + ";";
			mensagem += "\nValor do Metaspace superior a " + warning + "MB";
			identificarAlertas(TipoAlerta.WARN, TipoRecurso.METASPACE.toString().toLowerCase(), key, mensagem, sendAll);
		} else if (metaSpaceUse < warning) {
			String mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Memória Metaspace="
					+ server.getMetaspaceUsedMB() + "MB";
			mensagem += "|metaspace=" + server.getMetaspaceUsedMB() + ";" + warning + ";" + critical + ";" + minGrafico
					+ ";" + maxGrafico + ";";
			mensagem += "\nValor do Metaspace inferior a " + warning + "MB";
			identificarAlertas(TipoAlerta.OK, TipoRecurso.METASPACE.toString().toLowerCase(), key, mensagem, sendAll);
		}
	}

	/**
	 * Tratamento das Threads específicas de cada JVM Niveis de alerta definidos em
	 * propriedades
	 */
	private void tratarAlertaThread(Server server, String key, boolean sendAll) {
		RecursosAplicacao recursosAplicacao = recursosAplicacaoDAO.recupear(server.getNome(), "thread");
		String mensagem;
		String minGrafico = "0";
		String maxGrafico ="600";
		int warning = 0;
		int critical = 0;

		try {
			critical = recursosAplicacao.getQuantiodadeCritica();
			warning = recursosAplicacao.getQuantidadeMaxima();
		} catch (Exception e) {
			LOGGER.error("Falha ao recuperar informações das Metaspace em: " + key);
		}

		if (server.getThread() > critical) {
			mensagem = TipoAlerta.CRITICAL.getValor().toUpperCase() + " Quantidade de threads=" + server.getThread();
			mensagem += "|threads=" + server.getThread() + ";" + warning + ";" + critical + ";" + minGrafico + ";"
					+ maxGrafico + ";";
			mensagem += "\nValor das Threads superior a " + critical;
			identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.THREAD.toString().toLowerCase(), key, mensagem,
					sendAll);
		} else if (server.getThread() > warning) {
			mensagem = TipoAlerta.WARN.getValor().toUpperCase() + " Quantidade de threads=" + server.getThread();
			mensagem += "|threads=" + server.getThread() + ";" + warning + ";" + critical + ";" + minGrafico + ";"
					+ maxGrafico + ";";
			mensagem += "\nValor das Threads superior a " + warning;
			identificarAlertas(TipoAlerta.WARN, TipoRecurso.THREAD.toString().toLowerCase(), key, mensagem, sendAll);
		} else {
			mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Quantidade de threads=" + server.getThread();
			mensagem += "|threads=" + server.getThread() + ";" + warning + ";" + critical + ";" + minGrafico + ";"
					+ maxGrafico + ";";
			mensagem += "\nValor das Threads inferior a " + warning;
			identificarAlertas(TipoAlerta.OK, TipoRecurso.THREAD.toString().toLowerCase(), key, mensagem, sendAll);
		}
	}

	/**
	 * Tratamento do status de cada JVM Niveis de alerta definidos em propriedades
	 */
	private void tratarAlertaStatus(Server server, String key, boolean sendAll) {
		String mensagem;
		if (server.isAtivo()) {
			if (server.getStatus().equals(StatusServer.running.getValor())) {
				mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Status JVM " + server.getNome() + "="
						+ server.getStatus();
				mensagem += "|status=" + ok + ";" + warning + ";" + critical + ";" + minGRafico + ";" + maxGrafico
						+ ";";
				mensagem += "\n Instância JVM com funcionamento OK.";
				identificarAlertas(TipoAlerta.OK, TipoRecurso.STATUS.toString().toLowerCase(), key, mensagem, sendAll);
			} else if (server.getStatus().equals(StatusServer.restartrequired.getValor())) {
				mensagem = TipoAlerta.WARN.getValor().toUpperCase() + " Status JVM " + server.getNome() + "="
						+ server.getStatus();
				mensagem += "|status=" + warning + ";" + warning + ";" + critical + ";" + minGRafico + ";" + maxGrafico
						+ ";";
				mensagem += "\n Instância JVM solicitando restart.";
				identificarAlertas(TipoAlerta.WARN, TipoRecurso.STATUS.toString().toLowerCase(), key, mensagem,
						sendAll);
			} else {
				mensagem = TipoAlerta.CRITICAL.getValor().toUpperCase() + " Status JVM " + server.getNome() + "="
						+ server.getStatus();
				mensagem += "|status=" + critical + ";" + warning + ";" + critical + ";" + minGRafico + ";" + maxGrafico
						+ ";";
				mensagem += "\n Instância JVM em falha.";
				identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.STATUS.toString().toLowerCase(), key, mensagem,
						sendAll);
			}
		} else {
			mensagem = TipoAlerta.CRITICAL.getValor() + " Status JVM " + server.getNome() + "=" + server.getStatus();
			mensagem += "|status=" + critical + ";" + warning + ";" + critical + ";" + minGRafico + ";" + maxGrafico
					+ ";";
			mensagem += "\n Não foi possível recuperar os valores da instância JVM.";
			identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.STATUS.toString().toLowerCase(), key, mensagem,
					sendAll);
		}
	}

	/**
	 * Tratamento dos datasources específicos da JVM. Niveis de alerta definidos em
	 * propriedades
	 */
	private void tratarAlertaDatasources(Server server, String key, boolean sendAll) {
		RecursosAplicacao recursosAplicacao = recursosAplicacaoDAO.recupear(server.getNome(), "thread");
		int warning = 0;
		int critical = 0;
		String mensagem;
		String minGrafico = "0";
		String maxGrafico = "100";

		try {
			warning = recursosAplicacao.getQuantidadeMaxima();
			critical = recursosAplicacao.getQuantiodadeCritica();
		} catch (Exception e) {
			LOGGER.error("Falha ao recuperar informações das ds em: " + key);
		}

		try {
			for (Datasource ds : server.getDatasources()) {
				if (Integer.parseInt(ds.getPercentUsage()) > critical) {
					mensagem = TipoAlerta.CRITICAL.getValor().toUpperCase() + " Datasource [" + ds.getNome()
							+ "] com percentual de uso = " + ds.getPercentUsage() + "%";
					mensagem += "|datasource_" + ds.getNome() + "=" + ds.getPercentUsage() + ";" + warning + ";"
							+ critical + ";" + minGrafico + ";" + maxGrafico + ";";
					mensagem += "\n Percentual de uso do datasource superior a " + critical + "%";
					mensagem += "\n Quantidade de conexões no POOL = " + ds.getActiveCount() + " de um total de "
							+ ds.getMaxCount();
					identificarAlertas(TipoAlerta.CRITICAL,
							TipoRecurso.DATASOURCE.toString().toLowerCase() + "_" + ds.getNome(), key, mensagem,
							sendAll);
				} else if (Integer.parseInt(ds.getPercentUsage()) > warning) {
					mensagem = TipoAlerta.WARN.getValor().toUpperCase() + " Datasource [" + ds.getNome()
							+ "] com percentual de uso = " + ds.getPercentUsage() + "%";
					mensagem += "|datasource_" + ds.getNome() + "=" + ds.getPercentUsage() + ";" + warning + ";"
							+ critical + ";" + minGrafico + ";" + maxGrafico + ";";
					mensagem += "\n Quantidade de conexões no pool = " + ds.getActiveCount();
					mensagem += "\n Quantidade de conexões no POOL = " + ds.getActiveCount() + " de um total de "
							+ ds.getMaxCount();
					identificarAlertas(TipoAlerta.WARN,
							TipoRecurso.DATASOURCE.toString().toLowerCase() + "_" + ds.getNome(), key, mensagem,
							sendAll);
				} else {
					mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Datasource [" + ds.getNome()
							+ "] com percentual de uso = " + ds.getPercentUsage() + "%";
					mensagem += "|datasource_" + ds.getNome() + "=" + ds.getPercentUsage() + ";" + warning + ";"
							+ critical + ";" + minGrafico + ";" + maxGrafico + ";";
					mensagem += "\n Percentual de uso do datasource inferior a " + warning + "%";
					mensagem += "\n Quantidade de conexões no POOL = " + ds.getActiveCount() + " de um total de "
							+ ds.getMaxCount();
					identificarAlertas(TipoAlerta.OK,
							TipoRecurso.DATASOURCE.toString().toLowerCase() + "_" + ds.getNome(), key, mensagem,
							sendAll);
				}

				// Informar o Maximo utilizado em paralelo
				String limite = ds.getMaxCount();
				mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Datasource [" + ds.getNome()
						+ "] com máximo utilizado simultaneamente = " + ds.getMaxInUseCount();
				mensagem += "|datasource_" + ds.getNome() + "=" + ds.getMaxInUseCount() + ";" + limite + ";" + limite
						+ ";" + 0 + ";" + limite + ";";
				mensagem += "\n Máximo de conexões simultâneas = " + ds.getMaxInUseCount() + " de um total de "
						+ ds.getMaxCount();
				identificarAlertas(TipoAlerta.OK,
						TipoRecurso.DATASOURCE.toString().toLowerCase() + "_" + ds.getNome() + "_MaxInUse", key,
						mensagem, sendAll);
			}
		} catch (Exception e) {
			identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.DATASOURCE.toString().toLowerCase(), key, null,
					sendAll);
			LOGGER.error("Falha ao recuperar informações dos Datasources: " + key);
		}
	}
}
