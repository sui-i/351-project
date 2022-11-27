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


    public TimeStamp addTime(TimeStamp other){
        //Let's recap the one bit adders
        double [] first = {seconds,minutes,hours};
        double [] second = {other.getSeconds(),other.getMinutes(),other.getHours()};
        double [] result = {0,0,0};
        int [] remainder = {60,60,24};
        double [] carry ={0,0,0,0}; 
        for(int i=0;i<3;i++){
            if (i!=0){
                result[i] = (first[i] + second[i] +carry[i-1])%remainder[i];
                carry[i]= (int)(first[i] + second[i] +carry[i-1])/remainder[i];
            }
            else{
                result[i] = (first[i] + second[i] )%remainder[i];
            }
            

            

        }

        
        return new TimeStamp("");
    }
    public static void main(String[] args) {
        TimeStamp x= new TimeStamp("2222-12-23 19:23:12.23124");
        TimeStamp y= new TimeStamp("2222-12-23 0:45:12.23124");
        x.addTime(y);
    }
    public TimeStamp add(TimeStamp other){
        /*
         * YYYY-MM-DD HH-MM-SS
         * +
         * YYYY-
         */
        return new TimeStamp("");
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
