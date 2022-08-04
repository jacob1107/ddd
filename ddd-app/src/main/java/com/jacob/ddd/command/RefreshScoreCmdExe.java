package com.jacob.ddd.command;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.Assert;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.jacob.ddd.domain.metrics.SubMetric;
import com.jacob.ddd.domain.metrics.appquality.AppMetric;
import com.jacob.ddd.domain.metrics.appquality.AppQualityMetric;
import com.jacob.ddd.domain.metrics.devquality.BugMetric;
import com.jacob.ddd.domain.metrics.devquality.DevQualityMetric;
import com.jacob.ddd.domain.metrics.techcontribution.ContributionMetric;
import com.jacob.ddd.domain.metrics.techinfluence.InfluenceMetric;
import com.jacob.ddd.domain.user.UserProfile;
import com.jacob.ddd.dto.RefreshScoreCmd;
import com.jacob.ddd.event.handler.MetricItemCreatedHandler;
import com.jacob.ddd.domain.gateway.MetricGateway;
import com.jacob.ddd.domain.gateway.UserProfileGateway;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RefreshScoreCmdExe{
    private Logger logger = LoggerFactory.getLogger(MetricItemCreatedHandler.class);

    @Resource
    private UserProfileGateway userProfileGateway;

    @Resource
    private MetricGateway metricGateway;

    public Response execute(RefreshScoreCmd cmd) {
        UserProfile userProfile = getUserProfile(cmd);
        calculateScore(userProfile);
        update(userProfile);
        return Response.buildSuccess();
    }

    private UserProfile getUserProfile(RefreshScoreCmd cmd) {
        UserProfile userProfile = userProfileGateway.getByUserId(cmd.getUserId());
        Assert.notNull(userProfile, "There is no User Profile for "+cmd.getUserId()+" to update");
        return userProfile;
    }

    private void calculateScore(UserProfile userProfile) {
        loadInfluenceMetric(userProfile);
        loadContributionMetrics(userProfile);
        loadDevQualityMetrics(userProfile);
        loadAppQualityMetrics(userProfile);
        userProfile.calculateScore();
    }

    private void loadAppQualityMetrics(UserProfile userProfile) {
        AppQualityMetric appQualityMetric = new AppQualityMetric(userProfile);
        AppMetric appMetric = metricGateway.getAppMetric(userProfile.getUserId());
        appMetric.setParent(appQualityMetric);
    }

    private void loadDevQualityMetrics(UserProfile userProfile) {
        DevQualityMetric devQualityMetric = new DevQualityMetric(userProfile);
        BugMetric bugMetric = metricGateway.getBugMetric(userProfile.getUserId());
        bugMetric.setParent(devQualityMetric);
    }

    private void loadContributionMetrics(UserProfile userProfile) {
        ContributionMetric contributionMetric = new ContributionMetric(userProfile);
        List<SubMetric> subMetricList = metricGateway.listByTechContribution(userProfile.getUserId());
        subMetricList.forEach(subMetric -> subMetric.setParent(contributionMetric));
    }

    private void loadInfluenceMetric(UserProfile userProfile) {
        InfluenceMetric influenceMetric = new InfluenceMetric(userProfile);
        List<SubMetric> subMetricList = metricGateway.listByTechInfluence(userProfile.getUserId());
        subMetricList.forEach(subMetric -> subMetric.setParent(influenceMetric));
    }

    private void update(UserProfile userProfile) {
        userProfileGateway.update(userProfile);
    }
}
