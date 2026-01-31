package com.payment.Task3.client;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class ExternalPaymentClient {

    private final Random random = new Random();

    /**
     * Simulates an external payment provider.
     *
     * Possible behaviors:
     * - success
     * - pending
     * - temporary failure (exception)
     */
    public ProviderResponse initiatePayment() {

        int outcome = random.nextInt(3);

        // 0 → immediate success
        if (outcome == 0) {
            return new ProviderResponse(
                    "EXT-" + UUID.randomUUID(),
                    ProviderStatus.SUCCESS
            );
        }

        // 1 → pending (async callback later)
        if (outcome == 1) {
            return new ProviderResponse(
                    "EXT-" + UUID.randomUUID(),
                    ProviderStatus.PENDING
            );
        }

        // 2 → provider failure / timeout
        throw new RuntimeException("External payment provider timeout");
    }
}