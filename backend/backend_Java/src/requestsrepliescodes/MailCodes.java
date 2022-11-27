package requestsrepliescodes;

public enum MailCodes {
	EmailSentSuccessfully(350),
	EmailRecipientNotFound(351),
	EmailServiceNotAvailable(352),
	
	
	InternalError(500);

	public final int ID;

	MailCodes(int i) {
		this.ID = i;
	}
}
