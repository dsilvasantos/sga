package br.com.sga.monitoramento.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

public class Jvm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "heapUsage",nullable=false)
	private String heapUsage;
	
	@Column(name = "percentUse",nullable=false)
	private String percentUse;
	
	@Column(name = "maxHeap",nullable=false)
	private String maxHeap;
	
	@Transient
	private List<GC> infoGC;
	
	
	
	
	public Jvm() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
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
	
	public String getPercentUseHeap() {
		Long v1 = Long.parseLong(this.heapUsage.trim());
		Long v2 = Long.parseLong(this.maxHeap.trim());
		Long total = v1*100/v2;
		return total+"";
	}
	
	
}
