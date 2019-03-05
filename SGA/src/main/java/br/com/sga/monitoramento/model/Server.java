package br.com.sga.monitoramento.model;

import java.util.List;

import br.com.sga.monitoramento.enumeration.StatusServer;




public class Server{

	private String host;
	private String nome;
	private String status;
	private boolean valid;
	private String profile;
	private String serverGroup;
	private List<String> deployments;
	private int portOffset;
	private String jbossVersion;
	private String imagem;
	private String descricaoImagem;
	private List<DeploymentsVersion> version;
	private String ultimaAtualizacaoArquivoCSV;
	private String socketBindingGroup;
	private String serverUptime;
	private List<Datasource> datasources;
	private Jvm jvm;
	private int thread;
	private String maxMetaspace;
	private String usedMetaspace;
	private boolean ativo;
	
	public Server() {
		// TODO Auto-generated constructor stub
	}

	public Server(String nome, String status) {
		super();
		this.nome = nome;
		this.status = status;
		if(StatusServer.restartrequired.getValor().equals(status) | StatusServer.running.getValor().equals(status)){
				valid=true;
			}else{
				valid=false;
			}
		}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		
		 
		if(StatusServer.restartrequired.getValor().equals(status) | StatusServer.running.getValor().equals(status)){
			valid=true;
		}else{
			valid=false;
		}
		
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getServerGroup() {
		return serverGroup;
	}

	public void setServerGroup(String serverGroup) {
		this.serverGroup = serverGroup;
	}

	public List<String> getDeployments() {
		return deployments;
	}

	public void setDeployments(List<String> deployments) {
		this.deployments = deployments;
	}

	public int getPortOffset() {
		return portOffset;
	}

	public void setPortOffset(int portOffset) {
		this.portOffset = portOffset;
	}

	public String getJbossVersion() {
		return jbossVersion;
	}

	public void setJbossVersion(String jbossVersion) {
		this.jbossVersion = jbossVersion;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	
	public String getDescricaoImagem() {
		return descricaoImagem;
	}
	public void setDescricaoImagem(String descricaoImagem) {
		this.descricaoImagem = descricaoImagem;
	}

	public List<DeploymentsVersion> getVersion() {
		return version;
	}

	public void setVersion(List<DeploymentsVersion> version) {
		this.version = version;
	}

	public String getAtualizacao() {
		if(ultimaAtualizacaoArquivoCSV==null){
			return "";
		}
//		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(ultimaAtualizacaoArquivoCSV);
		return ultimaAtualizacaoArquivoCSV;
	}
	
	public void setUltimaAtualizacaoArquivoCSV(String ultimaAtualizacaoArquivoCSV) {
		this.ultimaAtualizacaoArquivoCSV = ultimaAtualizacaoArquivoCSV;
	}
	
	public String getSocketBindingGroup() {
		return socketBindingGroup;
	}
	public void setSocketBindingGroup(String socketBindingGroup) {
		this.socketBindingGroup = socketBindingGroup;
	}
	
	public String getServerUptime() {
		return serverUptime;
	}
	public void setServerUptime(String serverUptime) {
		this.serverUptime = serverUptime;
	}
	
	public List<Datasource> getDatasources() {
		return datasources;
	}
	
	public void setDatasources(List<Datasource> datasources) {
		this.datasources = datasources;
	}

	public Jvm getJvm() {
		return jvm;
	}

	public void setJvm(Jvm jvm) {
		this.jvm = jvm;
	}

	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMaxMetaspace() {
		return maxMetaspace;
	}

	public void setMaxMetaspace(String maxMetaspace) {
		this.maxMetaspace = maxMetaspace;
	}

	public String getUsedMetaspace() {
		return usedMetaspace;
	}

	public void setUsedMetaspace(String usedMetaspace) {
		this.usedMetaspace = usedMetaspace;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public String getMetaspaceUsedMB(){
		int used = Integer.parseInt(this.usedMetaspace);
		int usedMB = used/1024/1024;
		return usedMB+"";
	}
	
}
