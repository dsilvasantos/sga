package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.Query;

import br.com.sga.monitoramento.model.Celula;
import br.com.sga.monitoramento.model.Usuario;

public class UsuarioDAO extends EntityManagerSingleton{


	public void persist(Usuario Usuario) {
		try {
			if(!transaction.isActive())
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
			if(!transaction.isActive())
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
			if(!transaction.isActive())
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
			if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
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
			Query query =  entityManager.createNativeQuery(""
					+ "select * from Usuario f where f.login =?1 and f.senha =?2");
			query.setParameter(1, login);
			query.setParameter(2, senha);
			if(query.getResultList().size()==0) {
				return usuario;
			}
			Object[] objeto = (Object[]) query.getSingleResult();
			int id = (Integer)objeto[0];
			String nome = (String) objeto[3];
			String email = (String) objeto[4];
			int status = (Integer) objeto[5];
			int tipo = (Integer) objeto[6];
			int celula = (Integer) objeto[7];

			usuario = new Usuario(id, login, senha,  nome, email,  status, tipo, celula);
			return usuario;
			}catch(Exception e){
				e.printStackTrace();
			}
		return null;
	
	}
	
		
}
