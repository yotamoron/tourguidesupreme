package com.cruv.tourguidesupreme.bean;

import java.util.List;

/**
 * Created by yotam on 9/17/15.
 */
public class DbBean {
    private List<GuideBean> guides;
    private List<TourBean> tours;
    private List<LocationBean> locations;

    public List<GuideBean> getGuides() {
        return guides;
    }

    public void setGuides(List<GuideBean> guides) {
        this.guides = guides;
    }

    public List<TourBean> getTours() {
        return tours;
    }

    public void setTours(List<TourBean> tours) {
        this.tours = tours;
    }

    public List<LocationBean> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationBean> locations) {
        this.locations = locations;
    }
}
