package com.vou.users.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "brands")
@DiscriminatorValue("brand")
public class Brand extends User {
    // annotate the class as an entity and map to db table

    // define the fields

    // annotate the fields with db column names

    // create constructors

    // generate getter/setter methods

    // generate toString() method

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "field")
    private String field;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    public Brand() {
    }


    public Brand(String brandName, String field, String address, double latitude, double longitude, boolean status) {
        this.brandName = brandName;
        this.field = field;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Brand(String brandName, String field, String address, double latitude, double longitude) {
        this.brandName = brandName;
        this.field = field;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Brand(String fullName, String username, String accountId, String email, String phone, UserRole role, boolean status, String brandName, String field, String address, double latitude, double longitude) {
        super(fullName, username, accountId, email, phone, role, status);
        this.brandName = brandName;
        this.field = field;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "Brand{" +
                "brandName='" + brandName + '\'' +
                ", field='" + field + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

