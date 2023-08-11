package net.yoedtos.blog.repository.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

public class DaoHandlerTest extends AbstractDaoTest {

	public DaoHandlerTest() throws DataSetException {
		super();
	}

	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
	}
		
	@Test
	public void testInitDataSetCreation() throws DataSetException, SQLException {
		DaoHandler.createAdminUser();	
		ITable userData = connection.createQueryTable(Table.USER, "SELECT * FROM User");
		assertThat(userData.getValue(0, Columns.USERNAME), equalTo("admin-user"));
		assertThat(userData.getValue(0, Columns.ROLE), equalTo("ADMINISTRATOR"));
		assertTrue((boolean)userData.getValue(0, Columns.ACTIVE));
	
		DaoHandler.createDemoData();
		ITable postData = connection.createQueryTable(Table.POST, "SELECT * FROM POST");
		assertThat(postData.getRowCount(), equalTo(6));
	}
	
	class Columns {
		public static final String USERNAME = "username";
		public static final String ROLE = "role";
		public static final String ACTIVE = "active";
	}
	
	class Table {
		public static final String USER = "user";
		public static final String POST = "post";
	}
}
