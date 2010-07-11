package ar.com.natlehmann.cdcatalogue.business.exception;

import javax.persistence.PersistenceException;

public class DuplicateNameException extends PersistenceException {

	private static final long serialVersionUID = -4382586787449199938L;

	public DuplicateNameException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DuplicateNameException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DuplicateNameException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DuplicateNameException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
