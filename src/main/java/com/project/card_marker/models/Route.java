package com.project.card_marker.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class Route {
    private String id;
    private String zoneName;
    private BusRoute[] busRoute;

    public Route() {
    }

    public Route(String id, String zoneName, BusRoute[] busRoute) {
        this.id = id;
        this.zoneName = zoneName;
        this.busRoute = busRoute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public BusRoute[] getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(BusRoute[] busRoute) {
        this.busRoute = busRoute;
    }
}
