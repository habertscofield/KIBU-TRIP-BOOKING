package com.example.kibutripbooking.Admin;

public class Driver {
    String driverId;
    String driverNameI;
    String busNumberI;
    String address;
    String reg;
    String phone;
    String license;

    public Driver() {
    }


    public Driver(String driverId, String driverNameI, String busNumberI, String address, String reg, String phone, String license) {
        this.driverId = driverId;
        this.driverNameI = driverNameI;
        this.busNumberI = busNumberI;
        this.address = address;
        this.reg = reg;
        this.phone = phone;
        this.license = license;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverNameI() {
        return driverNameI;
    }

    public void setDriverNameI(String driverNameI) {
        this.driverNameI = driverNameI;
    }

    public String getBusNumberI() {
        return busNumberI;
    }

    public void setBusNumberI(String busNumberI) {
        this.busNumberI = busNumberI;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
