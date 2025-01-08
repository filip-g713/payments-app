package dtos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash
public class PaymentJobUnit implements Serializable {
    @Id
    private String id;
    @Reference
    @NonNull
    private Payment payment;
    @Indexed
    private String completed;
    private String success;
}
