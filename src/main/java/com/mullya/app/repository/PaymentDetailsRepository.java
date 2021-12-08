package com.mullya.app.repository;

import com.mullya.app.domain.PaymentDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {}
