package com.dataaccess.finalactivityMPS2627.model;

import java.time.LocalTime;

public class Flights {

    private String flightCode;
    private int source;
    private int destination;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int duration;
    private int airlineId;
    private int distance;
    private int plane;

    public Flights() {
    }

    public Flights(String flightCode, int source, int destination, LocalTime departureTime, LocalTime arrivalTime, int duration, int airlineId, int distance, int plane) {
        this.flightCode = flightCode;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.airlineId = airlineId;
        this.distance = distance;
        this.plane = plane;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(int airlineId) {
        this.airlineId = airlineId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPlane() {
        return plane;
    }

    public void setPlane(int plane) {
        this.plane = plane;
    }

}


