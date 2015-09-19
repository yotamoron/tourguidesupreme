package com.cruv.tourguidesupreme.bean;

import java.util.List;

/**
 * Created by yotam on 9/17/15.
 */
public class LocationBean {
    private int locationId;
    private String locationName;
    private List<LinkBean> links;
    private String videoId;
    private String geo;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public List<LinkBean> getLinks() {
        return links;
    }

    public void setLinks(List<LinkBean> links) {
        this.links = links;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }
}
