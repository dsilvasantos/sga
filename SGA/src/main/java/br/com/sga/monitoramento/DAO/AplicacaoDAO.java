package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Aplicacao;

public class AplicacaoDAO {

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


	public void persist(Aplicacao Aplicacao) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Aplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void merge(Aplicacao Aplicacao) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Aplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void remove(Aplicacao Aplicacao) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Aplicacao = entityManager.find(Aplicacao.class, Aplicacao.getId());
			entityManager.remove(Aplicacao);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public List<Aplicacao> recupear(String celula) {
		EntityManager entityManager = getEntityManager();
		try {
		Query query = entityManager.createNativeQuery(
				"Select aplicacao.id,aplicacao.nome,aplicacao.status from aplicacao,celula where aplicacao.ID_CELULA = "
						+ "celula.id and celula.nome=?1",Aplicacao.class);
		query.setParameter(1, celula);
		List<Aplicacao> aplicacoes = query.getResultList();
		return aplicacoes;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
		return null;
	}
	
	public Aplicacao recupearAplicacao(String nome) {
		EntityManager entityManager = getEntityManager();
		try {
		Query query = entityManager.createNativeQuery(
				"Select * from aplicacao where aplicacao.nome=?1",Aplicacao.class);
		query.setParameter(1, nome);
		Aplicacao aplicacao = (Aplicacao) query.getSingleResult();
		return aplicacao;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
		return null;
	}
	
	public Aplicacao recupearAplicacaoID(int id) {
		EntityManager entityManager = getEntityManager();
		try {
		Query query = entityManager.createNativeQuery(
				"Select * from aplicacao where aplicacao.id=?1",Aplicacao.class);
		query.setParameter(1, id);
		Aplicacao aplicacao = (Aplicacao) query.getSingleResult();
		return aplicacao;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
		return null;
	}
}
