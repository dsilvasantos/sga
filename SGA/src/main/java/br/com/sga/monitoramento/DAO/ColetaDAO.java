package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.Query;

import br.com.sga.monitoramento.model.Coleta;

public class ColetaDAO  extends EntityManagerSingleton{

	public void persist(Coleta Coleta) {
		try {
			transaction.begin();
			entityManager.persist(Coleta);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void merge(Coleta Coleta) {
	
		try {
			transaction.begin();
			entityManager.merge(Coleta);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void remove(Coleta Coleta) {
		try {
			transaction.begin();
			Coleta = entityManager.find(Coleta.class, Coleta.getId());
			entityManager.remove(Coleta);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}


	public List<String> recupear(String departamento){
		try {
			Query query = entityManager.createNativeQuery("Select coleta.nome from departamento,coleta where coleta.ID_DEPARTAMENTO = "
					+ "departamento.id and departamento.nome=?1");
			query.setParameter(1, departamento);
			List<String> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}
	
}
