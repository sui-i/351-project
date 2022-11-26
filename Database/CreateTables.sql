CREATE TABLE users_credentials (
   username  varchar(32),
   email  varchar(64),
   password  varchar(32),
   date_of_creation  timestamp,
   last_login  timestamp,
   is_active  boolean 
);

CREATE TABLE users_info(
   username   varchar(32),
   first_name   varchar(32),
   last_name   varchar(32),
   phone_number    int,
   birthdate     timestamp,
   location   varchar(32)
);

CREATE TABLE users_reservation_history (
   username   varchar(32),
   room_id    int,
   reservation_date     timestamp,
   check_in     timestamp,
   check_out     timestamp,
   cancelled     timestamp
);

CREATE TABLE room_info (
   room_id    int,
   num_of_beds    int,
   floor    int,
   price_per_night     double precision ,
   booked_until     timestamp
);

CREATE TABLE room_reservation_history (
   reservation_id    int,
   room_id    int,
   booked_in    timestamp,
   booked_until     timestamp
);