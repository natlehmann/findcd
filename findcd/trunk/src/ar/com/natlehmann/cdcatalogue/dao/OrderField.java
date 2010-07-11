package ar.com.natlehmann.cdcatalogue.dao;

public enum OrderField {
	
	CATEGORY_NAME	("categoryName", QueryHelper.CATEGORY_PREFIX, "Category"),
	VOLUME_NAME		("volumeName", QueryHelper.VOLUME_PREFIX, "Volume"),
	RESOURCE_NAME	("resourceName", QueryHelper.RESOURCE_PREFIX, "Name"),
	RESOURCE_PATH	("path", QueryHelper.RESOURCE_PREFIX, "Path"),
	RESOURCE_TYPE	("resourceType", QueryHelper.RESOURCE_PREFIX, "Type"),
	RESOURCE_MODIFIED_DATE	("modifiedDate", QueryHelper.RESOURCE_PREFIX, "Mod. Date"),
	RESOURCE_SIZE	("fileSize", QueryHelper.RESOURCE_PREFIX, "Size"),
	RESOURCE_COMMENTS		("comments", QueryHelper.RESOURCE_PREFIX, "Comments");
	
	private String fieldName;
	
	private String prefix;
	
	private String visibleName;
	
	private OrderField(String fieldName, String prefix, String visibleName) {
		this.fieldName = fieldName;
		this.prefix = prefix;
		this.visibleName = visibleName;
	}
	
	protected String getFieldName() {
		return fieldName;
	}
	
	protected String getQuery() {
		return this.prefix + "." + this.fieldName;
	}
	
	public String getVisibleName() {
		return this.visibleName;
	}
	
	public static String getVisibleName(int index) {
		return OrderField.values()[index].getVisibleName();
	}

}

