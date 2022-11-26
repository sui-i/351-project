package requestsrepliescodes;

public enum ReservationCodes {
	IndentityError(115),
	RoomStatusChangedSuccessfully(210),
	RoomAlreadyReserved(211),
	RoomReservationDateExceeded(212),

	RoomFoundSuccessfully(220),
	RoomIDInvalid(221),
	RoomNotFound(226),
	RoomNotReserved(225),
	HotelNotFound(224),
	PlanetNotFound(223),
	SolarSystemNotFound(222),
	
	InvalidDate(231),
	
	InternalError(500);
	
	public final int ID;

	ReservationCodes(int i) {
		this.ID = i;
	}
}
