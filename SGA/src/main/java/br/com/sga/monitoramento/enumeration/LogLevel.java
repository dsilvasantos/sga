package br.com.sga.monitoramento.enumeration;

public enum LogLevel {

	ALL("ALL"),
	FINEST("FINEST"),
	FINER("FINER"),
	TRACE("TRACE"),
	DEBUG("DEBUG"),
	FINE("FINE"),
	CONFIG("CONFIG"),
	INFO("INFO"),
	WARN("WARN"),
	WARNING("WARNING"),
	ERROR("ERROR"),
	SEVERE("SEVERE"),
	FATAL("FATAL"),
	ND("N/D");
	
	
	private final String level;

	public String getLevel() {
		return level;
	}

	private LogLevel(String level) {
		this.level = level;
	}
}
