package sample;

public class PendingOrdersForAlgorythm {
    private String nameOfTheUser;
    private String nameOfTheCompany;
    private boolean buyOrSell;
    private int amount;
    private Time time;
    private boolean canOperate;

    //constructor
    public PendingOrdersForAlgorythm(String nameOfTheUser, String nameOfTheCompany, boolean buyOrSell, int amount, Time time, boolean canOperate) {
        this.nameOfTheUser = nameOfTheUser;
        this.nameOfTheCompany = nameOfTheCompany;
        this.buyOrSell = buyOrSell;
        this.amount = amount;
        this.time = time;
        this.canOperate = canOperate;
    }

    //empty constructor
    public PendingOrdersForAlgorythm(){

    }

    //getters and setters
    public String getNameOfTheUser() {
        return nameOfTheUser;
    }

    public void setNameOfTheUser(String nameOfTheUser) {
        this.nameOfTheUser = nameOfTheUser;
    }

    public String getNameOfTheCompany() {
        return nameOfTheCompany;
    }

    public void setNameOfTheCompany(String nameOfTheCompany) {
        this.nameOfTheCompany = nameOfTheCompany;
    }

    public boolean isBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(boolean buyOrSell) {
        this.buyOrSell = buyOrSell;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public boolean isCanOperate() {
        return canOperate;
    }

    public void setCanOperate(boolean canOperate) {
        this.canOperate = canOperate;
    }

    @Override
    public String toString() {
        return "PendingOrdersForAlgorythm{" +
                "nameOfTheUser='" + nameOfTheUser + '\'' +
                ", nameOfTheCompany='" + nameOfTheCompany + '\'' +
                ", buyOrSell=" + buyOrSell +
                ", amount=" + amount +
                ", time=" + time +
                ", canOperate=" + canOperate +
                '}';
    }
}
