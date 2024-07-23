package com.meesho.notification_system.documents;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName="sms")
public class SmsElastic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phoneNumber;

    private String message;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Nullable
    private Integer failureCode;

    private String failureComments;

    private Date updatedAt;

    public enum Status {
        SENT,
        FAILED,
        IN_PROGRESS
    }
}
