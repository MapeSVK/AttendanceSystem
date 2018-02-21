package be;

public class Student {

    private String name;
    private String attendance;
    private String percentage;
    private String takenLessons;
    private String skippedDay;
    private String months;

    public Student(String name, String attendance, String percentage, String takenLessons, String skippedDay, String months) {
        this.name = name;
        this.attendance = attendance;
        this.percentage = percentage;
        this.takenLessons = takenLessons;
        this.skippedDay = skippedDay;
        this.months = months;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getTakenLessons() {
        return takenLessons;
    }

    public void setTakenLessons(String takenLessons) {
        this.takenLessons = takenLessons;
    }

    public String getSkippedDay() {
        return skippedDay;
    }

    public void setSkippedDay(String skippedDay) {
        this.skippedDay = skippedDay;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }
}
