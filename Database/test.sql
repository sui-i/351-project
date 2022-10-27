CREATE TABLE UsersCredentials (
   username varchar() ,
   password varchar() ,
   email varchar() ,
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
   username  varchar,
   room_id  int ,
   Date of Reservation  timestamp,
   check_in  timestamp,
   check_out  timestamp,
   cancelled  timestamp 

);
