package com.example.myapplication.takecar;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Car implements Serializable {
    private String ID;
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
    private List<String> photosUris;

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

    public Car(String ID, String producer, String model, int year, int power, int doors, int places, String plateNumber, String vin, boolean isAirConditioner, String gearbox, int hourlyPrice, int dailyPrice) {
        this.ID = ID;
        this.producer = producer;
        this.model = model;
        this.year = year;
        this.power = power;
        this.doors = doors;
        this.places = places;
        this.plateNumber = plateNumber;
        this.vin = vin;
        this.isAirConditioner = isAirConditioner;
        this.gearbox = gearbox;
        this.hourlyPrice = hourlyPrice;
        this.dailyPrice = dailyPrice;
    }

    public Car() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<Uri> getPhotosUris() {
        List<Uri> result = new ArrayList<Uri>();
        for (String uri:
             photosUris) {
            result.add(Uri.parse(uri));
        }
        return result;
    }

    public void setPhotosUris(List<Uri> photosUris) {
        List<String> result = new ArrayList<String>();
        for (Uri uri:
             photosUris) {
            result.add(uri.toString());
        }
        this.photosUris = result;
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

    public String getOwnerID() {
        return ID.substring(0, ID.indexOf('_'));
    }

    public int getRentalTotalPrice(int hours, int days) {
        return (hours * hourlyPrice) + (days * dailyPrice);
    }
}
