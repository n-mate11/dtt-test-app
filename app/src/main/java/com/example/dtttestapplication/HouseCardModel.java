package com.example.dtttestapplication;

import android.graphics.Bitmap;

import java.io.Serializable;

public class HouseCardModel implements Serializable {

    private int id;
    private int price;
    private String zip;
    private String city;
    private String image;
    private int bedroom;
    private int bathroom;
    private int layers;
    private String description;
    private String createdDate;
    private double latitude;
    private double longitude;
    private double distance;

    // Constructor
    public HouseCardModel(int id, int price, String zip, String city, String image, int bedroom, int bathroom, int layers, String description, double latitude, double longitude, String createdDate, double distance) {
        this.id = id;
        this.price = price;
        this.zip = zip;
        this.city = city;
        this.image = image;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.layers = layers;
        this.description = description;
        this.createdDate = createdDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getBedroom() {
        return bedroom;
    }

    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }

    public int getBathroom() {
        return bathroom;
    }

    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }

    public int getLayers() {
        return layers;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
