package br.com.sga.monitoramento.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EntityManagerSingleton {

	protected static EntityManagerFactory entityManagerFactory;
	protected static EntityManager entityManager;
	protected static EntityTransaction transaction;


	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("SGA");
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
	}

}