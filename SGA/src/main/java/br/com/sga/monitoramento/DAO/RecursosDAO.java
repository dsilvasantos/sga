package br.com.sga.monitoramento.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Recursos;

public class RecursosDAO {

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

	public void persist(Recursos recursos) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(recursos);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public void merge(Recursos recursos) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(recursos);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public void remove(Recursos recursos) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			recursos = entityManager.find(Recursos.class, recursos.getId());
			entityManager.remove(recursos);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public Recursos recupear(int recurso) {
		EntityManager entityManager = getEntityManager();
		try {
			Query query = entityManager.createNativeQuery("Select * from recursos where id = ?1", Recursos.class);
			query.setParameter(1, recurso);
			if (!query.getResultList().isEmpty()) {
				Recursos recursos = (Recursos) query.getSingleResult();
				return recursos;
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
		return null;
	}
}
