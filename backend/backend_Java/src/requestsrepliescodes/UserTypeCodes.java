package requestsrepliescodes;

public enum UserTypeCodes {
    DuplicatedUsers(-2),
    NotFound(-1),
    Admin(0),
    VerifiedUser(1),
    NonVerifiedUser(2),
    NotLoggedIn(3),
	
	
	InternalError(500);

    public final int ID;
    private UserTypeCodes(int ID){
        this.ID=ID;
    }
}
