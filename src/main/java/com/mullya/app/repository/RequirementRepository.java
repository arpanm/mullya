package com.mullya.app.repository;

import com.mullya.app.domain.Requirement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Requirement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Long> {}
