package br.com.sga.services;

import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import br.com.sga.monitoramento.model.Datasource;

public class DataSourceCLI {
/*
	private static ServicosCLI service;

	public boolean testConnect(Data data) throws IllegalStateException {

		String cmd = "/subsystem=datasources/" + data.getTipo() + "=" + data.getNome() + ":test-connection-in-pool";
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

		} finally {
			cli.disconnect();
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
*/
}
