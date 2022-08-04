package com.jacob.ddd.domain.metrics.appquality;

import com.jacob.ddd.domain.metrics.MainMetric;
import com.jacob.ddd.domain.metrics.MainMetricType;
import com.jacob.ddd.domain.metrics.devquality.BugMetric;
import com.jacob.ddd.domain.user.UserProfile;

public class AppQualityMetric extends MainMetric {

    private AppMetric appMetric;

    public AppQualityMetric(UserProfile metricOwner){
        this.metricOwner = metricOwner;
        metricOwner.setAppQualityMetric(this);
        this.metricMainType = MainMetricType.APP_QUALITY;
    }

    @Override
    public double getWeight() {
        return metricOwner.getWeight().getAppQualityWeight();
    }
}
