package br.com.sga.monitoramento.DAO;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Celula;

public class AplicacaoDAO extends EntityManagerSingleton{

	

	public void persist(Aplicacao Aplicacao) {
		try {
			transaction.begin();
			entityManager.persist(Aplicacao);
			 transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void merge(Aplicacao Aplicacao) {
		try {
			transaction.begin();
			entityManager.merge(Aplicacao);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void remove(Aplicacao Aplicacao) {
		try {
			transaction.begin();
			Aplicacao = entityManager.find(Aplicacao.class, Aplicacao.getId());
			entityManager.remove(Aplicacao);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<Aplicacao> listaAplicacao() {
		try {
			Query query = entityManager.createQuery("select d from Aplicacao d");
			List<Aplicacao> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public List<Aplicacao> recupear(String celula) {
		try {
		Query query = entityManager.createNativeQuery(
				"Select aplicacao.id,aplicacao.nome,aplicacao.status from aplicacao,celula where aplicacao.ID_CELULA = "
						+ "celula.id and celula.nome=?1",Aplicacao.class);
		query.setParameter(1, celula);
		List<Aplicacao> aplicacoes = query.getResultList();
		return aplicacoes;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public Aplicacao recupearAplicacao(String nome) {
		try {
		Query query = entityManager.createNativeQuery(
				"Select * from aplicacao where aplicacao.nome=?1",Aplicacao.class);
		query.setParameter(1, nome);
		Aplicacao aplicacao = (Aplicacao) query.getSingleResult();
		return aplicacao;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public Aplicacao recupearAplicacaoID(int id) {
		try {
		Query query = entityManager.createNativeQuery(
				"Select * from aplicacao where aplicacao.id=?1",Aplicacao.class);
		query.setParameter(1, id);
		Aplicacao aplicacao = (Aplicacao) query.getSingleResult();
		return aplicacao;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Aplicacao> listaAplicacaoID(int id) {
		try {
			Query query = entityManager.createNativeQuery(
					"Select * from aplicacao where aplicacao.id=?1",Aplicacao.class);
			query.setParameter(1, id);
			List<Aplicacao> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
