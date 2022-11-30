/*
    checkLoginCredentials :
    
    1. Select username , password from users_credentials where username = username ;


    RegisterUser:
    2. INSERT INTO users_credentials (username,email,password,date_of_creation,last_login,is_active)
        VALUES  ('%s','%s','%s',NOW(),NOW(),true ) ;

    Check Reservation :
        SELECT * FROM room_reservation_history where NOT ((booked_in <= EndB)  and  (booked_out >= StartB)) ; 
    
    SELECT count(*) from table where blahblahblah;
    
    // INNER JOIN
    select * from room_info INNER JOIN planet_info ON SUBSTRING(room_id from 5 for 4)=planet_info.simple_id;

    select * from room_info INNER JOIN planet_info,solar_system_info,hotel_info ON SUBSTRING(room_id from 5 for 4)=planet_info.simple_id;

    SELECT room_info.* , planet_info.simple_name as "planet_name", solar_system_info.simple_name as "solar_system_name" ,planet_info.simple_name as "planet_name" from room_info 
    INNER JOIN planet_info on SUBSTRING(room_id from 5 for 4)=planet_info.simple_id
    INNER JOIN solar_system_info  on SUBSTRING(room_id from 1 for 4)=solar_system_info.simple_id
    INNER JOIN hotel_info  on SUBSTRING(room_id from 9 for 4)=hotel_info.simple_id;

    room_type | num_of_beds | floor | price_per_night

    SELECT users_reservation_history.* ,  solar_system_info.simple_name as "solar_system_name" , planet_info.simple_name as "planet_name", hotel_info.simple_name as "hotel_name" from users_reservation_history 
    INNER JOIN room_info on users_reservation_history.room_id=room_info.room_id
    INNER JOIN planet_info on SUBSTRING(room_info.room_id from 5 for 4)=planet_info.simple_id
    INNER JOIN solar_system_info  on SUBSTRING(room_info.room_id from 1 for 4)=solar_system_info.simple_id
    INNER JOIN hotel_info  on SUBSTRING(room_info.room_id from 9 for 4)=hotel_info.simple_id 
    WHERE username='%s';



   
*/
