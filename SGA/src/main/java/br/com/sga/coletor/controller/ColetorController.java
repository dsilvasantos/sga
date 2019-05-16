package br.com.sga.coletor.controller;

import java.util.Timer;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.logging.Logger;

import br.com.sga.coletor.service.ColetorService;
import br.com.sga.monitoramento.enumeration.TiposUsuarios;
import br.com.sga.services.SessionContext;



@ManagedBean(eager=true)
@ApplicationScoped
public class ColetorController {
	
	private static final Logger LOGGER = Logger.getLogger(ColetorController.class);
	private static Timer t;
	private static boolean start;
	
	
	public ColetorController() {
	  String ativar = "true";
	  if(ativar.equals("true")){
		  LOGGER.info("Iniciando monitoramento dos serviços do JBoss.");
		  start = true;
		  startTimer();
	  }else{
		  LOGGER.info("Coletor de alertas do JBoss está desativado.");
		  start = false;
	  }
	}
	
	public static void startTimer(){
		LOGGER.info("Coletor Jboss ativado.");
		start=true;
		//Milissegundo
		int intervaloColeta = 120000;
		 t = new Timer();
		  ColetorService collector = new ColetorService();
		  t.scheduleAtFixedRate(collector, 0, intervaloColeta);
	}
	
	public static void cancelTimer(){
		LOGGER.info("Coletor Jboss desabilitado pela interface web do Monitor JBoss.");
		start = false;
		t.cancel();
	}


	public static Timer getT() {
		return t;
	}


	public static void setT(Timer t) {
		ColetorController.t = t;
	}

	public static boolean isStart() {
		return start;
	}

	public static void setStart(boolean start) {
		ColetorController.start = start;
	}



	
	
	
}
