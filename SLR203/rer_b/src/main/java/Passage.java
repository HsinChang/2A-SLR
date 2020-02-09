public class Passage {
    private String vehicleName;
    private String lineDirection;
    private int sens;
    private String code;
    private int time;
    private String schedule;

    public String getVehicleName() {
        return vehicleName;
    }

    public String getLineDirection() {
        return lineDirection;
    }

    public int getSens() {
        return sens;
    }

    public int getTime() {
        return time;
    }

    public String getCode() {
        return code;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLineDirection(String lineDirection) {
        this.lineDirection = lineDirection;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
