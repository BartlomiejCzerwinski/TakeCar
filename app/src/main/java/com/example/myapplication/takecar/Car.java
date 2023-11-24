package com.example.myapplication.takecar;

import android.net.Uri;

import java.util.List;

public class Car {
    private String producer;
    private String model;
    private int year;
    private int power;
    private int doors;
    private int places;
    private String plateNumber;
    private String vin;
    private boolean isAirConditioner;
    private String gearbox;
    private int hourlyPrice;
    private int dailyPrice;
    private List<Uri> photosUris;

    @Override
    public String toString() {
        return "Car{" +
                "producer='" + producer + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", power=" + power +
                ", doors=" + doors +
                ", places=" + places +
                ", plateNumber='" + plateNumber + '\'' +
                ", vin='" + vin + '\'' +
                ", isAirConditioner=" + isAirConditioner +
                ", gearbox='" + gearbox + '\'' +
                ", hourlyPrice=" + hourlyPrice +
                ", dailyPrice=" + dailyPrice +
                ", photosUris=" + photosUris +
                '}';
    }

    public List<Uri> getPhotosUris() {
        return photosUris;
    }

    public void setPhotosUris(List<Uri> photosUris) {
        this.photosUris = photosUris;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String isAirConditioner() {
        return String.valueOf(isAirConditioner);
    }

    public void setAirConditioner(boolean airConditioner) {
        isAirConditioner = airConditioner;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public int getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(int hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    public int getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(int dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
}
