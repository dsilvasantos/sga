package br.com.sga.monitoramento.model;

import br.com.sga.monitoramento.enumeration.LogLevel;

public class Log {

	private LogLevel level;
	private String texto;
	private String color;

	public Log() {
		// TODO Auto-generated constructor stub
	}

	public Log(LogLevel level, String texto, String color) {
		super();
		this.level = level;
		this.texto = texto;
		this.color = color;
	}

	public LogLevel getLevel() {
		return level;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
