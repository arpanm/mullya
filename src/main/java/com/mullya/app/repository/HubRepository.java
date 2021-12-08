package com.mullya.app.repository;

import com.mullya.app.domain.Hub;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Hub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HubRepository extends JpaRepository<Hub, Long> {}
