package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.Query;

import br.com.sga.monitoramento.model.Usuario;

public class UsuarioDAO extends EntityManagerSingleton{


	public void persist(Usuario Usuario) {
		try {
			transaction.begin();
			entityManager.persist(Usuario);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void merge(Usuario Usuario) {
		try {
			
				transaction.begin();
			entityManager.merge(Usuario);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void remove(Usuario Usuario) {
		
		try {
			
			transaction.begin();
			Usuario = entityManager.find(Usuario.class, Usuario.getId());
			entityManager.remove(Usuario);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}

	public List<Usuario> recupearPorCelula(String celula) {
		try {

			Query query = entityManager.createNativeQuery(
					"Select usuario.id,usuario.login,usuario.senha,usuario.nome,usuario.email,usuario.status from "
							+ "celula,usuario where celula.nome= ?1 and usuario.id_celula=celula.id "
,
					Usuario.class);
			query.setParameter(1, celula);
			if (query.getResultList().isEmpty()) {
				return null;
			}
			List<Usuario> usuarios = query.getResultList();
			return usuarios;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listaUsuario() {
		try {
			Query query = entityManager.createQuery("select d from Usuario d");
			List<Usuario> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public Usuario retornaUsuario(Integer idUsuario){
		Usuario usuario = null;
		try{
			usuario = (Usuario) entityManager.createQuery("select f from Usuario f where f.id = :idUsuario")
			.setParameter("idUsuario", idUsuario).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return usuario;
	}
	
	public Usuario retornaUsuario(String nomeUsuario){
		Usuario usuario = null;
		try{
			usuario = (Usuario) entityManager.createQuery("select f from Usuario f where f.nome = :nomeUsuario")
			.setParameter("nomeUsuario", nomeUsuario).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return usuario;
	}
	
	public Usuario buscaUsuario(Integer id){
		Usuario usuario = null;
		try{
			usuario = entityManager.find(Usuario.class, id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return usuario;
	}
	
	public Usuario retornaUsuario(String login,String senha){
		Usuario usuario = null;
		try{
			Query query =  entityManager.createQuery(
					 "select f from Usuario f where f.login =:login and f.senha =:senha");
			query.setParameter("login", login);
			query.setParameter("senha", senha);
			usuario = (Usuario) query.getSingleResult();
			return usuario;
			}catch(Exception e){
				e.printStackTrace();
			}
		return null;
	
	}
	
		
}