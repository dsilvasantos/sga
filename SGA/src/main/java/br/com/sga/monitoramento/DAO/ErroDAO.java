package br.com.sga.monitoramento.DAO;


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
}
