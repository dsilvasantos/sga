package br.com.sga.monitoramento.DAO;

import java.util.List;

import javax.persistence.Query;

import br.com.sga.monitoramento.model.Departamento;

public class DepartamentoDAO extends EntityManagerSingleton{


	public void persist(Departamento departamento) {
		
		try {
			transaction.begin();
			entityManager.persist(departamento);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			 transaction.rollback();
		} 
	}

	public void merge(Departamento departamento) {
		try {
			transaction.begin();
			entityManager.merge(departamento);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void remove(Departamento departamento) {
		try {
			transaction.begin();
			departamento = entityManager.find(Departamento.class, departamento.getId());
			entityManager.remove(departamento);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		} 
	}

	public List<String> recuperar() {
		try {
			Query query = entityManager.createNativeQuery("Select nome from departamento");
			List<String> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			 transaction.rollback();
		} 
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Departamento> listaDepartamentos() {
		try {
			Query query = entityManager.createQuery("select d from Departamento d");
			List<Departamento> result = query.getResultList();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		return null;
	}
	
	public Departamento retornaDepartamento(Integer idDepartamento){
		Departamento departamento = null;
		try{
			if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
			departamento = (Departamento) entityManager.createQuery("select f from Departamento f where f.id = :idDepartamento")
			.setParameter("idDepartamento", idDepartamento).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return departamento;
	}
	
	public Departamento retornaDepartamento(String nomeDepartamento){
		Departamento departamento = null;
		try{
			departamento = (Departamento) entityManager.createQuery("select f from Departamento f where f.nome = :nomeDepartamento")
			.setParameter("nomeDepartamento", nomeDepartamento).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return departamento;
	}
	
	public Departamento buscaDepartamento(Integer id){
		Departamento departamento = null;
		try{
			departamento = entityManager.find(Departamento.class, id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return departamento;
	}
}
