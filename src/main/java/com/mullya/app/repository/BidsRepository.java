package com.mullya.app.repository;

import com.mullya.app.domain.Bids;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bids entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BidsRepository extends JpaRepository<Bids, Long> {}
