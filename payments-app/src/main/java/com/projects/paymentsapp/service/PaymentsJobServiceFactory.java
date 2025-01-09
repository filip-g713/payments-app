package com.projects.paymentsapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentsJobServiceFactory {

    private final Map<String, PaymentsJobService> paymentsJobServiceMap;

    public PaymentsJobService getPaymentsJobService(String paymentsJobServiceType) {
        return paymentsJobServiceMap.get(paymentsJobServiceType);
    }
}
