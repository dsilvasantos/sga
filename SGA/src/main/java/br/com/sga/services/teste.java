package br.com.sga.services;
import javax.ws.rs.Path;

@Path("/teste")
public class teste {

	@Path("/inicio")
	public void inicio() {
		System.out.println("Teste");
		AmbienteServices amb = new AmbienteServices();
		amb.selecionarAmbiente(1);
		
		DepartamentoCLIServices departamento = new DepartamentoCLIServices();
		departamento.verficarDepartamento("desenvolvimento");
		
		CelulaCLIServices celula =new CelulaCLIServices();
		celula.verficarCelula("selic");
	}
}
