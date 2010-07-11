package ar.com.natlehmann.cdcatalogue.dao;

public class OrderBy {
	
	private OrderField field;
	private Direction direction;
	
	
	public OrderBy(OrderField field, Direction direction) {
		super();
		this.field = field;
		this.direction = direction;
	}

	public OrderField getField() {
		return field;
	}

	public void setField(OrderField field) {
		this.field = field;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}


	public void invertDirection() {
		this.direction = this.direction.getOpposite();
	}
	

	protected String getQuery() {		
		return this.field.getQuery() + " " + this.direction;
	}
	
	@Override
	public String toString() {
		return this.getQuery();
	}
	
	
	public enum Direction {
		
		ASC (Direction.DESC),
		DESC (Direction.ASC);
		
		private Direction opposite;
		
		private Direction(Direction opposite) {
			this.opposite = opposite;
		}
		
		public Direction getOpposite(){
			return this.opposite;
		}
	}

}
