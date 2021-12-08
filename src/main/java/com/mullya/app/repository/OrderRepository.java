package com.mullya.app.repository;

import com.mullya.app.domain.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Order entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(
        value = "select distinct jhiOrder from Order jhiOrder left join fetch jhiOrder.remittances",
        countQuery = "select count(distinct jhiOrder) from Order jhiOrder"
    )
    Page<Order> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct jhiOrder from Order jhiOrder left join fetch jhiOrder.remittances")
    List<Order> findAllWithEagerRelationships();

    @Query("select jhiOrder from Order jhiOrder left join fetch jhiOrder.remittances where jhiOrder.id =:id")
    Optional<Order> findOneWithEagerRelationships(@Param("id") Long id);
}
