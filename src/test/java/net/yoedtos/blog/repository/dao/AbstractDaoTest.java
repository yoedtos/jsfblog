package net.yoedtos.blog.repository.dao;

import static net.yoedtos.blog.util.TestConstants.DATA_SET;

import java.io.InputStream;

import javax.persistence.EntityManager;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.hibernate.internal.SessionImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractDaoTest {

	private static EntityManager entityManager;
	protected static IDatabaseConnection connection;
	protected static IDataSet dataSet;
	
	public AbstractDaoTest() throws DataSetException {
		InputStream xmlInputFile = getClass().getClassLoader().getResourceAsStream(DATA_SET);
		FlatXmlDataSetBuilder xmBuilder = new FlatXmlDataSetBuilder();
		xmBuilder.setColumnSensing(true);
		dataSet = xmBuilder.build(xmlInputFile);
	}

	@BeforeClass
	public static void setUp() throws DatabaseUnitException {
		DaoHandler.init();
		entityManager = DaoHandler.getEntityManager();
		connection = new DatabaseConnection(((SessionImpl) entityManager.getDelegate()).connection());
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
	}
	
	@AfterClass
	public static void tearDown() {
		entityManager.close();
		DaoHandler.shutdown();
	}
}
