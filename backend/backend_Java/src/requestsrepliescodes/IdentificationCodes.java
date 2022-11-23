package requestsrepliescodes;

public enum IdentificationCodes {
	LoginSuccessul(110),
	EmailNotVerified(111),
	UsernameNotFound(112),
	WrongPassword(113),
	
	RegistrationSuccessul(120),
	EmailNotAvailable(121),
	EmailAlreadyExists(122),
	UsernameAlreadyExists(123),
	
	VerificationSuccessful(130),
	WrongVerrificationCode(131),
	UserAlreadyVerified(132);

	final int ERRID;

	IdentificationCodes(int i) {
		this.ERRID = i;
	}
	
	
}
