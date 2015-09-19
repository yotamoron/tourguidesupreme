package com.cruv.tourguidesupreme.bean;

import java.util.List;

/**
 * Created by yotam on 9/17/15.
 */
public class TourBean {
    private int tourId;
    private int guideId;
    private String tourName;
    private List<Integer> locations;

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public List<Integer> getLocations() {
        return locations;
    }

    public void setLocations(List<Integer> locations) {
        this.locations = locations;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }
}
