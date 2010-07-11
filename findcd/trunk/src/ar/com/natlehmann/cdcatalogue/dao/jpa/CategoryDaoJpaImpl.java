package ar.com.natlehmann.cdcatalogue.dao.jpa;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.natlehmann.cdcatalogue.business.exception.DuplicateNameException;
import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.dao.CategoryDao;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.NonUniqueResultExcetion;

public class CategoryDaoJpaImpl implements CategoryDao {
	
	private static Log log = LogFactory.getLog(CategoryDaoJpaImpl.class);
	

	public Category createCategory(Category category) throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		try {
			
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
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
			throw new DaoException(e);
		}
		
		return category;		
	}

	@SuppressWarnings("unchecked")
	public Category findCategory(String categoryName) throws DaoException {

		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		List<Category> results = em.createQuery(
				"SELECT c FROM Category c WHERE c.categoryName = :categoryName")
				.setParameter("categoryName", categoryName)
				.getResultList();
		
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
		
		return DaoResources.getInstance().getEntityManager().find(
				Category.class, categoryId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryVolumes() throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		List<Category> results = em.createQuery(
				"SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.volumes v " +
				"ORDER BY v.volumeName").getResultList();
		
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategories() throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		List<Category> results = em.createQuery(
				"SELECT c FROM Category c ORDER BY c.categoryName")
				.getResultList();
		
		return results;
	}


	@Override
	public void deleteCategory(Category category) throws DaoException {

		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		
		try {
			Category deletedCategory = em.find(Category.class, category.getCategoryId());
			em.remove(deletedCategory);
			em.flush();
			em.getTransaction().commit();
			
		} catch (Exception e) {
			log.error("Could not delete Category. " + e.getMessage());
			em.getTransaction().rollback();
			throw new DaoException(e);
		}
	}
	
	@Override
	public void updateCategory(Category category) throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		
		try {
			category = em.merge(category);
			em.flush();
			em.getTransaction().commit();
			
		} catch (Exception e) {
			log.error("Could not update Category. " + e.getClass().getName());
			em.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

}
