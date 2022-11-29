package requestsrepliescodes;

public enum ReservationCodes {
	IndentityError(115),
	RoomStatusChangedSuccessfully(210),
	RoomAlreadyReserved(211),
	RoomReservationtimeInvalid(212),
	RoomReservationInvalid(213),

	RoomRechedulingFailed(241),

	RoomFoundSuccessfully(220),
	RoomIDInvalid(221),
	RoomNotFound(226),
	RoomAvailable(227),
	RoomNotReserved(225),
	HotelNotFound(224),
	PlanetNotFound(223),
	SolarSystemNotFound(222),
	
	InvalidDateFormat(231),
	
	InternalError(500);
	
	public final int ID;

	ReservationCodes(int i) {
		this.ID = i;
	}
}
