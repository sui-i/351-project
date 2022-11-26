/*
    checkLoginCredentials :
    
    1. Select username , password from users_credentials where username = username ;


    RegisterUser:
    2. INSERT INTO users_credentials (username,email,password,date_of_creation,last_login,is_active)
        VALUES  ('%s','%s','%s',NOW(),NOW(),true ) ;

    Check Reservation :
        SELECT * FROM room_reservation_history where NOT ((booked_in <= EndB)  and  (booked_out >= StartB)) ; 
    
    SELECT count(*) from table where blahblahblah;
    

    

*/
