CREATE TABLE users_credentials (
   username  varchar(32),
   email  varchar(64),
   password  varchar(32),
   date_of_creation  timestamp,
   last_login  timestamp,
   userType  int ,
   verification_code varchar(6),
   verified boolean ,
   primary key( username)
);

CREATE TABLE users_info(
   username   varchar(32),
   first_name   varchar(32),
   last_name   varchar(32),
   phone_number    int,
   birthdate     timestamp,
   location   varchar(32),
   primary key( username)
);

CREATE TABLE users_reservation_history (
   username   varchar(32),
   room_id    varchar(16),
   reservation_date     timestamp,
   check_in     timestamp,
   check_out     timestamp,
   cancelled     timestamp,
   primary key( username)
);

CREATE TABLE room_info (
   room_id    varchar(16),
   room_type bit(2),
   num_of_beds    int,
   floor    int,
   price_per_night     double precision ,
   primary key( room_id)
);

CREATE TABLE room_reservation_history (
   reservation_id    int,
   room_id    varchar(16),
   booked_in    timestamp,
   booked_until     timestamp,
   cancelled boolean ,
   primary key( reservation_id )
);


CREATE TABLE solar_system_info(
   simple_id   varchar(4),
   simple_name   varchar(16), 
   primary key( simple_id)
);

CREATE TABLE planet_info(
   simple_id   varchar(4),
   simple_name   varchar(16), 
   primary key( simple_id )
);
CREATE TABLE hotel_info(
   simple_id   varchar(4),
   simple_name   varchar(16), 
   number_of_floors int,
   number_of_rooms int,
   primary key( simple_id )
);

CREATE TABLE users_info_deleted(
   username   varchar(32),
   first_name   varchar(32),
   last_name   varchar(32),
   phone_number    int,
   birthdate     timestamp,
   location   varchar(32)

);

CREATE TABLE users_reservation_history_deleted (
   username   varchar(32),
   room_id    varchar(16),
   reservation_date     timestamp,
   check_in     timestamp,
   check_out     timestamp,
   cancelled     timestamp
);
CREATE TABLE users_credentials_deleted (
   username  varchar(32),
   email  varchar(64),
   password  varchar(32),
   date_of_creation  timestamp,
   last_login  timestamp,
   userType  int ,
   verification_code varchar(6),
   verified boolean 
);