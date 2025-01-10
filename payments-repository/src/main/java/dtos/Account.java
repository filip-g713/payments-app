package dtos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
public class Account implements Serializable {
    @Id
    private String id;
    private double balance;
}
