package com.mullya.app.repository;

import com.mullya.app.domain.RemittanceDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RemittanceDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemittanceDetailsRepository extends JpaRepository<RemittanceDetails, Long> {}
