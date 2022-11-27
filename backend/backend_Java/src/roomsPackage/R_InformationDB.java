package roomsPackage;

public class R_InformationDB {
    
    private final int ID;
    private final int number_of_beds;
    private final int floor;
    private final double price_per_night;
    private final String booked_until;
    private final String planet ;
    private final String solar_system;
    private final String hotel;
	

    public static class Builder{
        private final int ID;
        private final String booked_until;
        
        // Optional parameters - initialized to default values
        private  String planet = "";
        private  String solar_system="";
        private String hotel="";
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
        public Builder Hotel(String hotel){
            this.hotel=hotel;
            return this;
        }
        public Builder SolarSystem(String solar_system){
            this.solar_system=solar_system;
            return this;
        }
        public Builder Planet(String planet){
            this.planet=planet;
            return this;
        }
        public R_InformationDB build(){
            return new R_InformationDB(this);
        }    
    }

    private R_InformationDB(Builder B){
        ID=B.ID;number_of_beds=B.number_of_beds;floor=B.floor;price_per_night=B.price_per_night;booked_until=B.booked_until;
        hotel=B.hotel;solar_system=B.solar_system;planet=B.planet;
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
    public String getPlanet() {
		return planet;
	}
    public String getSolarSystem() {
		return solar_system;
	}
    public String getHotel() {
		return hotel;
	}

    @Override
    public String toString(){
        return "";
    }
	

}
