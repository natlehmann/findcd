package ar.com.natlehmann.cdcatalogue.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.QueryHelper;
import ar.com.natlehmann.cdcatalogue.dao.ResourceDao;

public class ResourceDaoJpaImpl implements ResourceDao {
	
	private static Log log = LogFactory.getLog(ResourceDaoJpaImpl.class);
	
	private static final String BASE_SELECT_QUERY_STRING = "SELECT " + QueryHelper.RESOURCE_PREFIX
			+ " FROM Resource " + QueryHelper.RESOURCE_PREFIX 
			+ " LEFT JOIN FETCH " + QueryHelper.RESOURCE_PREFIX + ".volume " 
			+ QueryHelper.VOLUME_PREFIX
			+ " LEFT JOIN FETCH " + QueryHelper.VOLUME_PREFIX + ".category " 
			+ QueryHelper.CATEGORY_PREFIX + " ";
	
	private static final String BASE_COUNT_QUERY_STRING = "SELECT COUNT(*)"
			+ " FROM Resource " + QueryHelper.RESOURCE_PREFIX 
			+ " JOIN " + QueryHelper.RESOURCE_PREFIX + ".volume " 
			+ QueryHelper.VOLUME_PREFIX
			+ " JOIN " + QueryHelper.VOLUME_PREFIX + ".category " 
			+ QueryHelper.CATEGORY_PREFIX + " ";
	
	
	public void createResource(Resource resource) throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		
		try {
			em.persist(resource);
			em.flush();
			em.getTransaction().commit();
			
		} catch (Exception e) {
			log.error("Could not create Resource. " + e.getMessage());
			em.getTransaction().rollback();
			throw new DaoException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> getResources(List<Parameter> parameters,
			OrderBy orderField, Page page) throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		StringBuffer queryStr = new StringBuffer(BASE_SELECT_QUERY_STRING);
		
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
		
		return query.setFirstResult(
				page.getFirstResult()).setMaxResults(page.getMaxResults()).getResultList();
	}

	@Override
	public List<Resource> getResources(List<Parameter> parameters)
			throws DaoException {

		return this.getResources(parameters, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<Resource> getResources(List<Parameter> parameters, OrderBy orderBy)
	throws DaoException {
		
		StringBuffer query = new StringBuffer(BASE_SELECT_QUERY_STRING);
		
		query.append(QueryHelper.getWhereClause(parameters));
		
		if (orderBy != null) {
			query.append("ORDER BY ").append(orderBy.toString());
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
	public long getResourceCount(List<Parameter> searchParameters)
			throws DaoException {
		
		EntityManager em = DaoResources.getInstance().getEntityManager();
		
		StringBuffer queryStr = new StringBuffer(BASE_COUNT_QUERY_STRING);
		
		queryStr.append(QueryHelper.getWhereClause(searchParameters));
		
		Query query = em.createQuery(queryStr.toString());
		
		if (!searchParameters.isEmpty()) {
			
			int index = 1;
			
			for (Parameter parameter : searchParameters) {
				query.setParameter(index, parameter.getValue());
				index ++;
			}
		}
		
		Long result = (Long)query.getSingleResult();
		return result.longValue();
	}
	
}
