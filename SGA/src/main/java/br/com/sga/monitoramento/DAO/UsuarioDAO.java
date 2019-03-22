package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Usuario;

public class UsuarioDAO {

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

	public void persist(Usuario Usuario) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Usuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void merge(Usuario Usuario) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Usuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}

	public void remove(Usuario Usuario) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Usuario = entityManager.find(Usuario.class, Usuario.getId());
			entityManager.remove(Usuario);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
	}
	
	public List<Usuario> recupearPorCelula(String celula) {
		EntityManager entityManager = getEntityManager();
		try {

			Query query = entityManager
					.createNativeQuery("Select usuario.id,usuario.login,usuario.senha,usuario.nome,usuario.email,usuario.status from "
							+ "celula,usuario,trabalha where celula.nome= ?1 and trabalha.id_celula=celula.id and "
							+ "usuario.id=trabalha.id_usuario",Usuario.class);
			query.setParameter(1, celula);
			if(query.getResultList().isEmpty()) {
				return null;
			}
			List<Usuario> usuarios = query.getResultList();
			return usuarios;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}finally {
			entityManager.close();
		}
		return null;

	}
}
