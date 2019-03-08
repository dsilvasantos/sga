package br.com.sga.monitoramento.DAO;

//import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Jvm;

public class JvmDAO {

	private static JvmDAO instance;
	protected EntityManager entityManager;
	
	
	public static JvmDAO getInstance(){
		if(instance == null){
			instance = new JvmDAO();
		}
		return instance;
	}
	private JvmDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SGA");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public Jvm getById(final int id) {
		return entityManager.find(Jvm.class, id);
	}

	public void persist(Jvm jvm) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(jvm);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Jvm Jvm) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Jvm);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Jvm Jvm) {
		try {
			entityManager.getTransaction().begin();
			Jvm = entityManager.find(Jvm.class, Jvm.getId());
			entityManager.remove(Jvm);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Jvm Jvm = getById(id);
			remove(Jvm);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	
	public Jvm recupearAmbiente(String nome) {
		Query query = entityManager.createNativeQuery(
				"Select * from jvm where jvm.nome=?1",Jvm.class);
		query.setParameter(1, nome);
		Jvm jvm = (Jvm) query.getSingleResult();
		return jvm;
	}

}
