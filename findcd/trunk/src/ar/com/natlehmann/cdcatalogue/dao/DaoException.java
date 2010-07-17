package ar.com.natlehmann.cdcatalogue.dao;

public class DaoException extends Exception {

	private static final long serialVersionUID = 7732317068431331974L;

	public DaoException() {
	}

	public DaoException(String arg0) {
		super(arg0);
	}

	public DaoException(Throwable arg0) {
		super(arg0);
	}

	public DaoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
