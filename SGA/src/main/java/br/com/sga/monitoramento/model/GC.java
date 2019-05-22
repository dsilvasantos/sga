package br.com.sga.monitoramento.model;

public class GC {
	
	private String tipo;
	private String nomeScript;
	private String tempo;
	private String count;

	
	public GC() {
		// TODO Auto-generated constructor stub
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getNomeScript() {
		return nomeScript;
	}


	public void setNomeScript(String nomeScript) {
		this.nomeScript = nomeScript;
	}


	public String getTempo() {
		return convertTime(tempo);
	}


	public void setTempo(String tempo) {
		this.tempo = tempo;
	}


	public String getCount() {
		return count;
	}


	public void setCount(String count) {
		this.count = count;
	}
	

	private String convertTime(String time){
		if(Long.parseLong(time)>3600000){
			long horas = Long.parseLong(time)/3600000;
			return horas+" Horas";
		}else if(Long.parseLong(time)>=60000 && Long.parseLong(time)<3600000){
			long minutos = Long.parseLong(time)/60000;
			return minutos+" Minutos";
		}else if(Long.parseLong(time)>=1000 && Long.parseLong(time)<60000){
			long segundos = Long.parseLong(time)/1000;
			return segundos+" Segundos";
		}else{
			return time+" Ms";
		}
	}
}
