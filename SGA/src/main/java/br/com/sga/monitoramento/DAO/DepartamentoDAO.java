package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Departamento;

public class DepartamentoDAO {
	private static DepartamentoDAO instance;
	protected EntityManager entityManager;

	public static DepartamentoDAO getInstance() {
		if (instance == null) {
			instance = new DepartamentoDAO();
		}

		return instance;
	}

	private DepartamentoDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SGA");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public Departamento getById(final int id) {
		return entityManager.find(Departamento.class, id);
	}

	public void persist(Departamento Departamento) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Departamento);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Departamento Departamento) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Departamento);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Departamento Departamento) {
		try {
			entityManager.getTransaction().begin();
			Departamento = entityManager.find(Departamento.class, Departamento.getId());
			entityManager.remove(Departamento);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Departamento Departamento = getById(id);
			remove(Departamento);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public List<String> recuperar(){
		Query query = entityManager.createNativeQuery("Select nome from departamento");
		List<String> result = query.getResultList();
		return result;
	}
}
