package repository;

import dtos.PaymentJobUnit;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

@Component
public class InstantPaymentQueue {

    private final RBlockingQueue<PaymentJobUnit> queue;

    public InstantPaymentQueue() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        RedissonClient redisson = Redisson.create(config);
        queue = redisson.getBlockingQueue("instantPaymentsQueue");
    }

    public PaymentJobUnit getNext() {
        return queue.poll();
    }

    public void putNext(PaymentJobUnit paymentJobUnit) {
        queue.offer(paymentJobUnit);
    }
}
