package com.payment.Task3.repository;

import com.payment.Task3.model.entity.Payment;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PaymentRepository {

    private final Map<Long, Payment> store = new ConcurrentHashMap<>();
    private final Map<String, Long> idempotencyIndex = new ConcurrentHashMap<>();
    private final Map<String, Long> externalRefIndex = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(0);


    public Payment save(Payment payment){

        if (payment.getId() == null){
            payment.setId(idGenerator.getAndIncrement());

            payment.setCreatedAt(LocalDateTime.now());
        }

        payment.setUpdatedAt(LocalDateTime.now());

        if (payment.getIdempotencyKey() != null) {
            idempotencyIndex.put(payment.getIdempotencyKey(), payment.getId());
        }
        if (payment.getExternalReference() != null) {
            externalRefIndex.put(payment.getExternalReference(), payment.getId());
        }
        return payment;
    }

    public Optional<Payment> findByIdempotencyKey(String key) {
        return Optional.ofNullable(idempotencyIndex.get(key))
                .map(store::get);
    }

    public Optional<Payment> findByExternalReference(String ref) {
        return Optional.ofNullable(externalRefIndex.get(ref))
                .map(store::get);
    }

}
