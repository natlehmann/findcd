package ar.com.natlehmann.cdcatalogue.business.exception;

public class InvalidNameException extends CdCatalogueException {

	private static final long serialVersionUID = 8810986014717117987L;

	public InvalidNameException() {
	}

	public InvalidNameException(String message) {
		super(message);
	}

	public InvalidNameException(Throwable cause) {
		super(cause);
	}

	public InvalidNameException(String message, Throwable cause) {
		super(message, cause);
	}

}
