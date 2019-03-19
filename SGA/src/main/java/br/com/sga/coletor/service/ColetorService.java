package br.com.sga.coletor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.jboss.logging.Logger;

import br.com.sga.coletor.model.Ambiente;
import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.enumeration.AmbienteEnum;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Datasource;
import br.com.sga.monitoramento.model.Erro;
import br.com.sga.monitoramento.model.Server;
import br.com.sga.services.AmbienteServices;
import br.com.sga.services.AplicacaoCLI;
import br.com.sga.services.DatasourceServices;
import br.com.sga.services.JvmServices;
import br.com.sga.services.ServicosCLI;

public class ColetorService extends TimerTask {

	private Ambiente ambiente;
	private ColetorAnalyze analyze;
	private static final Logger LOGGER = Logger.getLogger(ColetorService.class);
	List<AmbienteEnum> lista;
	public static Map<String, Erro> alertas = new HashMap<String, Erro>();
	
	public static Map<String, Erro> getAlertas() {
		return alertas;
	}

	public static void setAlertas(Map<String, Erro> alertas) {
		ColetorService.alertas = alertas;
	}

	public static Map<String, Erro> coletas = new HashMap<String, Erro>();
	int contador;
	boolean sendAll;
	int enviarNagios;
	AmbienteServices ambienteService = new AmbienteServices();
	private ServicosCLI service = new ServicosCLI();
	private JvmServices jvmServices = new JvmServices();

	private AplicacaoCLI aplicacaoCLI = new AplicacaoCLI();

	private DepartamentoDAO departamentoDAO = new DepartamentoDAO();
	private CelulaDAO celulaDAO = new CelulaDAO();
	private AplicacaoDAO aplicacaoDAO  = new AplicacaoDAO();
	public ColetorService() {
		lista = AmbienteEnum.getAmbientes();
		//enviarNagios = Integer.parseInt(LoadBundle.getValue("intervalo-envio-nagios", PropertiesFile.COLETOR));
		//contador = Integer.parseInt(LoadBundle.getValue("intervalo-envio-nagios", PropertiesFile.COLETOR)) - 1;

	}

	@Override
	public void run() {
		contador++;
		analyze = new ColetorAnalyze();
		ambiente = new Ambiente();
		if (contador == enviarNagios) {
			sendAll = true;
			contador = 0;
			analyze.notificarNagios();
		} else {
			sendAll = false;
		}
		LOGGER.info("#########  Iniciando ciclo de coleta  ###########");
		for (AmbienteEnum ambienteEnum : lista) {
			LOGGER.info("###########  Iniciando coleta do ambiente:" + ambienteEnum.getAmbiente() + " ############");
			try {
				ambiente.setNomeAmbiente(ambienteEnum.getAmbiente());
				coletarAmbiente(ambienteEnum);
				analyze.processarAlertas(ambiente, ambiente.getNomeAmbiente(), sendAll);
				LOGGER.info("###########  Finalizando coleta do ambiente:" + ambiente.getNomeAmbiente() + " ############");
			} catch (Exception e) {
				LOGGER.error(
						"Falha ao recuperar dados. Coleta do dados será realizada no próximo ciclo. "
								+ e.getMessage());
			}
		}
		LOGGER.info("Preparando envio para o Nagios via NRDP");
		// enviar dados ao BDC - enviar lista coletas
		LOGGER.info("Total de alertas armazenados: " + ColetorService.alertas.size());
		LOGGER.info("#########  Finalizando ciclo de coleta  ###########");
	}

	private void coletarAmbiente(AmbienteEnum ambienteEnum) throws IllegalStateException, IllegalArgumentException {
		DatasourceServices data = new DatasourceServices();
		List<Aplicacao> aplicacoesFinalizadas = new ArrayList<>();
		try {
			ambienteService.selecionarAmbiente(ambienteEnum);
		} catch (IllegalStateException e) {
			throw e;
		}

		List<String> departamentos = new ArrayList<>();
		departamentos = departamentoDAO.recuperar();
		for (String departamento : departamentos) {
			List<String> celulas = celulaDAO.recupear(departamento);
			for (String celula : celulas) {
				List<Aplicacao> aplicacoes = aplicacaoDAO.recupear(celula);
				for (Aplicacao aplicacao : aplicacoes) {
					aplicacaoCLI.recuperarServer(aplicacao);
					if (aplicacao.getServer().getStatus().equals("running")) {
						aplicacao.getServer().setAtivo(true);
						jvmServices.getServerInformations(aplicacao.getServer());
						aplicacao.getServer().setJvm(jvmServices.getJvmInformations(aplicacao.getServer()));
						dsInformations(data.getDataSource(aplicacao.getServer()),aplicacao.getServer());
					} else {
						aplicacao.getServer().setAtivo(false);
					}
					aplicacoesFinalizadas.add(aplicacao);
				}
			}
		}
		ambiente.setAplicacaos(aplicacoesFinalizadas);
	}

	private void dsInformations(List<Datasource> listaDatasources,Server server) throws IllegalArgumentException {
		String cmdDS = "/host=" + server.getHost() + "/server=" + server.getNome() + "/subsystem=datasources/";

		for (Datasource datasource : listaDatasources) {
			datasource.setMaxCount(service.readAttribute(
					cmdDS + datasource.getTipo() + "=" + datasource.getNome() + ":read-attribute(name=max-pool-size)"));
			datasource.setActiveCount(service.readAttribute(cmdDS + datasource.getTipo() + "=" + datasource.getNome()
					+ "/statistics=pool:read-attribute(name=ActiveCount)"));
			datasource.setMaxInUseCount(service.readAttribute(cmdDS + datasource.getTipo() + "=" + datasource.getNome()
					+ "/statistics=pool:read-attribute(name=MaxUsedCount)"));
		}
		server.setDatasources(listaDatasources);
	}
}
