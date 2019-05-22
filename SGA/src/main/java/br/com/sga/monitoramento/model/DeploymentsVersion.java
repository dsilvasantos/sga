package br.com.sga.monitoramento.model;

public class DeploymentsVersion {
	
	private String nomeAplicacao;
	private String aliasAplicacao;
	private String dataBuild;
	private String versaoBuild;
	private String ambiente;
	private String contextoRaiz;
	private String profile;
	private String urlRest;
	
	public DeploymentsVersion() {
		// TODO Auto-generated constructor stub
	}
	
	
	public DeploymentsVersion(String nomeAplicacao) {
		super();
		this.nomeAplicacao = nomeAplicacao;
	}
	
	public DeploymentsVersion(String nomeAplicacao,String ambiente,String contextoRaiz, String profile) {
		super();
		this.nomeAplicacao = nomeAplicacao;
		this.ambiente = ambiente;
		this.contextoRaiz = contextoRaiz;
		this.profile = profile;
	}
	
	public DeploymentsVersion(String ambiente,String contextoRaiz, String profile) {
		super();
		this.ambiente = ambiente;
		this.contextoRaiz = contextoRaiz;
		this.profile = profile;
	}

	

	public String getNomeAplicacao() {
		return nomeAplicacao;
	}


	public void setNomeAplicacao(String nomeAplicacao) {
		this.nomeAplicacao = nomeAplicacao;
	}


	public String getDataBuild() {
		return dataBuild;
	}


	public void setDataBuild(String dataBuild) {
		this.dataBuild = dataBuild;
	}


	public String getVersaoBuild() {
		return versaoBuild;
	}


	public void setVersaoBuild(String versaoBuild) {
		this.versaoBuild = versaoBuild;
	}


	public String getAmbiente() {
		return ambiente;
	}


	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}


	public String getContextoRaiz() {
		return contextoRaiz;
	}


	public void setContextoRaiz(String contextoRaiz) {
		this.contextoRaiz = contextoRaiz;
	}
	
	public String getProfile() {
		return profile;
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public String getUrlRest() {
		return urlRest;
	}
	
	public void setUrlRest(String urlRest) {
		this.urlRest = urlRest;
	}


	public String getAliasAplicacao() {
		return aliasAplicacao;
	}


	public void setAliasAplicacao(String aliasAplicacao) {
		this.aliasAplicacao = aliasAplicacao;
	}
	
}
