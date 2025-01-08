package com.projects.paymentsworker.scheduler;

import dtos.PaymentJobUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import repository.InstantPaymentQueue;
import repository.PaymentJobRedisRepository;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentsSchedulerService {

    private final PaymentJobRedisRepository paymentJobRedisRepository;
    private final InstantPaymentQueue instantPaymentQueue;

    public void processScheduledPayments() {
        List<PaymentJobUnit> incompletePaymentJobs = paymentJobRedisRepository.findAllByCompleted("false");
        incompletePaymentJobs.forEach(this::completePaymentJob);
        paymentJobRedisRepository.saveAll(incompletePaymentJobs);
    }

    public void processInstantPayments() {
        PaymentJobUnit nextJob = instantPaymentQueue.getNext();
         while (Objects.nonNull(nextJob)) {
             completePaymentJob(nextJob);
             paymentJobRedisRepository.save(nextJob);
             nextJob = instantPaymentQueue.getNext();
         }
    }

    //mock "integration" for completing payments
    private void completePaymentJob(PaymentJobUnit paymentJobUnit) {
        log.info("Payment job completed: {}", paymentJobUnit);
        paymentJobUnit.setCompleted("true");
        paymentJobUnit.setSuccess("true");
    }
}
