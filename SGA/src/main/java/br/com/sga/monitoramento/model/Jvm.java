package br.com.sga.monitoramento.model;

import java.util.List;

public class Jvm {
	
	private String heapUsage;
	private String percentUse;
	private String maxHeap;
	private List<GC> infoGC;
	
	
	
	
	public Jvm() {
		// TODO Auto-generated constructor stub
	}


	public String getHeapUsage() {
		return heapUsage;
	}


	public void setHeapUsage(String heapUsage) {
		this.heapUsage = heapUsage;
	}


	public String getMaxHeap() {
		return maxHeap;
	}


	public void setMaxHeap(String maxHeap) {
		this.maxHeap = maxHeap;
	}



	public String getPercentUse() {
		return percentUse;
	}


	public void setPercentUse(String percentUse) {
		this.percentUse = percentUse;
	}


	public List<GC> getInfoGC() {
		return infoGC;
	}


	public void setInfoGC(List<GC> infoGC) {
		this.infoGC = infoGC;
	}
	
	
	
}
