package ar.com.natlehmann.cdcatalogue.dao;

public class Page {
	
	private int pageNumber;
	
	public static final int RESULTS_PER_PAGE = 100;
	
	public Page(int pageNumber) {
		if (pageNumber < 1) {
			throw new IllegalArgumentException("Page number cannot be less than 1");
		}
		this.pageNumber = pageNumber;
	}
	

	public int getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public int getFirstResult() {
		return (this.pageNumber - 1) * RESULTS_PER_PAGE;
	}

	public int getMaxResults() {
		return RESULTS_PER_PAGE;
	}
	
	public int getFirstIndex() {
		return 0;
	}
	
	public int getLastResult() {
		return RESULTS_PER_PAGE * this.pageNumber - 1;
	}
	
	public Page next() {
		this.setPageNumber(this.getPageNumber() + 1);
		return this;
	}
	
	public Page previous() {
		if (this.pageNumber == 1) {
			throw new IllegalArgumentException("Cannot go to previous page because it doesn't exist.");
		}
		this.setPageNumber(this.getPageNumber() - 1);
		return this;
	}
	
	@Override
	public String toString() {
		// TODO Messages
		return "Page " + this.pageNumber;
	}

}
