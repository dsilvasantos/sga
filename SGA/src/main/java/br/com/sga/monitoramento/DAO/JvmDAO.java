package br.com.sga.monitoramento.DAO;

//import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Jvm;

public class JvmDAO {
	
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
	public void persist(Jvm jvm) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(jvm);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void merge(Jvm Jvm) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Jvm);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void remove(Jvm Jvm) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Jvm = entityManager.find(Jvm.class, Jvm.getId());
			entityManager.remove(Jvm);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}


	
	
	public Jvm recupearAmbiente(String nome) {
		EntityManager entityManager = getEntityManager();
		try {
		Query query = entityManager.createNativeQuery(
				"Select * from jvm where jvm.nome=?1",Jvm.class);
		query.setParameter(1, nome);
		Jvm jvm = (Jvm) query.getSingleResult();
		return jvm;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
		return null;
	}

}
