package sample;

public class StockPrice {
    private double value;
    private Time time;

    //constructor
    public StockPrice(double value, Time time) {
        this.value = value;
        this.time = time;
    }

    //empty constructor
    public StockPrice() {

    }

    //getters and setters
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String toString() {
        return "Stock Price: (value: " + getValue() + ", time: " + getTime() + ")";
    }

}
