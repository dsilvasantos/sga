package br.com.sga.monitoramento.DAO;

import javax.persistence.Query;

import br.com.sga.monitoramento.model.RecursosAplicacao;

public class RecursosAplicacaoDAO extends EntityManagerSingleton{


	public void persist(RecursosAplicacao recursosAplicacao) {
		try {
			transaction.begin();
			entityManager.persist(recursosAplicacao);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void merge(RecursosAplicacao recursosAplicacao) {
		try {
			transaction.begin();
			entityManager.merge(recursosAplicacao);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void remove(RecursosAplicacao recursosAplicacao) {
		try {
			transaction.begin();
			recursosAplicacao = entityManager.find(RecursosAplicacao.class, recursosAplicacao.getId());
			entityManager.remove(recursosAplicacao);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public RecursosAplicacao recupear(String aplicacao,String recurso) {
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
		}
		return null;
	}
}
