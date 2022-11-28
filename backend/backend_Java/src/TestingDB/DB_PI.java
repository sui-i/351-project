package TestingDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class DB_PI {
	// TO-DO add connections
    static final String JDBC_DRIVER ="org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/Hostellar";
    private static Connection conn;
    static final String USER = "postgres";
    static final String PASS ="hungryshark2003";
    public static void main(String[] args) {
            
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
                
                ResultSet rs= stmt.executeQuery("Select * from userscredentials  ; " );
                //rs.next();
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
                //stmt.close();
                //conn.close();
                


                
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
            System.out.println("Yessir");
            try {

            	System.out.println(extractQuery(String.format("Select username from userscredentials  ;"),new String[] {"username","password","email","date_of_creation","last_login"}));
    			stmt= conn.createStatement();
    			String username="hmk57";
    			String query = String.format("Select username from userscredentials where username = '%s' ;",username);
    	        ResultSet rs= stmt.executeQuery(query);
    	        while(rs.next()) {
    	        	//Extracting data
            	String c_username = rs.getString("username");
            	if(c_username.equals(username) ) {
            		System.out.println( true);
            	}
            	else {
            	
            		System.out.println( false);
            	}
    	        }
            	stmt.close();
                conn.close();
    	        
                System.out.println("NotFoundSir");
    		}
    		
            catch(Exception e) {
            	System.out.println(e.getMessage());
            	System.out.println( false);
            }
            

    }
    
    public static ArrayList<HashMap<String,String>>  extractQuery(String query, String [] keys ){
		assert conn!=null : "No connection mate";
		try{
			Statement stmt= conn.createStatement();
			ResultSet rs= stmt.executeQuery(query);
			ArrayList<HashMap<String,String>> results= new ArrayList<>();
			while(rs.next()){
				HashMap<String,String> result= new HashMap<>();
				for(String key : keys){
					try{
						result.put(key,rs.getString(key));
					}
					catch(Exception e){
						result.put(key,null);
					}
					
				}
				results.add(result);
			

			}
			stmt.close();
			return results;
		}
		catch(Exception e){
			e.printStackTrace();
			return new ArrayList<>();
		}
		
	}
    
    
}


/*
 * public static void main(String[] args) {
        final String url = "jdbc:postgresql://localhost:5432/Hostellar";
        final String user = "postgres";
        final String password = ;

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
