package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Departamento;

public class DepartamentoDAO {
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

	public void persist(Departamento Departamento) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Departamento);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public void merge(Departamento Departamento) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Departamento);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public void remove(Departamento Departamento) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Departamento = entityManager.find(Departamento.class, Departamento.getId());
			entityManager.remove(Departamento);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public List<String> recuperar() {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery("Select nome from departamento");
			List<String> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
		return null;
	}
}
