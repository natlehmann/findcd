package ar.com.natlehmann.cdcatalogue.dao.jpa;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

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

public class VolumeDaoJpaImpl extends JpaDaoSupport implements VolumeDao {
	
	private static Log log = LogFactory.getLog(VolumeDaoJpaImpl.class);
	
	private EntityManager em;
	
	private static final String BASE_QUERY_STRING = "SELECT DISTINCT " + QueryHelper.VOLUME_PREFIX + 
		" FROM Volume " + QueryHelper.VOLUME_PREFIX 
		+ " LEFT JOIN FETCH " + QueryHelper.VOLUME_PREFIX + ".resources " + QueryHelper.RESOURCE_PREFIX 
		+ " LEFT JOIN FETCH " + QueryHelper.VOLUME_PREFIX + ".category " 
		+ QueryHelper.CATEGORY_PREFIX + " ";
	
	public EntityManager getEntityManager() {
		if (em == null) {
			log.info("Creating Entity Manager");
			em = this.getJpaTemplate().getEntityManagerFactory().createEntityManager();
		}
		return em;
	}

	public Volume createVolume(Volume volume) throws DaoException {

		try {
			this.getJpaTemplate().execute(new CreateVolumeCallback(volume));
			
		} catch (Exception e) {
			
			log.error("Could not create volume " + volume);
			throw new DaoException(e);
		}
		
		return volume;
	}

	@SuppressWarnings("unchecked")
	public Volume findVolume(String volumeName) throws DaoException {

		List<Volume> results = this.getJpaTemplate().find(
				"SELECT c FROM Volume c WHERE c.volumeName = ?1", volumeName);
		
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

		return this.getJpaTemplate().find(Volume.class, volumeId);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Volume> getVolumes(List<Parameter> parameters, OrderBy orderField) 
	throws DaoException {
	
		
		StringBuffer query = new StringBuffer(BASE_QUERY_STRING);
		
		query.append(QueryHelper.getWhereClause(parameters));
		
		if (orderField != null) {
			query.append("ORDER BY ").append(orderField.toString());
		}
		
		
		List<Object> values = new LinkedList<Object>();
		
		if (!parameters.isEmpty()) {
			
			for (Parameter parameter : parameters) {
				values.add(parameter.getValue());
			}
		}
		
		return this.getJpaTemplate().find(query.toString(), values.toArray());
		
	}
	


	@Override
	public List<Volume> getVolumes(List<Parameter> parameters) throws DaoException {
		
		return this.getVolumes(parameters, null);
	}

	
	@SuppressWarnings("unchecked")
	public List<Volume> getVolumes(List<Parameter> parameters, OrderBy orderField, Page page) 
	throws DaoException {
		
		EntityManager em = getEntityManager();
		
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
		
		EntityManager em = getEntityManager();
		
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
		
		List<Volume> results = this.getJpaTemplate().find(
				"SELECT v FROM Volume v WHERE v.category = ?1 ORDER BY v.volumeName", category);
		
		return results;
	}

	@Override
	public void deleteVolume(Volume volume) throws DaoException {
		
		try {
			this.getJpaTemplate().execute(new DeleteVolumeCallback(volume));	
			
		} catch (Exception e) {
			
			log.error("Could not delete volume " + volume);
			throw new DaoException(e);
		}		
	}
	
	@Override
	public void updateVolume(Volume volume) throws DaoException {
		
		try {
			this.getJpaTemplate().execute(new UpdateVolumeCallback(volume));	
			
		} catch (Exception e) {
			
			log.error("Could not update volume " + volume);
			throw new DaoException(e);
		}
		
	}

	@Override
	public void updateVolumes(List<Volume> volumes) throws DaoException {
		
		try {
			this.getJpaTemplate().execute(new UpdateVolumesCallback(volumes));
			
		} catch (Exception e) {
			
			log.error("Could not update volumes " + volumes);
			throw new DaoException(e);
		}
		
	}
	
	
	public class DeleteVolumeCallback implements JpaCallback {
		
		private Volume volume;		

		public DeleteVolumeCallback(Volume volume) {
			this.volume = volume;
		}

		@Override
		public Object doInJpa(EntityManager em) throws PersistenceException {
			
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			try {
				this.volume = em.merge(volume);
				em.remove(volume);
				em.flush();
				em.getTransaction().commit();
				
			} catch (Exception e) {
				log.error("Could not delete Volume. " + e.getMessage());
				em.getTransaction().rollback();
				throw new PersistenceException(e);
			}
			return null;
		}		
	}
	
	public class UpdateVolumeCallback implements JpaCallback {
		
		private Volume volume;		

		public UpdateVolumeCallback(Volume volume) {
			this.volume = volume;
		}

		@Override
		public Object doInJpa(EntityManager em) throws PersistenceException {
			
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			try {
				this.volume = em.merge(volume);
				em.flush();
				em.getTransaction().commit();
				
			} catch (Exception e) {
				log.error("Could not update Volume. " + e.getMessage());
				em.getTransaction().rollback();
				throw new PersistenceException(e);
			}
			return this.volume;
		}		
	}
	
	public class UpdateVolumesCallback implements JpaCallback {
		
		private List<Volume> volumes;		

		public UpdateVolumesCallback(List<Volume> volumes) {
			this.volumes = volumes;
		}

		@Override
		public Object doInJpa(EntityManager em) throws PersistenceException {
			
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			try {
				
				for (Volume volume : this.volumes) {
					volume = em.merge(volume);
					em.flush();
				}				
				em.getTransaction().commit();
				
			} catch (Exception e) {
				log.error("Could not update Volume. " + e.getMessage());
				em.getTransaction().rollback();
				throw new PersistenceException(e);
			}
			
			return null;
		}		
	}
	
	public class CreateVolumeCallback implements JpaCallback {
		
		private Volume volume;		

		public CreateVolumeCallback(Volume volume) {
			this.volume = volume;
		}

		@Override
		public Object doInJpa(EntityManager em) throws PersistenceException {
			
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
				throw new PersistenceException(e);
			}
			
			return volume;
		}		
	}

}
