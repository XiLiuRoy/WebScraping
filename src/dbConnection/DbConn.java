package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import colesDataPickUp.ColesProduct;
import woolisDataPickUp.WoolProduct;

/**
 * Handles operations to DB
 * @author Xi
 *
 */
public class DbConn {
	
	private final String databaseServer = "XI";
    private final String databaseName = "TCDataRetrieve";
    private final String databaseUser = "n8661286";
    private final String databasePassword = "n8661286qut";
    private PreparedStatement insertData;
    private Connection connection = null;
    private final String connectionString = "jdbc:sqlserver://" + databaseServer + ";databaseName=" + databaseName + ";user="+databaseUser+";password="+databasePassword;

    /**
     * Constructor
     */
	public DbConn() {
		// TODO Auto-generated constructor stub
		this.connection = getConnection();
	}
	
    /**
     * Connect to database
     *
     * @return database connection
     */
    private Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(connectionString, databaseUser, databasePassword);
            } catch (Exception e) {
                System.out.println("Error Trace in getConnection(): " + e.getMessage());
            }
        }
        return connection;
    }
    
    /**
     * insert Woolis data to database
     * @param wp woolis entity
     */
    public void insertWp(WoolProduct wp){
    	String updateQuery = "insert into WoolWorth (Description,StockCode,Price,Url,Category) VALUES (?,?,?,?,?)";
    	try {
			insertData = this.connection.prepareStatement(updateQuery);
			insertData.setString(1, wp.getDesc());
			insertData.setString(2, wp.getStockCode());
			insertData.setString(3, wp.getPrice());
			insertData.setString(4, wp.getProductUrl());
			insertData.setString(5, wp.getCategory());
			insertData.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    /**
     * insert Coles data to database
     * @param cp Coles Entity
     */
    public void insertCp(ColesProduct cp){
    	String updateQuery = "insert into Coles (productId,itemId,partNumber,name,catEntryId,price) VALUES (?,?,?,?,?,?)";
    	try {
			insertData = this.connection.prepareStatement(updateQuery);
			insertData.setString(1, cp.getProductId());
			insertData.setString(2, cp.getItemId());
			insertData.setString(3, cp.getPartNumber());
			insertData.setString(4, cp.getName());
			insertData.setString(5, cp.getCatEntryId());
			insertData.setString(6, cp.getPrice());
			insertData.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}
