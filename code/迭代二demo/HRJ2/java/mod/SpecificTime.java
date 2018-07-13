package mod;

public class SpecificTime {
    // e.g. Range:1-7, 1 represents Sunday, 2 represents Monday
    private int day;
    // e.g. using format like 08:00 , 13:50 etc.
    private String startTime;
    private String endTime;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
