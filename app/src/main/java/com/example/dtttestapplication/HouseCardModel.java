package com.example.dtttestapplication;

public class HouseCardModel {

    private int price;
    private String address;
    private int image;
    private int bedroom;
    private int bathroom;
    private int layers;
    private String distance;

    // Constructor
    public HouseCardModel(int price, String address, int image, int bedroom, int bathroom, int layers, String distance) {
        this.price = price;
        this.address = address;
        this.image = image;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.layers = layers;
        this.distance = distance;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
