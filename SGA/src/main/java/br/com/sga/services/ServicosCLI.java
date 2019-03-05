package br.com.sga.services;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.jboss.as.cli.scriptsupport.CLI;
import org.jboss.dmr.ModelNode;

public class ServicosCLI {
	
	private static CLI cli;

	public ServicosCLI() {
		cli = ConexaoCLI.getCli();
	}

	public String executeCommand(String cmd) {
		String result = cli.cmd(cmd).getResponse().get("outcome").toString();
		return result;
	}
	
	public List<ModelNode> executeCommandList(String cmd){
		return cli.cmd(cmd).getResponse().get("result").asList();
	}
	
	public String executeCommandListValue(String cmd,String value) {
		return cli.cmd(cmd).getResponse().get("result").get(value).asString();
	}

	public String readValor(String cmd) {
		String attribute = cli.cmd(cmd).getResponse().toString();
		return attribute;
	}

	public String readAttribute(String cmd) {
		String attribute = cli.cmd(cmd).getResponse().get("result").toString();
		attribute = attribute.replace("\"", "");
		return attribute;
	}

	public String[] newReadList(String cmd) {
		String[] list = cli.cmd(cmd).getResponse().get("result").asString().split(",");
		for (int i = 0; i < list.length; i++) {
			list[i] = list[i].replace("\"", "");
			list[i] = list[i].replace("[", "");
			list[i] = list[i].replace("]", "");
		}
		return list;
	}

	public List<String> readList(String cmd) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);
		cli.cmd(cmd);
		System.out.flush();
		System.setOut(old);
		String listaString = baos.toString();
		String valores[] = listaString.split("\n");
		valores = removeValues(valores);

		List<String> lista = new ArrayList<String>();

		for (int i = 0; i < valores.length; i++) {
			lista.add(valores[i]);
		}
		return lista;
	}

	public List<String> readListST(String cmd) {

		String attribute = cli.cmd(cmd).getResponse().get("result").get("value").toString();

		attribute = attribute.replace("\"", "");

		List<String> lista = new ArrayList<String>();
		lista.add(attribute);
		return lista;
	}

	private String[] removeValues(String[] text) {
		String text2[] = new String[text.length];
		for (int i = 0; i < text.length; i++) {
			text2[i] = text[i].replace("\r", "");
		}
		return text2;
	}

	public String readResource(String cmd) {
		String resource = cli.cmd(cmd).getResponse().get("result").toString();
		return resource;
	}

	public static CLI getCli() {
		return cli;
	}
	
	
}
