import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.ResultSet;



public class ConnectionSQLite {

	public static void main(String[] args) {
		
		mostrarFacturas();
		introducirCliente();
	}
	
	public static void introducirCliente() {
		
		Connection con = connection();
		
		try {
			
			PreparedStatement pst = con.prepareStatement("INSERT INTO CLIENTE VALUES (50357170G, Scherezade, Mostoles, 2980000)");
			
			ResultSet rs = pst.executeQuery();
			
			Statement stm = con.createStatement();
			ResultSet rs2 = stm.executeQuery("SELECT * FROM CLIENTE");
			
			int numcols = rs.getMetaData().getColumnCount();
			while(rs.next()) {
				for(int i=1;i<=numcols;i++) {
					System.out.print("\t" + rs.getString(i));
			     }
					System.out.println("");
					
			      }


		}catch (Exception e) {
			e.getMessage();
		}
		
	}
	
	public static void mostrarFacturas() {
		
		Connection con = connection();
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM FACTURA");
			String numero = rs.getString("NumFac");
			String fecha = rs.getString("FecFac");
			String importe = rs.getString("ImpFac");
			String nif = rs.getString("NifCli");
			
			int numcols = rs.getMetaData().getColumnCount();
			while(rs.next()) {
				for(int i=1;i<=numcols;i++) {
					System.out.print("\t" + rs.getString(i));
			     }
					System.out.println("");
			      }
			
		}catch(Exception ex) {
			ex.getMessage();
		}
		
	}
	
	public static Connection connection() {
		Connection con = null;
		
		try {
			DataSource dataSource = setupDataSource("jdbc:sqlite:bdContabildiad.db");
			con = dataSource.getConnection();
			
		}catch(Exception ex) {
			ex.getMessage();
		}
		return con;
	}
	
	
	public static DataSource setupDataSource(String connectURI) {
		 BasicDataSource ds = new BasicDataSource();
		 ds.setUrl(connectURI);
		 return ds;
}
	
	public static void printDataSourceStats(DataSource ds) {
		 BasicDataSource bds = (BasicDataSource) ds;
		 System.out.println("NumActive: " + bds.getNumActive());
		 System.out.println("NumIdle: " + bds.getNumIdle());
		 }
	
	
	 public static void shutdownDataSource(DataSource ds) throws SQLException {
		 BasicDataSource bds = (BasicDataSource) ds;
		 bds.close();
		 }
}
