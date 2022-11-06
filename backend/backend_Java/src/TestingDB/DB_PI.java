package TestingDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;


public class DB_PI {
	// TO-DO add connections
    static final String JDBC_DRIVER ="org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/Hostellar";

    static final String USER = "postgres";
    static final String PASS ="Password";
    public static void main(String[] args) {
            Connection conn = null;
            Statement stmt = null;
            try{
                Class.forName(JDBC_DRIVER);
                //DriverManager.register(new org.postgresql.Driver());
                //DriverManager.registerDriver(new org.postgresql.Driver());
                System.out.println("Connecting to database ... ");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Connected");
                //Creating statement 
                stmt= conn.createStatement();
                ResultSet rs= stmt.executeQuery("Select * from userscredentials ; " );
                while(rs.next()) {
                	//Extracting data
                	String username = rs.getString("username");
                	String password= rs.getString("password");
                	String email = rs.getString("email");
                	String time= rs.getString("date_of_creation");
                	String lastLogin = rs.getString("last_login");
                	
                	System.out.println(username+" | "+password+" | "+ email +" | "+time+" | "+lastLogin );
                }
                
                System.out.println(rs.getRow());
                // Query for inserting into database
                /*
                String username="hashemKhodorz";
                String password= "YESSIRs";
            	String email = "HashemKhodor2003z";
            	String time= "NOW()";
            	String lastLogin = "NOW()";
            	String query = String.format("Insert INTO userscredentials (username,password,email,date_of_creation,last_login) VALUES('%s','%s','%s',%s,%s) ;",username,password,email,time,lastLogin);  
            	System.out.println(query);
            	stmt.execute(query );
            	*/
                stmt.close();
                conn.close();
                


                
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
            System.out.println("Yessir");

    }
    
    
}


/*
 * public static void main(String[] args) {
        final String url = "jdbc:postgresql://localhost:5432/Hostellar";
        final String user = "postgres";
        final String password = "hungryshark2003";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("HeyMan");
    }
 * 
 */
