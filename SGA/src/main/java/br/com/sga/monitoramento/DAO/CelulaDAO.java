package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Celula;

public class CelulaDAO {

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = null;
		EntityManager entityManager = null;
	
			// Obtém o factory a partir da unidade de persistência.
			factory = Persistence.createEntityManagerFactory("SGA");
			// Cria um entity manager.
			entityManager = factory.createEntityManager();
			// Fecha o factory para liberar os recursos utilizado.
			return entityManager;
		
	}

	public void persist(Celula Celula) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Celula);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void merge(Celula Celula) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Celula);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void remove(Celula Celula) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Celula = entityManager.find(Celula.class, Celula.getId());
			entityManager.remove(Celula);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}


	public List<String> recupear(String departamento){
		EntityManager entityManager = getEntityManager();
		try {
			Query query = entityManager.createNativeQuery("Select celula.nome from departamento,celula where celula.ID_DEPARTAMENTO = "
					+ "departamento.id and departamento.nome=?1");
			query.setParameter(1, departamento);
			List<String> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
		return null;
	}
}
