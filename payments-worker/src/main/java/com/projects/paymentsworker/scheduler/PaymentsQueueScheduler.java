package com.projects.paymentsworker.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PaymentsQueueScheduler {

    private final  PaymentsSchedulerService paymentsSchedulerService;

    @Scheduled(fixedDelay = 15 * 60 * 1000)
    private void processScheduledPayments() {
        log.info("Scheduling payments");
        paymentsSchedulerService.processScheduledPayments();
        log.info("Finished scheduling payments");
    }

    @Scheduled(fixedDelay = 30 * 1000)
    private void processInstantPayments() {
        log.info("Scheduling instant payments");
        paymentsSchedulerService.processInstantPayments();
        log.info("Finished scheduling instant payments");
    }
}
