package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.sga.monitoramento.model.Departamento;
import br.com.sga.monitoramento.model.Usuario;

public class UsuarioDAO {

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = null;
		EntityManager entityManager = null;

		// Obt�m o factory a partir da unidade de persist�ncia.
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
		} finally {
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
		} finally {
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
		} finally {
			entityManager.close();
		}
	}

	public List<Usuario> recupearPorCelula(String celula) {
		EntityManager entityManager = getEntityManager();
		try {

			Query query = entityManager.createNativeQuery(
					"Select usuario.id,usuario.login,usuario.senha,usuario.nome,usuario.email,usuario.status from "
							+ "celula,usuario,trabalha where celula.nome= ?1 and trabalha.id_celula=celula.id and "
							+ "usuario.id=trabalha.id_usuario",
					Usuario.class);
			query.setParameter(1, celula);
			if (query.getResultList().isEmpty()) {
				return null;
			}
			List<Usuario> usuarios = query.getResultList();
			return usuarios;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listaUsuario() {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createQuery("select d from Usuario d");
			List<Usuario> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
		return null;
	}
	
	public Usuario retornaUsuario(Integer idUsuario){
		EntityManager entityManager = getEntityManager();
		Usuario usuario = null;
		try{
			entityManager.getTransaction().begin();
			usuario = (Usuario) entityManager.createQuery("select f from Usuario f where f.id = :idUsuario")
			.setParameter("idUsuario", idUsuario).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return usuario;
	}
	
	public Usuario retornaUsuario(String nomeUsuario){
		EntityManager entityManager = getEntityManager();
		Usuario usuario = null;
		try{
			entityManager.getTransaction().begin();
			usuario = (Usuario) entityManager.createQuery("select f from Usuario f where f.nome = :nomeUsuario")
			.setParameter("nomeUsuario", nomeUsuario).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return usuario;
	}
	
	public Usuario buscaUsuario(Integer id){
		EntityManager entityManager = getEntityManager();
		Usuario usuario = null;
		try{
			entityManager.getTransaction().begin();
			usuario = this.getEntityManager().find(Usuario.class, id);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			entityManager.close();
		}
		return usuario;
	}

}
