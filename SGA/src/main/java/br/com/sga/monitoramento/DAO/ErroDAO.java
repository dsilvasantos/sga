package br.com.sga.monitoramento.DAO;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.sga.monitoramento.model.Erro;

public class ErroDAO {

	private static ErroDAO instance;
	protected EntityManager entityManager;

	public static ErroDAO getInstance() {
		if (instance == null) {
			instance = new ErroDAO();
		}

		return instance;
	}

	private ErroDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SGA");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public Erro getById(final int id) {
		return entityManager.find(Erro.class, id);
	}

	public void persist(Erro Erro) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Erro);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Erro Erro) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Erro);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Erro Erro) {
		try {
			entityManager.getTransaction().begin();
			Erro = entityManager.find(Erro.class, Erro.getId());
			entityManager.remove(Erro);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Erro Erro = getById(id);
			remove(Erro);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
