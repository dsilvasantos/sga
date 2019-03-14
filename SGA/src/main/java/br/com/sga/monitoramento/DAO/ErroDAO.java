package br.com.sga.monitoramento.DAO;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.sga.monitoramento.model.Erro;

public class ErroDAO {

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
	
	public void persist(Erro Erro) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Erro);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void merge(Erro Erro) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Erro);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void remove(Erro Erro) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Erro = entityManager.find(Erro.class, Erro.getId());
			entityManager.remove(Erro);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}
}
