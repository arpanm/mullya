package com.mullya.app.repository;

import com.mullya.app.domain.Catalogue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Catalogue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {}
