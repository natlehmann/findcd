package ar.com.natlehmann.cdcatalogue.dao;

import java.util.List;

public class QueryHelper {
	
	public static final String VOLUME_PREFIX = "v";
	
	public static final String RESOURCE_PREFIX = "r";
	
	public static final String CATEGORY_PREFIX = "c";
	
	
	public static String getWhereClause(List<Parameter> parameters) {
		
		StringBuffer query = new StringBuffer("");
		
		if (!parameters.isEmpty()) {
			
			int index = 1;
			query.append("WHERE ");
			
			for (Parameter parameter :  parameters) {
				query.append(parameter.getQuery(index)).append(" ");
				
				if (parameters.size() > index) {
					query.append("AND ");
				}
				
				index++;
			}
		}
		
		return query.toString();
		
	}

}
