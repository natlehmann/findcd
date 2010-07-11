package ar.com.natlehmann.cdcatalogue.dao;

import ar.com.natlehmann.cdcatalogue.dao.jpa.CategoryDaoTest;
import ar.com.natlehmann.cdcatalogue.dao.jpa.ResourceCategoryDaoTest;
import ar.com.natlehmann.cdcatalogue.dao.jpa.ResourceDaoTest;
import ar.com.natlehmann.cdcatalogue.dao.jpa.VolumeDaoTest;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * To run these tests you must first run the script sql/InsertTestData.sql on the DB
 * @author natalia
 *
 */
public class DaoTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for ar.com.natlehmann.cdcatalogue.dao");

		suite.addTestSuite(PageTest.class);
		suite.addTestSuite(CategoryDaoTest.class);
		suite.addTestSuite(ResourceDaoTest.class);
		suite.addTestSuite(VolumeDaoTest.class);
		suite.addTestSuite(ResourceCategoryDaoTest.class);

		return suite;
	}

}
