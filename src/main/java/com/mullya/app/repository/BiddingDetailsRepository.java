package com.mullya.app.repository;

import com.mullya.app.domain.BiddingDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BiddingDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiddingDetailsRepository extends JpaRepository<BiddingDetails, Long> {}
