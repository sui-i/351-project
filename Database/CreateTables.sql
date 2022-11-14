CREATE TABLE UsersCredentials (
   username varchar(32) ,
   password varchar(32) ,
   email varchar(64) ,
   Date_of_creation timestamp ,
   Last_login timestamp,
   active boolean
);

CREATE TABLE UsersInfo(
   username  varchar(),
   FirstName  varchar(),
   LastName  varchar(),
   Phone Number  int,
   Birthdate  DATE,
   Location varchar()
);

CREATE TABLE UserReservation (
   username  varchar(),
   room_id  int ,
   Date of Reservation  timestamp,
   check_in  timestamp,
   check_out  timestamp,
   cancelled  timestamp 

);

CREATE TABLE RoomInfo (
   room_id  int ,
   Num_of_beds int,
   floor int,
   price_per_night  int ,
   booked_until  timestamp 
);
