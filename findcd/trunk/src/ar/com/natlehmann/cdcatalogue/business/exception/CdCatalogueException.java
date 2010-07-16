package ar.com.natlehmann.cdcatalogue.business.exception;

public class CdCatalogueException extends Exception {

	private static final long serialVersionUID = -370924045345758480L;

	public CdCatalogueException() {
	}

	public CdCatalogueException(String message) {
		super(message);
	}

	public CdCatalogueException(Throwable cause) {
		super(cause);
	}

	public CdCatalogueException(String message, Throwable cause) {
		super(message, cause);
	}

}
