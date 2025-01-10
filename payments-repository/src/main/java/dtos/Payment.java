package dtos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash
public class Payment implements Serializable {
    @Id
    String id;
    @NonNull
    String fromAccountId;
    @NonNull
    String toAccountId;
    double amount;
    long scheduledFor;
    private String completed;
    private String success;
}
