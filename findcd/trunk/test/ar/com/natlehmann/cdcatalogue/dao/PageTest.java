package ar.com.natlehmann.cdcatalogue.dao;

import org.junit.Test;


public class PageTest extends DaoBaseTest {
	
	@Test
	public void testGetFirstResult() {
		
		Page page = new Page(1);
		assertEquals("Numero incorrecto", 0, page.getFirstResult());
		assertEquals("Numero incorrecto", Page.RESULTS_PER_PAGE - 1, page.getLastResult());
	}
	
	@Test
	public void testGetFirstResult2() {
		
		Page page = new Page(2);
		assertEquals("Numero incorrecto", Page.RESULTS_PER_PAGE, page.getFirstResult());
		assertEquals("Numero incorrecto", (Page.RESULTS_PER_PAGE + Page.RESULTS_PER_PAGE - 1), 
				page.getLastResult());
	}
	
	@Test
	public void testGetFirstResult3() {
		
		Page page = new Page(3);
		assertEquals("Numero incorrecto", (Page.RESULTS_PER_PAGE + Page.RESULTS_PER_PAGE), 
				page.getFirstResult());
		assertEquals("Numero incorrecto", (Page.RESULTS_PER_PAGE * 3 - 1), 
				page.getLastResult());
	}

}
