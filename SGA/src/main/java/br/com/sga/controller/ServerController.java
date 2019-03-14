package br.com.sga.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.MeterGaugeChartModel;

import br.com.sga.monitoramento.DAO.AplicacaoDAO;
import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Log;
import br.com.sga.monitoramento.model.Server;
import br.com.sga.services.AmbienteServices;
import br.com.sga.services.AplicacaoCLI;
import br.com.sga.services.DatasourceServices;
import br.com.sga.services.JvmServices;
import br.com.sga.services.LogService;

@ManagedBean(name = "server")
@ViewScoped
public class ServerController {

	private Server serverInfo;

	private MeterGaugeChartModel chart = new MeterGaugeChartModel();

	private List<Log> logs;

	private String msg = "Não foi possível executar esta operação. Clique em OK e tente novamente.";

	private String justificativa;
	
	private AplicacaoCLI aplicacaoCLI = new AplicacaoCLI();
	
	private DepartamentoDAO departamentoDAO = new DepartamentoDAO();
	private CelulaDAO celulaDAO = new CelulaDAO();
	private AplicacaoDAO aplicacaoDAO = new AplicacaoDAO();
	
	public TreeNode createAplicacoes() {
		AmbienteServices ambiente = new AmbienteServices();
		ambiente.selecionarAmbiente(1);
		Server rootServer = new Server();
		rootServer.setNome("Aplicações");
		rootServer.setStatus("../imagens/vazio.png");
		TreeNode root = new DefaultTreeNode(rootServer,null);
		
		List<String> departamentos = new ArrayList<>();
		departamentos = departamentoDAO.recuperar();
		for(String departamento:departamentos) {
			Server departamentoServer = new Server();
			departamentoServer.setNome(departamento);
			departamentoServer.setStatus("../imagens/vazio.png");
			TreeNode rootDepartamento = new DefaultTreeNode(departamentoServer,root);
			List<String> celulas = celulaDAO.recupear(departamento);
			for(String celula : celulas) {
				Server celulaServer = new Server();
				celulaServer.setNome(celula);
				celulaServer.setStatus("../imagens/vazio.png");
				TreeNode rootCelula = new DefaultTreeNode(celulaServer,rootDepartamento);
				List<Aplicacao> aplicacoes = aplicacaoDAO.recupear(celula);
				for(Aplicacao aplicacao : aplicacoes) {
					aplicacaoCLI.recuperarServer(aplicacao);
					Server serverAplicacao = new Server();
					serverAplicacao.setNome(aplicacao.getNome());
					serverAplicacao.setStatus(aplicacao.getServer().getImagem());
					new DefaultTreeNode("aplicação",serverAplicacao,rootCelula);
				}
			}
		}
		return root;
	}
	
	public TreeNode createCheckBoxAplicacoes() {
		
		Server rootServer = new Server();
		rootServer.setNome("Aplicações");
		rootServer.setStatus("-");
		TreeNode root = new CheckboxTreeNode(rootServer,null);
		
		List<String> departamentos = new ArrayList<>();
		departamentos = departamentoDAO.recuperar();
		for(String departamento:departamentos) {
			Server departamentoServer = new Server();
			departamentoServer.setNome(departamento);
			departamentoServer.setStatus("-");
			TreeNode rootDepartamento = new CheckboxTreeNode(departamentoServer,root);
			List<String> celulas = celulaDAO.recupear(departamento);
			for(String celula : celulas) {
				Server celulaServer = new Server();
				celulaServer.setNome(celula);
				celulaServer.setStatus("-");
				TreeNode rootCelula = new CheckboxTreeNode(celulaServer,rootDepartamento);
				List<Aplicacao> aplicacoes = aplicacaoDAO.recupear(celula);
				for(Aplicacao aplicacao : aplicacoes) {
					aplicacaoCLI.recuperarServer(aplicacao);
					new CheckboxTreeNode("aplicação",aplicacao,rootCelula);
				}
			}
		}
		return root;
	}
	public void recuperarInformacoesServer(Server server) {
		JvmServices jvmServices = new JvmServices();
		jvmServices.getServerInformations(server);
		serverInfo = server;
	}

	public void recuperarJvmInformacoes(Server server) {
		serverInfo = server;
		JvmServices jvmServices = new JvmServices();
		serverInfo.setJvm(jvmServices.getJvmInformations(server));
		chart = generateChart();
		chart.setTitle("Uso da memória Heap");
		chart.setGaugeLabel("MB");
		chart.setSeriesColors("008000,0000FF,FFFF00,FF0000");

	}

	private MeterGaugeChartModel generateChart() {
		List<Number> intervalos = new ArrayList<Number>();
		int fracao = Integer.parseInt(serverInfo.getJvm().getMaxHeap()) / 4;
		intervalos.add(fracao);
		intervalos.add(fracao * 2);
		intervalos.add(fracao * 3);
		intervalos.add(fracao * 4);

		return new MeterGaugeChartModel(Integer.parseInt(serverInfo.getJvm().getHeapUsage()), intervalos);
	}

