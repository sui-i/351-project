# Database Design:
We have a total of 5 tables : 
- Users' Credentials
- Users' Personal Information
- Users' Reservation History
- Room details
- RoomsReservationHistory
- Settings 

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

| room_id     | solar_system| planet | hotel |room_type| num_of_beds| floor   | price_per_night | booked_until  
| :---:        |    :----:   |      :----:   |  :----:   |   :---: |   :---: |  :---:        |    :----:   |          :---: |       
|       |        |   | | | |
|       |        |   | | | |

The above table has the Room id as a unique identifier (no duplicate room ids allowed). It will have the following domains which are of types:
- room_id : int
- num_of_beds : int
- floor : int
- price_per_night  : double
- booked_until : date ( or NULL if available)
- solar_system : varchar
- room_type : bit(2)
    - It b2b1 where
    - b2= 0 if room is single
    - b2= 1 if room is double
    - b1= 0 if it is smoking or 1 if otherwise
- planet : varchar
- hotel : varchar

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

## Security : 
We will use md5 hashing to store the passwords in the database.

