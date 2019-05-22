package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.Query;

import br.com.sga.monitoramento.model.Celula;
import br.com.sga.monitoramento.model.Departamento;

public class CelulaDAO extends EntityManagerSingleton{



	public void persist(Celula Celula) {
		try {
			if(!transaction.isActive())
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
			if(!entityManager.getTransaction().isActive())
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
			if(!entityManager.getTransaction().isActive())
			//transaction.begin();
			entityManager.getTransaction().begin();
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
	
	@SuppressWarnings("unchecked")
	public List<Celula> listaCelula() {
		try {
			Query query = entityManager.createQuery("select d from Celula d");
			List<Celula> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public Celula retornaCelula(Integer idCelula) {
		Celula celula = null;
		try{
			if(!entityManager.getTransaction().isActive())
				entityManager.getTransaction().begin();
			celula = (Celula) entityManager.createQuery("select f from Celula f where f.id = :idCelula")
			.setParameter("idCelula", idCelula).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return celula;
	}

}
