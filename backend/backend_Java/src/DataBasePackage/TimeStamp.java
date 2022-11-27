package DataBasePackage;

public class TimeStamp implements Comparable<TimeStamp>{
    
    private int year;
    private int month;
    private int day;
    private int hours;
    private int minutes;
    private double seconds;
    
    public int getYear(){
        return year;
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
    public int getHours(){
        return hours;
    }
    public int getMinutes(){
        return minutes;
    }
    public double getSeconds(){
        return seconds;
    }

    /**
     * 
     * @param dateTime: String in the form "YYYY-MM-DD HH:MM:SS.MS"
     */
    public TimeStamp(String dateTime){
        //Here we are assuming the dataBase type of the booked_until is timestamp YYYY-MM-DD HH:MM:SS.MS
        // TO-DO: Handle exceptions when dateTime isn't legit
        String [] sSplit = dateTime.split(" ");
        String date=sSplit[0];
        String time= sSplit[1];
        String[] dateStr = date.split("-");
        String[] timeStr = time.split(":");
        
        year= Integer.valueOf(dateStr[0]);
        month=Integer.valueOf(dateStr[1]);
        day= Integer.valueOf(dateStr[2]);

        hours=Integer.valueOf(timeStr[0]);;
        minutes=Integer.valueOf(timeStr[1]);;
        seconds=Double.valueOf(timeStr[2]);;



    }
    @Override
    public String toString() {
    	return String.format("%s-%s-%s %s:%s:%s", getYear(),getMonth(),getDay(),getHours(),getMinutes(),getSeconds());
    }

    private TimeStamp (Builder builder){
        this.year=builder.year;this.month=builder.month;this.day=builder.day; 
        this.hours=builder.hours;this.minutes=builder.minutes;this.seconds=builder.seconds;
    }


    
    public TimeStamp add(TimeStamp other){
    	//TODO:
        return other;
    }


    public static class Builder{
        private int year;
        private int month;
        private int day;
        //Optional Shit
        private int hours;
        
        private int minutes;
        private double seconds;
            
            
        public Builder(int year, int month,int day){
            if(year<=0 || month <=0 || day<=0 || month>12 || day>31) {}
            //Handle 31 and shit

            this.year=year;
            this.month=month;
            this.day=day;
        }
        public Builder Hour(int hour){
            this.hours = hour % 24;
            return this;
        }
        public Builder Minute(int minute){
            this.minutes = minute%60;
            return this;
        }
        public Builder Second(int second){
            this.seconds = second%60;
            return this;
        }
        
        public TimeStamp build(){
            return new TimeStamp(this);
        }    
    }


    @Override
    public int compareTo(TimeStamp o) {
        
        if(getYear()!=o.getYear()){
            return Integer.compare(getYear(),o.getYear());
        }
        if(getMonth()!=o.getMonth()){
            return Integer.compare(getMonth(),o.getMonth());
        }
        if(getDay()!=o.getDay()){
            return Integer.compare(getDay(),o.getDay());
        }
        if(getHours()!= o.getHours()){
            return Integer.compare(getHours(),o.getHours());
        }
        if(getMinutes()!= o.getMinutes()){
            return Integer.compare(getMinutes(),o.getMinutes());
        }
        if(getSeconds()!=o.getSeconds()){
            return Double.compare(getSeconds(),o.getSeconds());
        }
        return 0;
    }


}
