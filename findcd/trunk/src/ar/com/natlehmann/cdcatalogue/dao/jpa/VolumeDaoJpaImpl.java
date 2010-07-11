package ar.com.natlehmann.cdcatalogue.dao.jpa;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.natlehmann.cdcatalogue.business.exception.DuplicateNameException;
import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.NonUniqueResultExcetion;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.QueryHelper;
import ar.com.natlehmann.cdcatalogue.dao.VolumeDao;

public class VolumeDaoJpaImpl implements VolumeDao {
	
	private static Log log = LogFactory.getLog(VolumeDaoJpaImpl.class);
	
	private static final String BASE_QUERY_STRING = "SELECT DISTINCT " + QueryHelper.VOLUME_PREFIX + 
		" FROM Volume " + QueryHelper.VOLUME_PREFIX 
		+ " LEFT JOIN FETCH " + QueryHelper.VOLUME_PREFIX + ".resources " + QueryHelper.RESOURCE_PREFIX 
		+ " LEFT JOIN FETCH " + QueryHelper.VOLUME_PREFIX + ".category " 
		+ QueryHelper.CATEGORY_PREFIX + " ";
	

	public Volume createVolume(Volume volume) throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		
		try {
			em.persist(volume);
			em.flush();
			em.getTransaction().commit();
		
		} catch (EntityExistsException e) {
			log.error("Volume already exists. Could not create volume. " + e.getMessage());
			em.getTransaction().rollback();
			throw new DuplicateNameException("Volume already exists.", e);
			
		} catch (Exception e) {
			log.error("Could not create volume. " + e.getMessage());
			em.getTransaction().rollback();
			throw new DaoException(e);
		}
		
		return volume;
	}

	@SuppressWarnings("unchecked")
	public Volume findVolume(String volumeName) throws DaoException {

		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		List<Volume> results = em.createQuery(
				"SELECT c FROM Volume c WHERE c.volumeName = ?1")
				.setParameter(1, volumeName)
				.getResultList();
		
		if (!results.isEmpty()) {	
			
			if (results.size() > 1) {
				throw new NonUniqueResultExcetion(
						"Volume " + volumeName + " is not unique");
			}
			
			return results.iterator().next();
			
		} else {
			return null;
		}
	}

	public Volume getVolume(Integer volumeId) throws DaoException {

		return DaoResources.getInstance().getEntityManager().find(
				Volume.class, volumeId);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Volume> getVolumes(List<Parameter> parameters, OrderBy orderField) 
	throws DaoException {	
		
		StringBuffer query = new StringBuffer(BASE_QUERY_STRING);
		
		query.append(QueryHelper.getWhereClause(parameters));
		
		if (orderField != null) {
			query.append("ORDER BY ").append(orderField.toString());
		}
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		Query queryObj = em.createQuery(query.toString());
		
		if (!parameters.isEmpty()) {
			
			int index = 1;
			
			for (Parameter parameter : parameters) {
				queryObj.setParameter(index, parameter.getValue());
				index++;
			}
		}
		
		return queryObj.getResultList();
		
	}
	


	@Override
	public List<Volume> getVolumes(List<Parameter> parameters) throws DaoException {
		
		return this.getVolumes(parameters, null);
	}

	
	@SuppressWarnings("unchecked")
	public List<Volume> getVolumes(List<Parameter> parameters, OrderBy orderField, Page page) 
	throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		StringBuffer queryStr = new StringBuffer(BASE_QUERY_STRING);
		
		queryStr.append(QueryHelper.getWhereClause(parameters));

		if (orderField != null) {
			queryStr.append("ORDER BY ").append(orderField.toString());
		}
		
		Query query = em.createQuery(queryStr.toString());
		
		if (!parameters.isEmpty()) {
			
			int index = 1;
			
			for (Parameter parameter : parameters) {
				query.setParameter(index, parameter.getValue());
				index ++;
			}
		}
		
		query.setFirstResult(page.getFirstResult());
		query.setMaxResults(page.getMaxResults());
		
		return query.getResultList();
	}
	

	public List<Volume> getVolumes(Page page) throws DaoException {
		
		return this.getVolumes(null, page);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Volume> getVolumes(OrderBy orderField, Page page) 
	throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		StringBuffer queryStr = new StringBuffer(BASE_QUERY_STRING);
		
		if (orderField != null) {
			queryStr.append("ORDER BY ").append(orderField.toString());
		}

		return em.createQuery(queryStr.toString()).setFirstResult(
				page.getFirstResult()).setMaxResults(page.getMaxResults()).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Volume> getVolumes(Category category) throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		List<Volume> results = em.createQuery(
				"SELECT v FROM Volume v WHERE v.category = ?1 ORDER BY v.volumeName")
				.setParameter(1, category)
				.getResultList();
		
		return results;
	}

	@Override
	public void deleteVolume(Volume volume) throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		
		try {
			Volume deletedVol = em.find(Volume.class, volume.getVolumeId());
			em.remove(deletedVol);
			em.flush();
			em.getTransaction().commit();
			
		} catch (Exception e) {
			log.error("Could not delete Volume. " + e.getMessage());
			em.getTransaction().rollback();
			throw new DaoException(e);
		}		
	}
	
	@Override
	public void updateVolume(Volume volume) throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		
		try {
			volume = em.merge(volume);
			em.flush();
			em.getTransaction().commit();
			
		} catch (Exception e) {
			log.error("Could not update Volume. " + e.getMessage());
			em.getTransaction().rollback();
			throw new DaoException(e);
		}		
	}

	@Override
	public void updateVolumes(List<Volume> volumes) throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		
		try {
			
			for (Volume volume : volumes) {
				volume = em.merge(volume);
				em.flush();
			}				
			em.getTransaction().commit();
			
		} catch (Exception e) {
			log.error("Could not update Volume. " + e.getMessage());
			em.getTransaction().rollback();
			throw new DaoException(e);
		}		
	}

}
