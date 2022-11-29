package requestsrepliescodes;

public enum UserTypeCodes {
    
    Admin(0),
    VerifiedUser(1),
    NonVerifiedUser(2);

    public final int ID;
    private UserTypeCodes(int ID){
        this.ID=ID;
    }
}
