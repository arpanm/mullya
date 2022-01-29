package com.mullya.app.repository;

import com.mullya.app.domain.Catalogue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Catalogue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {
    @Query("select c from Catalogue c where c.parent.id =:pid")
    Page<Catalogue> findAllByParent(Pageable pageable, @Param("pid") Long parent);

    Page<Catalogue> findAllByParentIsNull(Pageable pageable);
}
