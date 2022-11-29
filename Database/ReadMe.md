# Database Design:
We have a total of 8 tables : 
- users_credentials
- users_info
- users_reservation_history
- room_info
- room_reservation_history
- solar_system_info
- planet_info
- hotel_info

## users_credentials 

| username      | email | password   | date_of_creation | last_login | userType
| :---:        |    :----:   |          :---: |  :---:        |    :----:   |          :---: |
|       |        |   | | | |
|       |        |   | | | |

The above table has the username as a unique identifier (no duplicate usernames allowed). It will have the following domains which are of types:
- username : varchar
- email : varchar
- password : varchar
- date_of_creation : timestamp
- last_login : timestamp
- userType : int  


## users_info

| username      | first_name | last_name   | phone_number | birthdate | location
| :---:        |    :----:   |          :---: |  :---:        |    :----:   |          :---: |
|       |        |   | | | |
|       |        |   | | | |

The above table has the username as a unique identifier (no duplicate usernames allowed). It will have the following domains which are of types:
- username : varchar
- first_name : varchar
- last_name : varchar
- phone_number : int
- birthdate : date
- location : varchar


## users_reservation_history

| username      | room_id | reservation_date   | check_in | check_out | cancelled
| :---:        |    :----:   |          :---: |  :---:        |    :----:   |          :---: |
|       |        |   | | | |
|       |        |   | | | |

The above table has the username as a unique identifier (no duplicate usernames allowed). It will have the following domains which are of types:
- username : varchar
- room_id : int
- reservation_date : date
- check_in : date
- check_out : date
- cancelled : date ( or NULL if not cancelled)


## room_info

| room_id      |room_type| num_of_beds| floor   | price_per_night  
 :----:   |   :---: |   :---: |  :---:        |    :----:   |          
|       |        |   | | | |
|       |        |   | | | |

The above table has the Room id as a unique identifier (no duplicate room ids allowed). It will have the following domains which are of types:
- room_id : varchar(16) of format "SSSSPPPPHHHHRMRM":
    - "SSSS" : short for the solar system
    - "PPPP" : short for the planet
    - "HHHH" : short for the hotel name
    - "RMRM" : short for the room number
- num_of_beds : int
- floor : int
- price_per_night  : double

- room_type : bit(2)
    - It b2b1 where
    - b2= 0 if room is single
    - b2= 1 if room is double
    - b1= 0 if it is smoking or 1 if otherwise

## room_reservation_history

| reservation_id     | room_id| booked_in | booked_until | cancelled
| :---:        |    :----:  |          :---:  |          :---: |  :---:        |        
|       |        |   | |
|       |        |   | | 

The above table has the Room id as a unique identifier (no duplicate room ids allowed). It will have the following domains which are of types:
- reservation_id : int
- room_id : int
- booked_in : timestamp
- booked_until : date ( or NULL if available)
- cancelled : boolean


## solar_system_info

| simple_id     | simple_name
| :---:        |    :----:  |        

The above table has the simple_id as a unique identifier (no duplicate room ids allowed). It will have the following domains which are of types:
- simple_id : varchar(4)
- simple_name : varchar(16)

## planet_info

| simple_id     | simple_name
| :---:        |    :----:  |        

The above table has the simple_id as a unique identifier (no duplicate room ids allowed). It will have the following domains which are of types:
- simple_id : varchar(4)
- simple_name : varchar(16)


## hotel_info

| simple_id     | simple_name | number_of_floors|number_of_rooms
| :---:        |    :----:  |     :----:  |  :----:  |  

The above table has the simple_id as a unique identifier (no duplicate room ids allowed). It will have the following domains which are of types:
- simple_id : varchar(4)
- simple_name : varchar(16)
- number_of_floors : int 
- number_of_rooms : int 



## Security : 
We will use md5 hashing to store the passwords in the database.

