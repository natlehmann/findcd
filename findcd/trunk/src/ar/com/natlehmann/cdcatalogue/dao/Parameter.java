package ar.com.natlehmann.cdcatalogue.dao;

public class Parameter {
	
	private SearchField searchField;
	
	private Object value;

	public Parameter(SearchField searchField, Object value) {
		this.searchField = searchField;
		this.value = value;
	}

	public SearchField getSearchField() {
		return searchField;
	}

	public void setSearchField(SearchField searchField) {
		this.searchField = searchField;
	}

	public Object getValue() {
		
		if (this.searchField.getOperator().equals(Operator.LIKE)) {
			return "%" + this.value + "%";
		
		} else {
			return this.value;
		}
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getQuery(int index) {
		return this.searchField.getQuery() + " ?" + index; 
	}

}
