package br.com.sga.monitoramento.model;

public class Datasource {

	private String nome;
	private String jndi;
	private String driver;
	private String conexoesDisponiveis;
	private String maxConnectionUsed;
	private String maxPool;
	private String inUseConnections;
	private String tipo;
	private String status;
	private boolean valid;
	private String nomeServer;
	private String jta;
	private String restricao;
	private String maxCount;
	private String activeCount;
	private String maxInUseCount;

	public Datasource() {
		// TODO Auto-generated constructor stub
	}

	public String getRestricao() {
		return restricao;
	}

	public void setRestricao(String restricao) {
		this.restricao = restricao;
	}

	public Datasource(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getJndi() {
		return jndi;
	}

	public void setJndi(String jndi) {
		this.jndi = jndi;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getConexoesDisponiveis() {
		return conexoesDisponiveis;
	}

	public void setConexoesDisponiveis(String conexoesDisponiveis) {
		this.conexoesDisponiveis = conexoesDisponiveis;
	}

	public String getMaxPool() {
		return maxPool;
	}

	public void setMaxPool(String maxPool) {
		this.maxPool = maxPool;
	}

	public String getInUseConnections() {
		return inUseConnections;
	}

	public void setInUseConnections(String inUseConnections) {
		this.inUseConnections = inUseConnections;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMaxConnectionUsed() {
		return maxConnectionUsed;
	}

	public void setMaxConnectionUsed(String maxConnectionUsed) {
		this.maxConnectionUsed = maxConnectionUsed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getNomeServer() {
		return nomeServer;
	}

	public void setNomeServer(String nomeServer) {
		this.nomeServer = nomeServer;
	}

	public String getJta() {
		return jta;
	}

	public void setJta(String jta) {
		this.jta = jta;
	}

	public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public String getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(String activeCount) {
		this.activeCount = activeCount;
	}

	public String getMaxInUseCount() {
		return maxInUseCount;
	}

	public void setMaxInUseCount(String maxInUseCount) {
		this.maxInUseCount = maxInUseCount;
	}
	
	public String getPercentUsage(){
		Long v1 = Long.parseLong(this.activeCount.trim());
		Long v2 = Long.parseLong(this.maxCount.trim());
		Long total = v1*100/v2;
		return total+"";
	}

}
