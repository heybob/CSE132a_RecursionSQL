import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class Project {
	public static void main(String[] args) throws FileNotFoundException {

		//Create a PrintWriter Object for text output
		PrintWriter pw = new PrintWriter("output.txt");
		Connection con = null;

		try {
			// Get the connection to the database
			DriverManager.registerDriver(new SQLServerDriver());
			String url = "jdbc:sqlserver://ACS-CSEB-SRV.ucsd.edu:1433;databaseName="
					+ args[0];
			con = DriverManager.getConnection(url, args[0], args[1]);

			// Query the Flights table
			Statement stmt = con.createStatement();
			ResultSet rset = stmt.executeQuery("SELECT * from " + args[2]);

			// MAIN PART OF PROGRAM
			while(rset.next()){
				findAllFlights(rset.getString("Origin"));
			}
				
			
			// Create Temp Databases

			// Print the Origin and Destination columns
			while (rset.next()) {
				System.out.print(rset.getString("Origin"));
				System.out.print("---");
				System.out.println(rset.getString("Destination"));
				pw.print(rset.getString("Origin"));
				pw.print("---");
				pw.println(rset.getString("Destination"));
			}

			// close the result set, statement
			pw.close();
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException("There was a problem!", e);
		} finally {
			// I have to close the connection in the "finally" clause otherwise
			// in case of an exception i would leave it open.
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new RuntimeException(
						"Help! I could not close the connection!!!", e);
			}
		}
	}

	public static void findAllFlights(String OGN){
		
	}

}
