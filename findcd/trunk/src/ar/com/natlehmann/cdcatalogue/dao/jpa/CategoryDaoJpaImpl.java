package ar.com.natlehmann.cdcatalogue.dao.jpa;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import ar.com.natlehmann.cdcatalogue.business.exception.DuplicateNameException;
import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.dao.CategoryDao;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.NonUniqueResultExcetion;

public class CategoryDaoJpaImpl extends JpaDaoSupport implements CategoryDao {
	
	private static Log log = LogFactory.getLog(CategoryDaoJpaImpl.class);
	private EntityManager em;
	
	public EntityManager getEntityManager() {
		if (em == null) {
			log.info("Creating Entity Manager");
			em = this.getJpaTemplate().getEntityManagerFactory().createEntityManager();
		}
		return em;
	}
	

	public Category createCategory(Category category) throws DaoException {
		
		try {
			this.getJpaTemplate().execute(new CreateCategoryCallback(category));
			
		} catch (DuplicateNameException e) {
			throw e;
			
		} catch (Exception e) {
			
			log.error("Could not create category " + category);
			throw new DaoException(e);
		}
		
		return category;		
	}

	@SuppressWarnings("unchecked")
	public Category findCategory(String categoryName) throws DaoException {

		List<Category> results = this.getJpaTemplate().find(
				"SELECT c FROM Category c WHERE c.categoryName = ?1", categoryName);
		
		if (!results.isEmpty()) {
			
			if (results.size() > 1) {
				throw new NonUniqueResultExcetion(
						"Category " + categoryName + " is not unique");
			}
			
			return results.iterator().next();
			
		} else {
			return null;
		}
		
	}

	public Category getCategory(Integer categoryId) throws DaoException {

		return this.getJpaTemplate().find(Category.class, categoryId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryVolumes() throws DaoException {
		
		List<Category> results = this.getJpaTemplate().find(
				"SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.volumes v " +
				"ORDER BY v.volumeName");
		
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategories() throws DaoException {
		
		List<Category> results = this.getJpaTemplate().find(
				"SELECT c FROM Category c ORDER BY c.categoryName");
		
		return results;
	}


	@Override
	public void deleteCategory(Category category) throws DaoException {

		try {
			this.getJpaTemplate().execute(new DeleteCategoryCallback(category),true);
			
		} catch (Exception e) {
			
			log.error("Could not delete category " + category);
			throw new DaoException(e);
		}
		
	}
	
	@Override
	public void updateCategory(Category category) throws DaoException {
		
		try {
			this.getJpaTemplate().execute(new UpdateCategoryCallback(category),true);
			
		} catch (Exception e) {
			
			log.error("Could not update category " + category);
			throw new DaoException(e);
		}
		
	}
	
	
	public class DeleteCategoryCallback implements JpaCallback {
		
		private Category category;		

		public DeleteCategoryCallback(Category category) {
			this.category = category;
		}

		@Override
		public Object doInJpa(EntityManager em) throws PersistenceException {
			
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			try {
				this.category = em.merge(category);
				em.remove(category);
				em.flush();
				em.getTransaction().commit();
				
			} catch (Exception e) {
				log.error("Could not delete Category. " + e.getMessage());
				em.getTransaction().rollback();
				throw new PersistenceException(e);
			}
			
			return null;
		}		
	}
	
	public class CreateCategoryCallback implements JpaCallback {
		
		private Category category;		

		public CreateCategoryCallback(Category category) {
			this.category = category;
		}

		@Override
		public Object doInJpa(EntityManager em) throws PersistenceException {
			
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			try {
				em.persist(category);
				em.flush();
				em.getTransaction().commit();
				
			} catch (EntityExistsException e) {
				log.error("Category already exists. Could not create category. " + e.getMessage());
				em.getTransaction().rollback();
				throw new DuplicateNameException("Category already exists.", e);
				
			} catch (Exception e) {
				log.error("Could not create Category. " + e.getMessage());
				em.getTransaction().rollback();
				throw new PersistenceException(e);
			}
			
			return category;
		}		
	}
	
	
	public class UpdateCategoryCallback implements JpaCallback {
		
		private Category category;		

		public UpdateCategoryCallback(Category category) {
			this.category = category;
		}

		@Override
		public Object doInJpa(EntityManager em) throws PersistenceException {
			
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			try {
				this.category = em.merge(category);
				em.flush();
				em.getTransaction().commit();
				
			} catch (Exception e) {
				log.error("Could not update Category. " + e.getClass().getName());
				em.getTransaction().rollback();
				throw new PersistenceException(e);
			}
			
			return this.category;
		}		
	}


}
