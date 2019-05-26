package br.com.sga.coletor.service;

import static br.com.sga.monitoramento.enumeration.Recursos.STATUS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;

import org.jboss.logging.Logger;

import br.com.sga.coletor.model.TipoAlerta;
import br.com.sga.coletor.model.TipoRecurso;
import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.DAO.ColetaDAO;
import br.com.sga.monitoramento.DAO.ErroDAO;
import br.com.sga.monitoramento.DAO.RecursosAplicacaoDAO;
import br.com.sga.monitoramento.enumeration.StatusServer;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Coleta;
import br.com.sga.monitoramento.model.Datasource;
import br.com.sga.monitoramento.model.Erro;
import br.com.sga.monitoramento.model.RecursosAplicacao;
import br.com.sga.monitoramento.model.Server;

public class ColetorRoles {

	public static Logger LOGGER = Logger.getLogger(ColetorRoles.class);
	private SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private RecursosAplicacaoDAO recursosAplicacaoDAO = new RecursosAplicacaoDAO();
	private AplicacaoDAO aplicacaoDAO = new AplicacaoDAO();
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
	 * sofra mudança de STATUS, notificar. A lista de alertas e global e esta
	 * definidas na classe ColetorService
	 */
	private void identificarAlertas(TipoAlerta alerta, String recurso, String key, String descricao,
			int idRecurso,String serverNome,Integer valor) {
		ErroDAO erroDAO = new ErroDAO();
		Aplicacao aplicacao = aplicacaoDAO.recupearAplicacao(serverNome);

		Erro erro = new Erro(alerta.getValor(), descricao, aplicacao.getId(), new Date(), "Aberto",idRecurso);
		key = key + "_" + recurso;

		ColetorService.coletas.put(key, erro);
		
		if(valor!=null) {
			ColetaDAO coleta = new ColetaDAO();
			coleta.persist(new Coleta(aplicacao.getId(),idRecurso,valor));
		}

		switch (alerta) {
		case CRITICAL:
			if (ColetorService.alertas.containsKey(key)) {
				erro = ColetorService.alertas.get(key);
				if (erro.getPrioridade().equals(TipoAlerta.WARN.getValor())) {
					// atualizar o alerta
					erro.setPrioridade(TipoAlerta.CRITICAL.getValor());
					erro.setDescricao(descricao);
					ColetorService.alertas.put(key, erro);
					erroDAO.merge(erro);
				} else {
					ColetorService.alertas.put(key, erro);
					erroDAO.merge(erro);
				}
			} else {
				ColetorService.alertas.put(key, erro);
				erroDAO.persist(erro);
			}
			break;

		case WARN:
			if (ColetorService.alertas.containsKey(key)) {
				erro = ColetorService.alertas.get(key);
				if (erro.getPrioridade().equals(TipoAlerta.CRITICAL.getValor())) {
					// atualizar o alerta
					erro.setPrioridade(TipoAlerta.WARN.getValor());
					erro.setDescricao(descricao);
					ColetorService.alertas.put(key, erro);
					erroDAO.merge(erro);
				}
			} else {
				// inserir novo alerta no nagios
				ColetorService.alertas.put(key, erro);
				erroDAO.persist(erro);
			}
			break;
		case OK:
			if (ColetorService.alertas.containsKey(key)) {
				// remove alerta
				ColetorService.alertas.remove(key);
				erro.setDataSolucao(new Date());
				erro.setStatus("Finalizado");
				erro.setSolucao("Solucionado automáticamente");
				erroDAO.persist(erro);
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
	public void tratarAlertas(String host, Server server) throws MissingResourceException {
		String key = server.getNome();
		String mensagem = null;
		String versaoJboss = null;
		RecursosAplicacao recursosAplicacao = new RecursosAplicacao();
		// Tratando Versao Jboss
		try {
			recursosAplicacao = recursosAplicacaoDAO.recupear(server.getNome(), "versao-jboss");
			versaoJboss = recursosAplicacao.getValor();
		} catch (Exception e) {
			LOGGER.error("Falha ao recuperar informações das Threads em: " + key);
		}
		if (server.getJbossVersion().equals(versaoJboss)) {
			mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Versão do JBoss OK = " + server.getJbossVersion()
					+ "|versao-jboss=" + versaoJboss;

			mensagem += "\n Versão do JBoss OK.";
			identificarAlertas(TipoAlerta.OK, TipoRecurso.VERSAOJBOSS.toString().toLowerCase(), key, mensagem, recursosAplicacao.getRecursos().getId(),
					server.getNome(),null);
		} else {
			mensagem = TipoAlerta.CRITICAL.getValor().toUpperCase() + " Versão do JBoss está diferente = "
					+ server.getJbossVersion() + "|versao-jboss=" + versaoJboss;
			mensagem += "\n Versão do JBoss está diferente.";
			identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.VERSAOJBOSS.toString().toLowerCase(), key, mensagem,
					recursosAplicacao.getRecursos().getId(), server.getNome(),null);
		}

		// Tratando alertas dos Server
		tratarAlertaStatus(server, key);
		if (server.isAtivo()) {
			tratarrAlertaHeap(server, key);
			tratarAlertaThread(server, key);
			tratarAlertaDatasources(server, key);
			tratarrAlertaMetaspace(server, key);
		}
	}

	/**
	 * Tratamento da memória Heap de cada JVM Niveis de alerta definidos em
	 * propriedades
	 */
	private void tratarrAlertaHeap(Server server, String key) {

		String maxGrafico = "100";
		String minGrafico = "0";
		String mensagem;

		try {
			RecursosAplicacao recursosAplicacao = recursosAplicacaoDAO.recupear(server.getNome(), "heap");
			int NumWarning = recursosAplicacao.getQuantidadeMaxima();
			int NumCritical = recursosAplicacao.getQuantiodadeCritica();

			int valorHeap = Integer.parseInt(server.getJvm().getPercentUseHeap());
			
			if (valorHeap > NumCritical) {
				mensagem = TipoAlerta.CRITICAL.getValor().toUpperCase() + " Uso da memória heap="
						+ server.getJvm().getPercentUseHeap() + "%";
				mensagem += "|heap=" + server.getJvm().getPercentUseHeap() + ";" + warning + ";" + critical + ";"
						+ minGrafico + ";" + maxGrafico + ";";
				mensagem += "\n Uso da memória Heap superior a " + critical + "%";
				identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.HEAP.toString().toLowerCase(), key, mensagem,
						recursosAplicacao.getRecursos().getId(), server.getNome(),valorHeap);
			} else if (valorHeap > NumWarning) {
				mensagem = TipoAlerta.WARN.getValor().toUpperCase() + " Uso da memória heap="
						+ server.getJvm().getPercentUseHeap() + "%";
				mensagem += "|heap=" + server.getJvm().getPercentUseHeap() + ";" + warning + ";" + critical + ";"
						+ minGrafico + ";" + maxGrafico + ";";
				mensagem += "\n Uso da memória Heap superior a " + warning + "%";
				identificarAlertas(TipoAlerta.WARN, TipoRecurso.HEAP.toString().toLowerCase(), key, mensagem, recursosAplicacao.getRecursos().getId(),
						server.getNome(),valorHeap);
			} else {
				mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Uso da memória heap="
						+ server.getJvm().getPercentUseHeap() + "%";
				mensagem += "|heap=" + server.getJvm().getPercentUseHeap() + ";" + warning + ";" + critical + ";"
						+ minGrafico + ";" + maxGrafico + ";";
				mensagem += "\n Uso da memória Heap inferior a " + warning + "%";
				identificarAlertas(TipoAlerta.OK, TipoRecurso.HEAP.toString().toLowerCase(), key, mensagem, recursosAplicacao.getRecursos().getId(),
						server.getNome(),valorHeap);
			}
		} catch (Exception e) {
			LOGGER.error("Falha ao recuperar informações de Heap em: " + key);
		}

	}

	/**
	 * Metaspace não possui limite de tamanho definido, por isso não é possível
	 * calcular o percentual de uso, apenas o uso em MB Após ter uma definição de
	 * tamanho, deverá implementar o percentual de uso.
	 * 
	 * @param server
	 * @param key
	 * @param recursosAplicacao.getRecursos().getId()
	 */
	// TODO aguardar a definição do limite do metaspace para calcular o percentual
	// de uso, por enquanto, apenas é informado a quantidade de uso em MB
	private void tratarrAlertaMetaspace(Server server, String key) {

		String maxGrafico = "600";
		String minGrafico = "0";
		int warning = 0;
		int critical = 0;
		RecursosAplicacao recursosAplicacao = new RecursosAplicacao();
		try {
			recursosAplicacao = recursosAplicacaoDAO.recupear(server.getNome(), "metaspace");
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
					recursosAplicacao.getRecursos().getId(), server.getNome(),metaSpaceUse);
		} else if (metaSpaceUse > warning) {
			String mensagem = TipoAlerta.WARN.getValor().toUpperCase() + " Memória Metaspace="
					+ server.getMetaspaceUsedMB() + "MB";
			mensagem += "|metaspace=" + server.getMetaspaceUsedMB() + ";" + warning + ";" + critical + ";" + minGrafico
					+ ";" + maxGrafico + ";";
			mensagem += "\nValor do Metaspace superior a " + warning + "MB";
			identificarAlertas(TipoAlerta.WARN, TipoRecurso.METASPACE.toString().toLowerCase(), key, mensagem, recursosAplicacao.getRecursos().getId(),
					server.getNome(),metaSpaceUse);
		} else if (metaSpaceUse < warning) {
			String mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Memória Metaspace="
					+ server.getMetaspaceUsedMB() + "MB";
			mensagem += "|metaspace=" + server.getMetaspaceUsedMB() + ";" + warning + ";" + critical + ";" + minGrafico
					+ ";" + maxGrafico + ";";
			mensagem += "\nValor do Metaspace inferior a " + warning + "MB";
			identificarAlertas(TipoAlerta.OK, TipoRecurso.METASPACE.toString().toLowerCase(), key, mensagem, recursosAplicacao.getRecursos().getId(),
					server.getNome(),metaSpaceUse);
		}
	}

