package br.com.sga.monitoramento.DAO;

//import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

//import org.primefaces.expression.impl.ThisExpressionResolver;

import br.com.sga.monitoramento.model.Ambiente;

public class AmbienteDAO {

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

	public void persist(Ambiente Ambiente) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Ambiente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public void merge(Ambiente Ambiente) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Ambiente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public void remove(Ambiente Ambiente) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Ambiente = entityManager.find(Ambiente.class, Ambiente.getId());
			entityManager.remove(Ambiente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public Ambiente recupearAmbiente(String nome) {
		EntityManager entityManager = getEntityManager();
		try {
			Query query = entityManager.createNativeQuery("Select * from ambiente a where a.nome=?1", Ambiente.class);
			query.setParameter(1, nome);
			Ambiente ambiente = (Ambiente) query.getSingleResult();
			return ambiente;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
		return null;
	}
}
