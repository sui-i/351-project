# Database Design:
We have a total of 5 tables : 
- Users' Credentials
- Users' Personal Information
- Users' Reservation History
- Room details
- Settings 

## Users' Credentials 

| Username      | Email | Password   | Date of Creation | Last login | Active User
| :---:        |    :----:   |          :---: |  :---:        |    :----:   |          :---: |
|       |        |   | | | |
|       |        |   | | | |

The above table has the username as a unique identifier (no duplicate usernames allowed). It will have the following domains which are of types:
- username : varchar
- Email : varchar
- Password : varchar
- Date of Creation : timestamp
- Last Login : timestamp
- Active User : boolean 


## Users' Information 

| Username      | FirstName | LastName   | Phone Number | Birthdate | Location
| :---:        |    :----:   |          :---: |  :---:        |    :----:   |          :---: |
|       |        |   | | | |
|       |        |   | | | |

The above table has the username as a unique identifier (no duplicate usernames allowed). It will have the following domains which are of types:
- username : varchar
- FirstName : varchar
- LastName : varchar
- Phone Number : int
- Birthdate : date
- Location : varchar


## Users' Reservation History

| Username      | Room id | Date of Reservation   | Check in | Check out | Cancelled
| :---:        |    :----:   |          :---: |  :---:        |    :----:   |          :---: |
|       |        |   | | | |
|       |        |   | | | |

The above table has the username as a unique identifier (no duplicate usernames allowed). It will have the following domains which are of types:
- username : varchar
- Room id : int
- Date of Reservation : date
- Check in : date
- Check out  : date
- Cancelled : date ( or NULL if not cancelled)


## Rooms

| Room id      | Number of beds| floor   | Price per night | Availab | Booked until
| :---:        |    :----:   |          :---: |  :---:        |    :----:   | :----:   |        
|       |        |   | | | |
|       |        |   | | | |

The above table has the Room id as a unique identifier (no duplicate room ids allowed). It will have the following domains which are of types:
- Room id : int
- Number of beds : int
- Floor : int
- Price per night  : double
- Booked until : date ( or NULL if available)



