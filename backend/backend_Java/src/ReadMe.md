# Classes and Functions
## Client Package

### C_InformationDB 
- Role: Holding the information of the user's  (usersinformation table)
- Data

## DataBasePackage

### DB_API
The DB_API is the main class for the interface between the database and the backend. 
#### Data Attributes
It has the following data attributes :
- private Connection conn : Connection  with database (default value is null).
- static final String JDBC_DRIVER : Responsible for specifying the type of the JDBC_DRIVER (in this project we used Postgres SQL)
- static final String DB_URL : Database url
- static final String USER : username (by default it's postgres)
- static final String PASS : password of the database
- private static HashMap<String,Integer> cache;
```java 
private Connection conn;
static final String JDBC_DRIVER ="org.postgresql.Driver"; 
static final String DB_URL = "jdbc:postgresql://localhost/DatabaseName";
static final String USER = "postgres";
static final String PASS ="YourPassword";
private static HashMap<String,Integer> cache;
```


#### Methods:
| Constructor and Description| 
|:--:|
|`public boolean ConnectDB() throws Exception;` <br> Used for creating a connection with the database. Setting conn to c |
|`static public String getFirstName(String username);`|
|`static public String getLastName(String username);`|
|`static public boolean checkMembership(String username) throws Exception; `|
|`static public int checkLoginCredentials(String username, String password,String email) throws Exception;`|
|`static public int RegisterUser(String username, String email, String password) throws Exception;`|






```java
public boolean ConnectDB() throws Exception;
static public String getFirstName(String username);
static public String getLastName(String username);
static public boolean checkMembership(String username) throws Exception;
static public int checkLoginCredentials(String username, String password,String email) throws Exception;
static public int RegisterUser(String username, String email, String password) throws Exception;
```
