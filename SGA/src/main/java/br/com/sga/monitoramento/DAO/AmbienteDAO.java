package br.com.sga.monitoramento.DAO;

//import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

//import org.primefaces.expression.impl.ThisExpressionResolver;

import br.com.sga.monitoramento.model.Ambiente;

public class AmbienteDAO {
	
	private static AmbienteDAO instance;
	protected EntityManager entityManager;
	
	
	public static AmbienteDAO getInstance(){
		if(instance == null){
			instance = new AmbienteDAO();
		}
		return instance;
	}
	private AmbienteDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SGA");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public Ambiente getById(final int id) {
		return entityManager.find(Ambiente.class, id);
	}

	public void persist(Ambiente Ambiente) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Ambiente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Ambiente Ambiente) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Ambiente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Ambiente Ambiente) {
		try {
			entityManager.getTransaction().begin();
			Ambiente = entityManager.find(Ambiente.class, Ambiente.getId());
			entityManager.remove(Ambiente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Ambiente Ambiente = getById(id);
			remove(Ambiente);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Ambiente recupearAmbiente(String nome) {
		Query query = entityManager.createNativeQuery(
				"Select * from ambiente a where a.nome=?1",Ambiente.class);
		query.setParameter(1, nome);
		Ambiente ambiente = (Ambiente) query.getSingleResult();
		return ambiente;
	}
}
