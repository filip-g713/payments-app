package dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash
public class Payment implements Serializable {
    @Id
    String id;
    String fromAccount;
    String toAccount;
    BigDecimal amount;
    LocalDateTime scheduledFor;
    boolean instant;
}
