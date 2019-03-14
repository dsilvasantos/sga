package br.com.sga.monitoramento.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.RecursosAplicacao;

public class RecursosAplicacaoDAO {

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


	public void persist(RecursosAplicacao recursosAplicacao) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(recursosAplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void merge(RecursosAplicacao recursosAplicacao) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(recursosAplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void remove(RecursosAplicacao recursosAplicacao) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			recursosAplicacao = entityManager.find(RecursosAplicacao.class, recursosAplicacao.getId());
			entityManager.remove(recursosAplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public RecursosAplicacao recupear(String aplicacao,String recurso) {
		EntityManager entityManager = getEntityManager();
		try {
		Query query = entityManager.createNativeQuery(
				"Select recursos_aplicacao.id,ID_APLICACAO,ID_RECURSOS,quantidade_minima,quantidade_maxima,quantidade_critica,valor from recursos_aplicacao,recursos,aplicacao WHERE recursos_aplicacao.ID_APLICACAO = aplicacao.id and "
					    + "aplicacao.nome = ?1 and "
						+ "recursos_aplicacao.ID_RECURSOS = recursos.id and recursos.nome=?2",RecursosAplicacao.class);
		query.setParameter(1, aplicacao);
		query.setParameter(2, recurso);
		RecursosAplicacao recursosAplicacao = (RecursosAplicacao) query.getSingleResult();
		return recursosAplicacao;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
		return null;
	}
}
