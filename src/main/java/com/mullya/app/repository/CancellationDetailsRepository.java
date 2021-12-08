package com.mullya.app.repository;

import com.mullya.app.domain.CancellationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CancellationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CancellationDetailsRepository extends JpaRepository<CancellationDetails, Long> {}
