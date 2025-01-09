package repository;

import dtos.PaymentJobUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentJobRedisRepository extends CrudRepository<PaymentJobUnit, String> {
    List<PaymentJobUnit> findAllByScheduledFor(long scheduledFor);
}
