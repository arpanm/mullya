package com.mullya.app.repository;

import com.mullya.app.domain.OTPAttempt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OTPAttempt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OTPAttemptRepository extends JpaRepository<OTPAttempt, Long> {}
