package roomsPackage;

public class R_InformationDB {
    
    private final int ID;
    private final int number_of_beds;
    private final int floor;
    private final double price_per_night;
    private final String booked_until;
	
	

    public static class Builder{
        private final int ID;
        private final String booked_until;
        // Optional parameters - initialized to default values
        private  int number_of_beds=1;
        private  int floor=0;
        private  double price_per_night=0;
        
            
        public Builder(int ID,String booked_until){
            this.ID=ID;
            this.booked_until=booked_until;
        }
        public Builder NumOfBeds(int number_of_beds){
            this.number_of_beds=number_of_beds;
            return this;
        }
        public Builder Floor(int floor){
            this.floor=floor;
            return this;
        }
        public Builder PricePerNight(double price_per_night){
            this.price_per_night=price_per_night;
            return this;
        }
        
        public R_InformationDB build(){
            return new R_InformationDB(this);
        }    
    }

    private R_InformationDB(Builder B){
        ID=B.ID;number_of_beds=B.number_of_beds;floor=B.floor;price_per_night=B.price_per_night;booked_until=B.booked_until;
    }
	public int getID() {
		return ID;
	}
	public int getNumOfBeds() {
		return number_of_beds;
	}
	public int getFloor() {
		return floor;
	}
	public double getPricePerNight() {
		return price_per_night;
	}
	public String getBookedUntil() {
		return booked_until;
	}
	

}
