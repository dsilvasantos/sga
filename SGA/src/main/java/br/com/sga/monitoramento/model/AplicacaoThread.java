package br.com.sga.monitoramento.model;

import java.util.concurrent.TimeUnit;


public class AplicacaoThread implements Comparable<AplicacaoThread>{
	private long id;
	private String name;
	private String state;
	private long blocked_time;
	private long waited_time;
	private String lock_name;
	private String stackTrace;
	
	private long cpu_time;
	
	public AplicacaoThread() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getBlocked_time() {
		return blocked_time;
	}

	public void setBlocked_time(long blocked_time) {
		this.blocked_time = blocked_time;
	}

	public long getWaited_time() {
		return waited_time;
	}

	public void setWaited_time(long waited_time) {
		this.waited_time = waited_time;
	}

	public String getLock_name() {
		return lock_name;
	}

	public void setLock_name(String lock_name) {
		this.lock_name = lock_name;
	}

	public long getCpu_time() {
		return cpu_time;
	}

	public void setCpu_time(long cpu_time) {
		this.cpu_time = cpu_time;
	}
	
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}


	@Override
	public int compareTo(AplicacaoThread arg0) {
		if(this.cpu_time < arg0.getCpu_time()){
			return -1;
		}
		if(this.cpu_time > arg0.getCpu_time()){
			return 1;
		}
		return 0;
	}
	
	public String getTempo(long tempo){
		long time = TimeUnit.MILLISECONDS.convert(tempo, TimeUnit.NANOSECONDS);
		
		if(time<1000){
			return time+" Millisegundos";
		}
		else if(time>1000 && time<60000){
			return time/1000 +" Segundos";
		}
		else if(time>60000 && time<3600000){
			long minutos = time/1000/60;
			long segundos = time%1000%60;
			return minutos+":"+segundos+" Minutos";
		}
		else if(time>3600000 && time<86400000){
			long hora = time/1000/60/60;
			long minutos = time%1000%60%60;
			return hora+":"+minutos+" Horas";
		}
		else{
			long dias = time/1000/60/60/24;
			long horas = time%1000%60%60%24;
			return  dias+" dias e "+horas+" horas";
		}
	}
			
	
}
