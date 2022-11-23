package com.kgisl.demo1.Entity;

public class Car {
    private int carId;
    private String brand;
    private String carName;
    private float price;
    public Car(int carId, String brand, String carName, float price) {
        this.carId = carId;
        this.brand = brand;
        this.carName = carName;
        this.price = price;
    }
    public Car(String brand, String carName, float price) {
        this.brand = brand;
        this.carName = carName;
        this.price = price;
    }
    public int getCarId() {
        return carId;
    }
    public void setCarId(int carId) {
        this.carId = carId;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getCarName() {
        return carName;
    }
    public void setCarName(String carName) {
        this.carName = carName;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Car [Car Id = " + carId + ", Brand = " + brand + ", Car Name = " + carName + ", Price = " + price + "]";
    }

    
}
