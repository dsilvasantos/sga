package br.com.sga.services;
import javax.ws.rs.Path;

import br.com.sga.monitoramento.DAO.CelulaDAO;
import br.com.sga.monitoramento.model.Celula;

@Path("/teste")
public class teste {

	@Path("/inicio")
	public void inicio() {
		Celula celulamodel = new Celula();
		celulamodel.setDescricao("aaaaaaaaaa");
		celulamodel.setNome("aaaa");
		
		CelulaDAO.getInstance().persist(celulamodel);
	}
}
