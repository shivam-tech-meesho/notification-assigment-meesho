package com.meesho.notification_system.repositories;

import com.meesho.notification_system.documents.SmsElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.annotations.Query;
import java.util.List;

public interface SmsElasticRepo extends ElasticsearchRepository<SmsElastic, String> {

    @Query("""
            {
                "bool": {
                  "must": [
                    {
                      "range": {
                        "updatedAt": {
                          "gte": #{#startMillis},
                          "lte": #{#endMillis},
                          "format": "epoch_millis"
                        }
                      }
                    },
                    {
                      "term": {
                        "status.keyword": "#{#status}"
                      }
                    }
                  ]
                }
              }""")
    List<SmsElastic> findByCreatedAtBetweenAndStatus(long startMillis, long endMillis, String status);




    @Query("""
            {
              "bool": {
                "must": [
                  {
                    "match": {
                      "message": "#{#text}"
                    }
                  }
                ]
              }
            }
            """)
    Page<SmsElastic> findByMessageContaining(String text, Pageable pageable);
}
