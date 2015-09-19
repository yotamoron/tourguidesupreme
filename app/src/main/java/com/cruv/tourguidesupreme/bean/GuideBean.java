package com.cruv.tourguidesupreme.bean;

import android.content.Intent;

import java.util.List;

/**
 * Created by yotam on 9/17/15.
 */
public class GuideBean {
    private int guideId;
    private String guideName;
    private List<Integer> tours;
    private String image;

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public List<Integer> getTours() {
        return tours;
    }

    public void setTours(List<Integer> tours) {
        this.tours = tours;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
