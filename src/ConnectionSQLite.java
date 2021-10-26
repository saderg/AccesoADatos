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
		actualizarArticulo();
		borrarCliente();
		
	}
	
	public static void mostrarFacturas() {
		
		Connection con = connection();
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM FACTURA");
			
			while(rs.next()) {
				String numero = rs.getString("NumFac");
				String fecha = rs.getString("FecFac");
				String importe = rs.getString("ImpFac");
				String nif = rs.getString("NifCli");
				
				System.out.println(numero + " " + fecha + " " + importe + " " + nif);
			      }
			
			
		}catch(Exception ex) {
			ex.getMessage();
		}
		
	}
	
	public static void introducirCliente() {
		
		Connection con = connection();
		
		try {
			
			PreparedStatement pst = con.prepareStatement("INSERT INTO CLIENTE (NIFCLI, NOMCLI, DIRCLI, SDOCLI) VALUES (?,?,?,?)");
			pst.setString(1, "50357170G");
			pst.setString(2, "Scherezade");
			pst.setString(3, "Encarla, 12, Madrid");
			pst.setInt(4, 20000000);
			pst.executeUpdate();
		

		}catch (Exception e) {
			e.getMessage();
		}
		
	}
	
	public static void actualizarArticulo() {
		
		Connection con = connection();
		
		try {
			
			PreparedStatement pst = con.prepareStatement("UPDATE ARTICULO SET CODART = 'C32' WHERE CODART = 'C24'");
			pst.executeUpdate();
			

		}catch (Exception e) {
			e.getMessage();
		}
		
	}
	
	public static void borrarCliente() {
		
		Connection con = connection();
		
		try {
			
			PreparedStatement pst = con.prepareStatement("DELETE FROM CLIENTE WHERE NOMCLI = ?");
			pst.setString(1, "Scherezade");
			pst.executeUpdate();
			

		}catch (Exception e) {
			e.getMessage();
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
