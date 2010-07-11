package ar.com.natlehmann.cdcatalogue.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DaoResources {
	
	public static DaoResources instance = new DaoResources();
	
	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	private DaoResources() {}
	
	public static DaoResources getInstance() {
		return instance;
	}
	
	public EntityManager getEntityManager() {
		
		if (this.entityManager == null) {
			this.factory = Persistence.createEntityManagerFactory("CdCatalogueJpa");
			this.entityManager = this.factory.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public void shutDown() {
		this.entityManager.close();
		this.factory.close();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.shutDown();
	}

}
