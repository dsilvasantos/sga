package br.com.sga.monitoramento.DAO;


import java.util.List;

import javax.persistence.Query;

import br.com.sga.monitoramento.model.Aplicacao;
import br.com.sga.monitoramento.model.Erro;

public class ErroDAO extends EntityManagerSingleton{

	
	public void persist(Erro Erro) {

		try {
			transaction.begin();
			entityManager.persist(Erro);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void merge(Erro Erro) {
		try {
			transaction.begin();
			entityManager.merge(Erro);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}

	public void remove(Erro Erro) {
		try {
			transaction.begin();
			Erro = entityManager.find(Erro.class, Erro.getId());
			entityManager.remove(Erro);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}
	}
	
	public List<Erro> recupearErroIDUsuario(int id) {
		try {
		Query query = entityManager.createNativeQuery(
				"Select * from erros where erros.id_usuario=?1 and status='Aberto'",Erro.class);
		query.setParameter(1, id);
		List<Erro> erros = query.getResultList();
		return erros;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
