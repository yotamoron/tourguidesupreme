package com.cruv.tourguidesupreme.db;

import com.cruv.tourguidesupreme.Tour;
import com.cruv.tourguidesupreme.bean.DbBean;
import com.cruv.tourguidesupreme.bean.GuideBean;
import com.cruv.tourguidesupreme.bean.LocationBean;
import com.cruv.tourguidesupreme.bean.TourBean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yotam on 9/17/15.
 */
public class DbUtils {
    public static GuideBean byGuideId(final DbBean dbBean, final Integer guideId) {
        for (final GuideBean guideBean : dbBean.getGuides()) {
            if (guideBean.getGuideId() == guideId) {
                return guideBean;
            }
        }
        throw new RuntimeException("Guide not found " + guideId);
    }

    public static TourBean byTourId(DbBean dbBean, int tourId) {
        for (final TourBean tourBean : dbBean.getTours()) {
            if (tourBean.getTourId() == tourId) {
                return tourBean;
            }
        }
        throw new RuntimeException("Tour not found " + tourId);
    }

    public static List<LocationBean> allLocationsByTourId(DbBean dbBean, int tourId) {
        final List<LocationBean> locations = new LinkedList<>();
        final TourBean tourBean = byTourId(dbBean, tourId);

        for (final int locationId : tourBean.getLocations()) {
            locations.add(byLocationId(dbBean, locationId));
        }
        return locations;
    }

    public static LocationBean byLocationId(DbBean dbBean, int locationId) {
        for (final LocationBean locationBean : dbBean.getLocations()) {
            if (locationBean.getLocationId() == locationId) {
                return locationBean;
            }
        }
        throw new RuntimeException("Location not found " + locationId);
    }

    public static String[] tourNames(DbBean dbBean) {
        final List<String> names = new LinkedList<>();

        for (final TourBean tourBean : dbBean.getTours()) {
            names.add(tourBean.getTourName());
        }

        final String[] namesArray = new String[names.size()];
        return names.toArray(namesArray);
    }

    public static String[] guideNames(DbBean dbBean) {
        final List<String> names = new LinkedList<>();

        for (final GuideBean guideBean : dbBean.getGuides()) {
            names.add(guideBean.getGuideName());
        }

        final String[] namesArray = new String[names.size()];
        return names.toArray(namesArray);
    }
}
