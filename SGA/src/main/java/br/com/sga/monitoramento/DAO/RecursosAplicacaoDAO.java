package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.RecursosAplicacao;

public class RecursosAplicacaoDAO {

	private static RecursosAplicacaoDAO instance;
	protected EntityManager entityManager;

	public static RecursosAplicacaoDAO getInstance() {
		if (instance == null) {
			instance = new RecursosAplicacaoDAO();
		}

		return instance;
	}

	private RecursosAplicacaoDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SGA");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public RecursosAplicacao getById(final int id) {
		return entityManager.find(RecursosAplicacao.class, id);
	}

	public void persist(RecursosAplicacao recursosAplicacao) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(recursosAplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(RecursosAplicacao recursosAplicacao) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(recursosAplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(RecursosAplicacao recursosAplicacao) {
		try {
			entityManager.getTransaction().begin();
			recursosAplicacao = entityManager.find(RecursosAplicacao.class, recursosAplicacao.getId());
			entityManager.remove(recursosAplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			RecursosAplicacao recursosAplicacao = getById(id);
			remove(recursosAplicacao);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public RecursosAplicacao recupear(String aplicacao,String recurso) {
		Query query = entityManager.createNativeQuery(
				"Select ID_APLICACAO,ID_RECURSOS,quantidade_minima,quantidade_maxima,quantidade_critica,valor from recursos_aplicacao,recursos,aplicacao WHERE recursos_aplicacao.ID_APLICACAO = aplicacao.id and"
					    + "aplicacao.nome = ?1 and"
						+ "recursos_aplicacao.ID_RECURSOS = recursos.id and recursos.nome=?2",RecursosAplicacao.class);
		query.setParameter(1, aplicacao);
		query.setParameter(2, recurso);
		RecursosAplicacao recursosAplicacao = (RecursosAplicacao) query.getSingleResult();
		return recursosAplicacao;
	}
}
