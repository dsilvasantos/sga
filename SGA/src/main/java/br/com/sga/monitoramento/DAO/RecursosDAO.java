package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.Query;

import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Recursos;

public class RecursosDAO extends EntityManagerSingleton{


	public void persist(Recursos recursos) {
		try {
			transaction.begin();
			entityManager.persist(recursos);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void merge(Recursos recursos) {
		try {
			transaction.begin();
			entityManager.merge(recursos);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		} 
	}

	public void remove(Recursos recursos) {
		try {
			transaction.begin();
			recursos = entityManager.find(Recursos.class, recursos.getId());
			entityManager.remove(recursos);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Recursos> listaRecursos() {
		try {
			Query query = entityManager.createQuery("select d from Recursos d");
			List<Recursos> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public Recursos recupear(int recurso) {
		try {
			Query query = entityManager.createNativeQuery("Select * from recursos where id = ?1", Recursos.class);
			query.setParameter(1, recurso);
			if (!query.getResultList().isEmpty()) {
				Recursos recursos = (Recursos) query.getSingleResult();
				return recursos;
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Recursos> listaRecursoID(int id) {
		try {
			Query query = entityManager.createNativeQuery(
					"SELECT R.* FROM RECURSOS R WHERE R.ID NOT IN (SELECT ID_RECURSOS FROM RECURSOS_APLICACAO WHERE ID_APLICACAO =?1) ",Recursos.class);
			query.setParameter(1, id);
			List<Recursos> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
