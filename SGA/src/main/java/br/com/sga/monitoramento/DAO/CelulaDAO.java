package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.Query;

import br.com.sga.monitoramento.model.Celula;

public class CelulaDAO extends EntityManagerSingleton{



	public void persist(Celula Celula) {
		try {
			transaction.begin();
			entityManager.persist(Celula);
			 transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void merge(Celula Celula) {
		try {
			transaction.begin();
			entityManager.merge(Celula);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void remove(Celula Celula) {
		try {
			transaction.begin();
			Celula = entityManager.find(Celula.class, Celula.getId());
			entityManager.remove(Celula);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}


	public List<String> recupear(String departamento){
		try {
			Query query = entityManager.createNativeQuery("Select celula.nome from departamento,celula where celula.ID_DEPARTAMENTO = "
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