	public void dataBase(Server server) {
		try {
			DatasourceServices jvmServices = new DatasourceServices();
			jvmServices.getDataSourcesValidos(server);
			serverInfo = server;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateLog(Server server, int linhas) {
		serverInfo = server;
		LogService logService = new LogService();
		logs = logService.getLogs(server, linhas);
	}

	public void convertCSV(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getColumnIndex()) {
				case 0:
					if (cell.getStringCellValue().contains("INFO") && !cell.getStringCellValue().equals("Severidade")) {
						cell.setCellValue("INFO");
					} else if (cell.getStringCellValue().contains("WARN")
							&& !cell.getStringCellValue().equals("Severidade")) {
						cell.setCellValue("WARN");
					} else if (cell.getStringCellValue().contains("ERROR")
							&& !cell.getStringCellValue().equals("Severidade")) {
						cell.setCellValue("ERROR");
					} else if (cell.getStringCellValue().contains("DEBUG")
							&& !cell.getStringCellValue().equals("Severidade")) {
						cell.setCellValue("DEBUG");
					}
					break;
				default:
					break;
				}
			}
		}
	}

	public void startJboss(Server server) {
		int cont = 0;
		String user = null;
		AplicacaoCLI aplicacaoCli = new AplicacaoCLI();
		System.out.println(
				"Usuário: " + user + " solicitou START do server:" + server.getNome() + " do host " + server.getHost());
		try {
			boolean result = aplicacaoCli.startAplicacao(server);
			if (result) {
				while (true) {
					if (aplicacaoCli.statusAplicacao(server).equals("STARTED")) {
						System.out.println("Server " + server.getNome() + " inicializado com sucesso.");
						msg = "Server " + server.getNome()+ " inicializado com sucesso.";
						break;
					}
					System.out.println("Server " + server.getNome()
							+ " ainda está iniciando. Aguardando 2 segundos para a nova consulta.");
					Thread.sleep(2000);
					cont++;
					if (cont == 30) {
						System.out.println("Timeout esgotado para inicialização do server ");
						msg = "Não foi possível identificar o início do server " + server.getNome() + " do Host "
								+ server.getHost() + ". Verifique o LOG da aplicação.";
						break;
					}
				}
			} else {
				System.out.println("Não foi possível executar o comando de START do server " + server.getNome()
						+ " Provavelmente o PID está ativo no Sistema Operacional.");
				msg = "Erro ao iniciar o server " + server.getNome() + " do Host " + server.getHost()
						+ ". Verifique se o PID ainda está ativo no Sistema Operacional.";
			}

		} catch (Exception e) {
			System.out.println("Falha na inicialização do server.");
			e.printStackTrace();
			msg = "Erro ao iniciar o server " + server.getNome() + " do Host " + server.getHost()
					+ ". Por favor, verifique se o PID ainda está ativo.";
		}
	}
	
	
	public void stop(Server server){
		serverInfo = server;
	}

	public void stopJboss(Server server) {
		AplicacaoCLI aplicacaoCli = new AplicacaoCLI();
		int cont = 0;
		String user = null;
		System.out.println("Usuário: " + user + " solicitou STOP do server: " + server.getNome() + " do host "
				+ server.getHost() + " com a justificativa: [" + justificativa + "].");

		try {

			boolean resultDestroy = aplicacaoCli.destroyAplicacao(server);
			if (resultDestroy) {
				while (true) {
					if (aplicacaoCli.statusAplicacaoStop(server).equals("STOPPED")
							|| aplicacaoCli.statusAplicacaoStop(server).equals("DISABLED")) {
						System.out.println("Server " + server.getNome() + " finalizado com sucesso pelo DESTROY.");
						msg = "Server " + server.getNome() + " finalizado com sucesso.";
						break;
					}
					System.out.println("Server " + server.getNome()
							+ " ainda está sendo encerrado pelo DESTROY. Aguardando 2 segundos para a nova consulta.");
					Thread.sleep(2000);
					cont++;

					// Timeout do stop gracefull. Iniciando parada pelo KILL.
					if (cont == 5) {
						System.out.println("Timeout esgotado para finalização do server " + server.getNome()
								+ " utilizando o DESTROY.");
						int aux = 0;
						System.out.println("Iniciando parada do server " + server.getNome() + " pelo KILL.");
						boolean resultKill = aplicacaoCli.killAplicacao(server);
						if (resultKill) {
							while (aux < 5) {
								if (aplicacaoCli.statusAplicacaoStop(server).equals("STOPPED")
										|| aplicacaoCli.statusAplicacaoStop(server).equals("DISABLED")) {
									System.out.println(
											"Server " + server.getNome() + " finalizado com sucesso pelo KILL.");
									msg = "Server " + server.getNome()
											+ " finalizado com sucesso utilizando o processo KILL.";
									break;
								}
								System.out.println("Server " + server.getNome()
										+ " ainda está sendo encerrado pelo KILL. Aguardando 2 segundos para a nova consulta.");
								Thread.sleep(2000);
								aux++;
							}
						} else {
							System.out.println("Falha ao executar o comando KILL para o server " + server.getNome());
							msg = "Não foi possível executar o comando KILL no server " + server.getNome()
									+ ". Verifique o LOG deste server.";
						}
					}
				}
			} else {
				System.out.println("Falha ao executar o comando DESTROY para o server " + server.getNome());
				msg = "Erro ao parar o server " + server.getNome() + " do Host " + server.getHost()
						+ ". Verifique se o server já se encontra parado.";
			}

		} catch (Exception e) {
			System.out.println("Exceção ao parar o server  " + server.getNome());
			e.printStackTrace();
			msg = "Erro ao parar o server " + server.getNome() + " do Host " + server.getHost()
					+ ". Por favor, verifique se o o server já está finalizado.";
		}
	}

	public Server getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(Server serverInfo) {
		this.serverInfo = serverInfo;
	}

	public MeterGaugeChartModel getChart() {
		return chart;
	}

	public void setChart(MeterGaugeChartModel chart) {
		this.chart = chart;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

}