	/**
	 * Tratamento das Threads específicas de cada JVM Niveis de alerta definidos em
	 * propriedades
	 */
	private void tratarAlertaThread(Server server, String key) {

		String mensagem;
		String minGrafico = "0";
		String maxGrafico = "600";
		int warning = 0;
		int critical = 0;
		RecursosAplicacao recursosAplicacao = new RecursosAplicacao();
		try {
			recursosAplicacao = recursosAplicacaoDAO.recupear(server.getNome(), "thread");
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
			identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.THREAD.toString().toLowerCase(), key, mensagem, recursosAplicacao.getRecursos().getId(),
					server.getNome(),server.getThread());
		} else if (server.getThread() > warning) {
			mensagem = TipoAlerta.WARN.getValor().toUpperCase() + " Quantidade de threads=" + server.getThread();
			mensagem += "|threads=" + server.getThread() + ";" + warning + ";" + critical + ";" + minGrafico + ";"
					+ maxGrafico + ";";
			mensagem += "\nValor das Threads superior a " + warning;
			identificarAlertas(TipoAlerta.WARN, TipoRecurso.THREAD.toString().toLowerCase(), key, mensagem, recursosAplicacao.getRecursos().getId(),
					server.getNome(),server.getThread());
		} else {
			mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Quantidade de threads=" + server.getThread();
			mensagem += "|threads=" + server.getThread() + ";" + warning + ";" + critical + ";" + minGrafico + ";"
					+ maxGrafico + ";";
			mensagem += "\nValor das Threads inferior a " + warning;
			identificarAlertas(TipoAlerta.OK, TipoRecurso.THREAD.toString().toLowerCase(), key, mensagem, recursosAplicacao.getRecursos().getId(),
					server.getNome(),server.getThread());
		}
	}

	/**
	 * Tratamento do status de cada JVM Niveis de alerta definidos em propriedades
	 */
	private void tratarAlertaStatus(Server server, String key) {
		String mensagem;
		if (server.isAtivo()) {
			if (server.getStatus().equals(StatusServer.running.getValor())) {
				mensagem = TipoAlerta.OK.getValor().toUpperCase() + " Status JVM " + server.getNome() + "="
						+ server.getStatus();
				mensagem += "|status=" + ok + ";" + warning + ";" + critical + ";" + minGRafico + ";" + maxGrafico
						+ ";";
				mensagem += "\n Instância JVM com funcionamento OK.";
				identificarAlertas(TipoAlerta.OK, TipoRecurso.STATUS.toString().toLowerCase(), key, mensagem,STATUS.getValor(),
						server.getNome(),null);
			} else if (server.getStatus().equals(StatusServer.restartrequired.getValor())) {
				mensagem = TipoAlerta.WARN.getValor().toUpperCase() + " Status JVM " + server.getNome() + "="
						+ server.getStatus();
				mensagem += "|status=" + warning + ";" + warning + ";" + critical + ";" + minGRafico + ";" + maxGrafico
						+ ";";
				mensagem += "\n Instância JVM solicitando restart.";
				identificarAlertas(TipoAlerta.WARN, TipoRecurso.STATUS.toString().toLowerCase(), key, mensagem, STATUS.getValor(),
						server.getNome(),null);
			} else {
				mensagem = TipoAlerta.CRITICAL.getValor().toUpperCase() + " Status JVM " + server.getNome() + "="
						+ server.getStatus();
				mensagem += "|status=" + critical + ";" + warning + ";" + critical + ";" + minGRafico + ";" + maxGrafico
						+ ";";
				mensagem += "\n Instância JVM em falha.";
				identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.STATUS.toString().toLowerCase(), key, mensagem,
						STATUS.getValor(), server.getNome(),null);
			}
		} else {
			mensagem = TipoAlerta.CRITICAL.getValor() + " Status JVM " + server.getNome() + "=" + server.getStatus();
			mensagem += "|status=" + critical + ";" + warning + ";" + critical + ";" + minGRafico + ";" + maxGrafico
					+ ";";
			mensagem += "\n Não foi possível recuperar os valores da instância JVM.";
			identificarAlertas(TipoAlerta.CRITICAL, TipoRecurso.STATUS.toString().toLowerCase(), key, mensagem, STATUS.getValor(),
					server.getNome(),null);
		}
	}

	/**
	 * Tratamento dos datasources específicos da JVM. Niveis de alerta definidos em
	 * propriedades
	 */
	private void tratarAlertaDatasources(Server server, String key) {

		int warning = 0;
		int critical = 0;
		String mensagem;
		String minGrafico = "0";
		String maxGrafico = "100";
		RecursosAplicacao recursosAplicacao = new RecursosAplicacao();
		try {
			recursosAplicacao = recursosAplicacaoDAO.recupear(server.getNome(), "data");
			warning = recursosAplicacao.getQuantidadeMaxima();
			critical = recursosAplicacao.getQuantiodadeCritica();
		} catch (Exception e) {
			LOGGER.error("Falha ao recuperar informações das ds em: " + key);
		}

		try {
			for (Datasource ds : server.getDatasources()) {
				
				int percentualUso = Integer.parseInt(ds.getPercentUsage());
				
				if (percentualUso > critical) {
					mensagem = TipoAlerta.CRITICAL.getValor().toUpperCase() + " Datasource [" + ds.getNome()
							+ "] com percentual de uso = " + ds.getPercentUsage() + "%";
					mensagem += "|datasource_" + ds.getNome() + "=" + ds.getPercentUsage() + ";" + warning + ";"
							+ critical + ";" + minGrafico + ";" + maxGrafico + ";";
					mensagem += "\n Percentual de uso do datasource superior a " + critical + "%";
					mensagem += "\n Quantidade de conexões no POOL = " + ds.getActiveCount() + " de um total de "
							+ ds.getMaxCount();
					identificarAlertas(TipoAlerta.CRITICAL,
							TipoRecurso.DATASOURCE.toString().toLowerCase() + "_" + ds.getNome(), key, mensagem,
							recursosAplicacao.getRecursos().getId(), server.getNome(),percentualUso);
				} else if (percentualUso > warning) {
					mensagem = TipoAlerta.WARN.getValor().toUpperCase() + " Datasource [" + ds.getNome()
							+ "] com percentual de uso = " + ds.getPercentUsage() + "%";
					mensagem += "|datasource_" + ds.getNome() + "=" + ds.getPercentUsage() + ";" + warning + ";"
							+ critical + ";" + minGrafico + ";" + maxGrafico + ";";
					mensagem += "\n Quantidade de conexões no pool = " + ds.getActiveCount();
					mensagem += "\n Quantidade de conexões no POOL = " + ds.getActiveCount() + " de um total de "
							+ ds.getMaxCount();
					identificarAlertas(TipoAlerta.WARN,
							TipoRecurso.DATASOURCE.toString().toLowerCase() + "_" + ds.getNome(), key, mensagem,
							recursosAplicacao.getRecursos().getId(), server.getNome(),percentualUso);
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
							recursosAplicacao.getRecursos().getId(), server.getNome(),percentualUso);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Falha ao recuperar informações dos Datasources: " + key);
		}
	}
}
