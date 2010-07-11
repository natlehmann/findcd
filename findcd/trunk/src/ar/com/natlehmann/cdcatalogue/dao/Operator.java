package ar.com.natlehmann.cdcatalogue.dao;

public enum Operator {
	
	LIKE	("LIKE"),
	GT		(">"),
	GE		(">="),
	EQ		("="),
	LT		("<"),
	LE		("<=");
	
	
	private String symbol;
	
	private Operator(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return this.getSymbol();
	}

}
