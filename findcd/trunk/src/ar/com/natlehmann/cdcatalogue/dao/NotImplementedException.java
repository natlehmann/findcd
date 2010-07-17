package ar.com.natlehmann.cdcatalogue.dao;


public class NotImplementedException extends DaoException {

	private static final long serialVersionUID = 7476866604617265448L;

	public NotImplementedException() {
	}

	public NotImplementedException(String arg0) {
		super(arg0);
	}

	public NotImplementedException(Throwable arg0) {
		super(arg0);
	}

	public NotImplementedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
