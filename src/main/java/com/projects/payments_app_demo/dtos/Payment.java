package com.projects.payments_app_demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    String id;
    String fromAccount;
    String toAccount;
    BigDecimal amount;
    LocalDateTime scheduledFor;
    boolean instant;
}
