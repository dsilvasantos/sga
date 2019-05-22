package br.com.sga.services;

import java.util.ArrayList;
import java.util.List;

import br.com.sga.monitoramento.model.Datasource;
import br.com.sga.monitoramento.model.Server;

public class DatasourceServices {

	private ServicosCLI service = new ServicosCLI();

	/**
	 * Recupera todos os Datasources e XA Datasource do aplicacao selecionado
	 * 
	 * @param aplicacao
	 * @return
	 */
	public boolean testConnect(String cmd, Server aplicacao) throws IllegalStateException {

		try {
			String resultado = service.readAttribute(cmd);
			if (resultado.equals("[true]")) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println("Erro ao carregar as propriedades !");
			return false;

		}
	}

	public List<Datasource> getFinalDataSource(List<String> propertys, List<Datasource> dataSources,
			List<Datasource> dataSourcesGlobais) {
		List<Datasource> datas = new ArrayList<Datasource>();
		for (String property : propertys) {
			for (Datasource data : dataSources) {
				String nome = data.getNome();
				nome = nome.toLowerCase();
				if (data.getNome().toLowerCase().equals(property)) {
					data.setRestricao("Restrito");
					datas.add(data);
					break;
				}
			}

		}
		for (Datasource data : dataSourcesGlobais) {
			datas.add(data);
		}
		return datas;
	}

	public String getProfile(String cmd, Server server) {

		try {
			return service.readAttribute(cmd);

		} catch (Exception e) {
			System.out.println("Erro ao carregar as propriedades !");
			return null;

		}
	}

	public List<String> getProperty(String cmd, String perfil) {

		try {
			List<String> propertys = new ArrayList<String>();
			propertys = service.readList(cmd);

			List<String> propertysDS = new ArrayList<String>();
			propertysDS = getPropertyDS(perfil, propertys);
			return propertysDS;

		} catch (Exception e) {
			System.out.println("Erro ao carregar as propriedades !");
			return null;
		}
	}

	public List<String> getPropertyDS(String profile, List<String> propertys) {
		List<String> ds = new ArrayList<String>();
		for (String property : propertys) {
			if (property.contains("global." + profile + ".ds")) {
				ds.add(getDS(property));
			}
		}
		return ds;
	}

	public String getDS(String txt) {

		String ds = txt.substring(txt.lastIndexOf("ds.") + 3);
		ds = minusculo(ds);
		return ds;
	}

	public String minusculo(String ds) {
		return ds.toLowerCase();
	}

	public List<Datasource> getDataSource(Server server) {
		List<Datasource> dataSources = new ArrayList<Datasource>();

		List<String> lsDataSource = new ArrayList<String>();
		List<String> lsDataSourceXA = new ArrayList<String>();

		lsDataSource = getDataSources(server);

		if (lsDataSource.get(0).isEmpty()) {

		} else {
			for (String datasource : lsDataSource) {
				Datasource data = new Datasource();
				data.setNome(datasource);
				data.setTipo("data-source");
				dataSources.add(data);
			}
		}

		lsDataSourceXA = getDataSourcesXA(server);
		if (lsDataSourceXA.get(0).isEmpty()) {

		} else {
			for (String datasource : lsDataSourceXA) {
				Datasource data = new Datasource();
				data.setNome(datasource);
				data.setTipo("xa-data-source");
				dataSources.add(data);
			}
		}
		return dataSources;
	}

	public List<Datasource> globais(List<Datasource> dataSources, Server server) {
		List<Datasource> dataSourcesGlobais = new ArrayList<Datasource>();
		Boolean retorno;
		for (Datasource datasource : dataSources) {
			retorno = getDataSourcesGlobais(datasource, server);
			if (retorno == true) {
				datasource.setRestricao("Global");
				dataSourcesGlobais.add(datasource);
			} else {

			}
		}
		return dataSourcesGlobais;
	}

	public List<String> getDataSources(Server server) {

		String cmd = "ls /host=" + server.getHost() + "/server=" + server.getNome()
				+ "/subsystem=datasources/data-source";
		try {
			return service.readList(cmd);

		} catch (Exception e) {
			System.out.println("Erro ao carregar as propriedades !");
			return null;

		}
	}

	public List<String> getDataSourcesXA(Server server) {
		String cmd = "ls /host=" + server.getHost() + "/server=" + server.getNome()
				+ "/subsystem=datasources/xa-data-source";

		try {
			return service.readList(cmd);

		} catch (Exception e) {
			System.out.println("Erro ao carregar as propriedades !");
			return null;

		}
	}

	public boolean getDataSourcesGlobais(Datasource data, Server server) {

		String cmd = "/host=" + server.getHost() + "/server=" + server.getNome() + "/subsystem=datasources/"
				+ data.getTipo() + "=" + data.getNome() + ":read-attribute(name=user-name)";

		try {
			String user = service.readAttribute(cmd);
			if (user.contains("global")) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			System.out.println("Erro ao carregar as propriedades !");
			return false;

		}
	}

	public void getDataSourcesValidos(Server server) {
		List<Datasource> datasourceServer = new ArrayList<Datasource>();
		List<Datasource> lista = getDataSource(server);
		for (Datasource data : lista) {
			String cmd = "/host=" + server.getHost() + "/server=" + server.getNome() + "/subsystem=datasources/"
					+ data.getTipo() + "=" + data.getNome() + ":test-connection-in-pool";
			boolean resultado = testConnect(cmd, server);
			if (resultado == true) {
				data.setValid(true);
				data.setStatus("OK");
			} else {
				data.setValid(false);
				data.setStatus("NOK");
			}
			datasourceServer.add(data);
			server.setDatasources(datasourceServer);
		}
	}
}
