package com.meesho.notification_system.services;
import org.springframework.data.domain.Page;
import com.meesho.notification_system.documents.SmsElastic;

import java.util.List;

public interface ElasticSearchService {
    public List<SmsElastic> getSms(long startMillis, long endMillis, String status);
    public Page<SmsElastic> searchMessages(String searchText, int page, int size);
}
