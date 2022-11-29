package DataBasePackage;

import requestsrepliescodes.UserTypeCodes;

public class DB_UserInformation {

	private String username;
	private String first_name;
	private String last_name;
	private String phone_number;
	private String birthdate;
	private String location;
	private String email;
	private String password;
	private String date_of_creation;
	private String last_login;
	private UserTypeCodes usertype;

    public static class Builder{

		private String username;
		private String first_name;
		private String last_name;
		private String phone_number;
		private String birthdate;
		private String location;
		private String email;
		private String password;
		private String date_of_creation;
		private String last_login;
		private UserTypeCodes usertype;

        public Builder(String username,String firstName,String lastName){
			this.username= username;
			this.first_name=firstName;
			this.last_name=lastName;
        }
        public Builder PhoneNumber(String phoneNumber){
            this.phone_number=phoneNumber;
            return this;
        }
        public Builder Birthdate(String birthdate){
            this.birthdate=birthdate;
            return this;
        }
        public Builder Location(String location){
            this.location=location;
            return this;
        }
        public Builder Email(String email){
            this.email=email;
            return this;
        }
        public Builder Password(String password){
            this.password=password;
            return this;
        }
        public Builder DateOfCreation(String date_of_creation){
            this.date_of_creation=date_of_creation;
            return this;
        }
		public Builder LastLogin(String last_login){
			this.last_login=last_login;
			return this;
		}
		public Builder UserType (UserTypeCodes usertype){
			this.usertype=usertype;
			return this;
		}

        public DB_UserInformation build(){
            return new DB_UserInformation(this);
        }    
    }

    private DB_UserInformation(Builder B){
		username=B.username;first_name= B.first_name;last_name=B.last_name;phone_number=B.phone_number;
		birthdate=B.birthdate;location=B.location;email=B.email;password=B.password;date_of_creation=B.date_of_creation;
		last_login=B.last_login;usertype=B.usertype;

    }
	public String getuserName() {
		return username;
	}
	public String getfirstName() {
		return first_name;
	}
	public String getlastName() {
		return last_name;
	}
	public String getphoneNumber() {
		return phone_number;
	}
	public String getbirthDate() {
		return birthdate;
	}
	public String getLocation() {
		return location;
	}
	public String getEmail() {
		return email;
	}
	public String getPasswordHash() {
		return password;
	}

	public String getDateOfCreation(){
		return date_of_creation;
	}

	public String getLastLogin(){
		return last_login;
	}

	public UserTypeCodes getUserType(){
		return usertype;
	}

    @Override
    public String toString(){
        return "";
    }
	

}
