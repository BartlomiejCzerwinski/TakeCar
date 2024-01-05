package com.example.myapplication.takecar;

public class Rental {
    private String totalPrice;
    private String startTime;
    private String endTime;
    private String takerID;
    private String ownerID;
    private String carID;

    public Rental() {
    }

    public int getTotalPrice() {
        return Integer.parseInt(totalPrice);
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

    public String getTakerID() {
        return takerID;
    }

    public void setTakerID(String takerID) {
        this.takerID = takerID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "totalPrice='" + totalPrice + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", takerID='" + takerID + '\'' +
                ", ownerID='" + ownerID + '\'' +
                ", carID='" + carID + '\'' +
                '}';
    }
}
