package com.mullya.app.repository;

import com.mullya.app.domain.DeliveryDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DeliveryDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails, Long> {}
