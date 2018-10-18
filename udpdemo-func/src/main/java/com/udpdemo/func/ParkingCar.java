package com.udpdemo.func;

public class ParkingCar {
    String id;
    String service_id;
    String event_type;
    String parking_id;
    String gate_id;
    String parking_floor;
    String parking_area;
    String parking_number;
    String time;
    String car_number;
    String car_type;
    String lpr_image;
    String car_image;
    String device_id;
    String operation;
    String result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getParking_id() {
        return parking_id;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }

    public String getGate_id() {
        return gate_id;
    }

    public void setGate_id(String gate_id) {
        this.gate_id = gate_id;
    }

    public String getParking_floor() {
        return parking_floor;
    }

    public void setParking_floor(String parking_floor) {
        this.parking_floor = parking_floor;
    }

    public String getParking_area() {
        return parking_area;
    }

    public void setParking_area(String parking_area) {
        this.parking_area = parking_area;
    }

    public String getParking_number() {
        return parking_number;
    }

    public void setParking_number(String parking_number) {
        this.parking_number = parking_number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getLpr_image() {
        return lpr_image;
    }

    public void setLpr_image(String lpr_image) {
        this.lpr_image = lpr_image;
    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ParkingCar{" +
                "id='" + id + '\'' +
                ", service_id='" + service_id + '\'' +
                ", event_type='" + event_type + '\'' +
                ", parking_id='" + parking_id + '\'' +
                ", gate_id='" + gate_id + '\'' +
                ", parking_floor='" + parking_floor + '\'' +
                ", parking_area='" + parking_area + '\'' +
                ", parking_number='" + parking_number + '\'' +
                ", time='" + time + '\'' +
                ", car_number='" + car_number + '\'' +
                ", car_type='" + car_type + '\'' +
                ", lpr_image='" + lpr_image + '\'' +
                ", car_image='" + car_image + '\'' +
                ", device_id='" + device_id + '\'' +
                ", operation='" + operation + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
