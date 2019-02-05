package br.com.sga.services;

import java.util.ArrayList;
import java.util.List;

import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Datasource;

public class DatasourceServices {

	private ServicosCLI service = new ServicosCLI();

	/**
	 * Recupera todos os Datasources e XA Datasource do aplicacao selecionado
	 * 
	 * @param aplicacao
	 * @return
	 */
	public boolean testConnect(String cmd, Aplicacao aplicacao) throws IllegalStateException {

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

	public String getProfile(String cmd, Aplicacao aplicacao) {
		
		try {
			return service.readAttribute(cmd);

		} catch (Exception e) {
			System.out.println("Erro ao carregar as propriedades !");
			return null;

		} 
	}

	public List<String> getProperty(String cmd, Aplicacao aplicacao, String perfil) {

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
			if (property.contains("sutec." + profile + ".ds")) {
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

	public List<Datasource> getDataSource(Aplicacao aplicacao) {
		List<Datasource> dataSources = new ArrayList<Datasource>();

		List<String> lsDataSource = new ArrayList<String>();
		List<String> lsDataSourceXA = new ArrayList<String>();

		lsDataSource = getDataSources(aplicacao);

		if (lsDataSource.get(0).isEmpty()) {

		} else {
			for (String datasource : lsDataSource) {
				Datasource data = new Datasource();
				data.setNome(datasource);
				data.setTipo("data-source");
				dataSources.add(data);
			}
		}

		lsDataSourceXA = getDataSourcesXA(aplicacao);
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

	public List<Datasource> globais(List<Datasource> dataSources, Aplicacao aplicacao) {
		List<Datasource> dataSourcesGlobais = new ArrayList<Datasource>();
		Boolean retorno;
		for (Datasource datasource : dataSources) {
			retorno = getDataSourcesGlobais(datasource, aplicacao);
			if (retorno == true) {
				datasource.setRestricao("Global");
				dataSourcesGlobais.add(datasource);
			} else {

			}
		}
		return dataSourcesGlobais;
	}

	public List<String> getDataSources(Aplicacao aplicacao) {

		String cmd = "ls /subsystem=datasources/data-source";
		try {
			return service.readList(cmd);

		} catch (Exception e) {
			System.out.println("Erro ao carregar as propriedades !");
			return null;

		} 
	}

	public List<String> getDataSourcesXA(Aplicacao aplicacao) {
		String cmd = "ls /host=" + aplicacao.getHost() + "/server=" + aplicacao.getNome()
				+ "/subsystem=datasources/xa-data-source";

		try {
			return service.readList(cmd);

		} catch (Exception e) {
			System.out.println("Erro ao carregar as propriedades !");
			return null;

		}
	}

	public boolean getDataSourcesGlobais(Datasource data, Aplicacao aplicacao) {

		String cmd = "/host=" + aplicacao.getHost() + "/server=" + aplicacao.getNome() + "/subsystem=datasources/"
				+ data.getTipo() + "=" + data.getNome() + ":read-attribute(name=user-name)";

		try {
			String user = service.readAttribute(cmd);
			if (user.contains("sutec")) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			System.out.println("Erro ao carregar as propriedades !");
			return false;

		}
	}
}
