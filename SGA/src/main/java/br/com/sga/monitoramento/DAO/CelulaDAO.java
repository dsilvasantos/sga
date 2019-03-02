package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Celula;

public class CelulaDAO {

	private static CelulaDAO instance;
	protected EntityManager entityManager;

	public static CelulaDAO getInstance() {
		if (instance == null) {
			instance = new CelulaDAO();
		}

		return instance;
	}

	private CelulaDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SGA");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public Celula getById(final int id) {
		return entityManager.find(Celula.class, id);
	}

	public void persist(Celula Celula) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Celula);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Celula Celula) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Celula);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Celula Celula) {
		try {
			entityManager.getTransaction().begin();
			Celula = entityManager.find(Celula.class, Celula.getId());
			entityManager.remove(Celula);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Celula Celula = getById(id);
			remove(Celula);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<String> recupear(String departamento){
			Query query = entityManager.createNativeQuery("Select celula.nome from departamento,celula where celula.ID_DEPARTAMENTO = "
					+ "departamento.id and departamento.nome=?1");
			query.setParameter(1, departamento);
			List<String> result = query.getResultList();
			return result;
	}
}
