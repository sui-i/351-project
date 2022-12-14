package requestsrepliescodes;

/**
 * set of functions that checks for validity of identitification credentials.
 * */
public class ValidateSynthax {
	public static boolean checkUsername(String username) {
		return !username.contains(" ") && !username.contains(",");
	}
	public static boolean checkPassword(String password) {
		return password.length()>7 && !password.contains(",");
	}
	public static boolean checkEmail(String email) {
		//uses Regular expressions to validate the email.
		return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
	}
	public static boolean checkName(String name) {
		//uses Regular expressions to validate the email.
		return name.matches("^[a-zA-Z+ ]+");
	}
	public static boolean checkTime(String time) {
		return time.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$");
	}

	
}
