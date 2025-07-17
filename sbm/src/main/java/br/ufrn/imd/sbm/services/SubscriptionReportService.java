package br.ufrn.imd.sbm.services;

import br.ufrn.imd.sbm.ai.SubscriptionAgent;
import br.ufrn.imd.sbm.models.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionReportService {
    @Autowired SubscriptionTypeService subscriptionTypeService;
    @Autowired SubscriptionService subscriptionService;
    @Autowired SubscriptionAgent agent;
    public void generateSubscriptionReport() {
        Map<SubscriptionType,Double> summedPrices = new HashMap<>();
        List<SubscriptionType> subscriptionTypes = subscriptionTypeService.findAllSubscriptionTypes();
        double totalIncoming = 0.0;
        long totalSubscriptions = 0L;
        double totalAvg = 0.0;
        for (SubscriptionType subscriptionType : subscriptionTypes) {
            Long subscriptionTypeCount = subscriptionService.countBySubscriptionType(subscriptionType);
            summedPrices.put(subscriptionType, subscriptionTypeCount*subscriptionType.getPrice()*(30.0/subscriptionType.getFrequency()));
            totalIncoming += subscriptionTypeCount*subscriptionType.getPrice()*(30.0/subscriptionType.getFrequency());
            totalSubscriptions += subscriptionTypeCount;
        }
        totalAvg = totalIncoming/totalSubscriptions;
        Map<String,Double> totals = new HashMap<>();
        totals.put("totalAvg", totalAvg);
        totals.put("totalIncoming", totalIncoming);
        agent.report(totals, null, summedPrices);
    }
}
