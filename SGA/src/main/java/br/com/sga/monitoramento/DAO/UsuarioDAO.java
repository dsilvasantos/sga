package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Celula;
import br.com.sga.monitoramento.model.Usuario;

	public class UsuarioDAO {
	
	private static UsuarioDAO instance;
	protected EntityManager entityManager;

	public static UsuarioDAO getInstance() {
		if (instance == null) {
			instance = new UsuarioDAO();
		}
		return instance;
	}


	private UsuarioDAO() {
		entityManager = getEntityManager();
		
	}
	
	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SGA");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}
	
	public Usuario getById(final int id) {
		return entityManager.find(Usuario.class, id);
	}
	
	public void persist(Usuario Usuario) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Usuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}
	
	public void merge(Usuario Usuario) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Usuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}
	
	public void remove(Usuario Usuario) {
		try {
			entityManager.getTransaction().begin();
			Usuario = entityManager.find(Usuario.class, Usuario.getId());
			entityManager.remove(Usuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}
	
	public void removeById(final int id) {
		try {
			Usuario Usuario = getById(id);
			remove(Usuario);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public List<String> recupear(String departamento){
		Query query = entityManager.createNativeQuery("Select usuario.nome from departamento,usuario where usuario.departamento = "
				+ "departamento.id and departamento.nome=?1");
		query.setParameter(1, departamento);
		List<String> result = query.getResultList();
		return result;
	}
}
