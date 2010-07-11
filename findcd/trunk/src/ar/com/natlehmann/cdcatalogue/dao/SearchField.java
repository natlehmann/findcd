package ar.com.natlehmann.cdcatalogue.dao;

public enum SearchField {
	
	CATEGORY_NAME	("categoryName", QueryHelper.CATEGORY_PREFIX, Operator.LIKE),
	VOLUME_NAME		("volumeName", QueryHelper.VOLUME_PREFIX, Operator.LIKE),
	RESOURCE_NAME	("resourceName", QueryHelper.RESOURCE_PREFIX, Operator.LIKE),
	RESOURCE_PATH	("path", QueryHelper.RESOURCE_PREFIX, Operator.LIKE),
	RESOURCE_TYPE	("resourceType", QueryHelper.RESOURCE_PREFIX, Operator.LIKE),
	RESOURCE_MODIFIED_DATE_FROM	("modifiedDate", QueryHelper.RESOURCE_PREFIX, Operator.GE),
	RESOURCE_MODIFIED_DATE_TO	("modifiedDate", QueryHelper.RESOURCE_PREFIX, Operator.LE),
	RESOURCE_COMMENTS			("comments", QueryHelper.RESOURCE_PREFIX, Operator.LIKE);
	
	private String fieldName;
	
	private String prefix;
	
	private Operator operator;
	
	private SearchField(String fieldName, String prefix, Operator operator) {
		this.fieldName = fieldName;
		this.prefix = prefix;
		this.operator = operator;
	}
	
	protected String getFieldName() {
		return fieldName;
	}
	
	protected Operator getOperator() {
		return operator;
	}
	
	protected String getQuery() {
		return this.prefix + "." + this.fieldName + " " + this.operator.getSymbol();
	}
	

}

