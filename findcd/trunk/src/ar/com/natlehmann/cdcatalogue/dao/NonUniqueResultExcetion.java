package ar.com.natlehmann.cdcatalogue.dao;

public class NonUniqueResultExcetion extends DaoException {

	private static final long serialVersionUID = -703313374914454511L;

	public NonUniqueResultExcetion() {
	}

	public NonUniqueResultExcetion(String arg0) {
		super(arg0);
	}

	public NonUniqueResultExcetion(Throwable arg0) {
		super(arg0);
	}

	public NonUniqueResultExcetion(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
