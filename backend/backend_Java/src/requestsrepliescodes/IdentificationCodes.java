package requestsrepliescodes;

public enum IdentificationCodes {
	LoginSuccessful(110),
	EmailNotVerified(111),
	UsernameNotFound(112),
	WrongPassword(113),
	
	RegistrationSuccessul(120),
	EmailSendingError(121),
	EmailAlreadyExists(122),
	UsernameAlreadyExists(123),
	
	VerificationSuccessful(130),
	WrongVerrificationCode(131),
	UserAlreadyVerified(132),
	
	LogoutSuccessful(140),
	
	InternalError(500);

	public final int ID;
	IdentificationCodes(int i) {
		this.ID = i;
	}
}
