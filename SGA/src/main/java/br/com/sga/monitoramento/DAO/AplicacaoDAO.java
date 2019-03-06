package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Aplicacao;

public class AplicacaoDAO {

	private static AplicacaoDAO instance;
	protected EntityManager entityManager;

	public static AplicacaoDAO getInstance() {
		if (instance == null) {
			instance = new AplicacaoDAO();
		}

		return instance;
	}

	private AplicacaoDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SGA");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public Aplicacao getById(final int id) {
		return entityManager.find(Aplicacao.class, id);
	}

	public void persist(Aplicacao Aplicacao) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Aplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Aplicacao Aplicacao) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Aplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Aplicacao Aplicacao) {
		try {
			entityManager.getTransaction().begin();
			Aplicacao = entityManager.find(Aplicacao.class, Aplicacao.getId());
			entityManager.remove(Aplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Aplicacao Aplicacao = getById(id);
			remove(Aplicacao);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<Aplicacao> recupear(String celula) {
		Query query = entityManager.createNativeQuery(
				"Select aplicacao.id,aplicacao.nome,aplicacao.status from aplicacao,celula where aplicacao.ID_CELULA = "
						+ "celula.id and celula.nome=?1",Aplicacao.class);
		query.setParameter(1, celula);
		List<Aplicacao> aplicacoes = query.getResultList();
		return aplicacoes;
	}
	
	public Aplicacao recupearAplicacao(String nome) {
		Query query = entityManager.createNativeQuery(
				"Select * from aplicacao where aplicacao.nome=?1",Aplicacao.class);
		query.setParameter(1, nome);
		Aplicacao aplicacao = (Aplicacao) query.getSingleResult();
		return aplicacao;
	}
}
