package ar.com.natlehmann.cdcatalogue.view.dataModel;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.com.natlehmann.cdcatalogue.Configuration;
import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelFacade;
import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.OrderField;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy.Direction;

public class ResourceResultsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -303997187748614469L;
	
	private CdCatalogueModelFacade modelFacade;
	private OrderBy orderBy;
	private List<Parameter> searchParameters;
	private Page page;
	private long resultsCount;
	
	private boolean dirtyResults = true;
	private boolean dirtyResultsCount = true;

	private List<Resource> resources;
	
	
	public ResourceResultsTableModel(CdCatalogueModelFacade modelFacade) {
		super();
		this.modelFacade = modelFacade;
	}

	@Override
	public int getColumnCount() {
		
		// we do not show the last column
		return OrderField.values().length - 1;
	}

	@Override
	public int getRowCount() {
		
		this.checkRefresh();
		return this.resources.size();
	}

	@Override
	public Object getValueAt(int row, int column) {

		this.checkRefresh();
		Resource resource = this.resources.get(row);
		
		String value = "";
		
		switch (column) {
		
		case 0: 
			if (resource.getVolume().getCategory() != null) {
				value = resource.getVolume().getCategory().getCategoryName();
			}
			break;
		
		case 1: value = resource.getVolume().getVolumeName();
				break;
				
		case 2: value = resource.getResourceName();
				break;
				
		case 3: value = resource.getPath();
				break;
				
		case 4: value = resource.getResourceType();
				break;
				
		case 5: value = (resource.getModifiedDate() != null) ?
							Configuration.getDateAsString(resource.getModifiedDate()) : "";
				break;
				
		case 6: value = resource.getFileSize();
		
		}
		
		return value;
	}
	
	
	@Override
	public String getColumnName(int column) {
		return OrderField.getVisibleName(column);
	}

	private void checkRefresh() {
		if (this.dirtyResults) {
			this.resources = this.modelFacade.getResources(
					this.getSearchParameters(), 
					this.getOrderBy(), 
					this.getPage());
			this.dirtyResults = false;
		}		
	}

	public OrderBy getOrderBy() {
		if (this.orderBy == null) {
			this.orderBy = new OrderBy(OrderField.CATEGORY_NAME, Direction.ASC);
		}
		return orderBy;
	}

	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
		this.dirtyResults = true;
	}

	public List<Parameter> getSearchParameters() {
		if (this.searchParameters == null) {
			this.searchParameters = new LinkedList<Parameter>();
		}
		return searchParameters;
	}

	public void setSearchParameters(List<Parameter> searchParameters) {
		this.searchParameters = searchParameters;
		this.dirtyResults = true;
		this.dirtyResultsCount = true;
	}

	public Page getPage() {
		if (this.page == null) {
			this.page = new Page(1);
		}
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
		this.dirtyResults = true;
	}
	
	public long getResultsCount() {
		if (this.dirtyResultsCount) {
			this.resultsCount = this.modelFacade.getResourceCount(this.getSearchParameters());
			this.dirtyResultsCount = false;
		}
		
		return this.resultsCount;
	}

	public String getNumberOfResults() {
		
		long resultCount = this.getResultsCount();
		
		long showing = 0;
		
		if (this.getPage().getPageNumber() == this.getLastPageNumber()) {
			showing = resultCount - this.getPage().getFirstResult();
		
		} else {
			showing = this.getPage().getMaxResults();
		} 
		
		return "Showing " + showing + " of " + this.getResultsCount();
	}

	public int getLastPageNumber() {
		return (int)Math.ceil((double)this.getResultsCount() / this.getPage().getMaxResults());
	}

}
