package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.BiddingStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BiddingDetails.
 */
@Entity
@Table(name = "bidding_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BiddingDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "bidding_status")
    private BiddingStatus biddingStatus;

    @Column(name = "min_price")
    private Float minPrice;

    @Column(name = "max_price")
    private Float maxPrice;

    @Column(name = "min_quantity_kg")
    private Float minQuantityKg;

    @Column(name = "max_quantity_kg")
    private Float maxQuantityKg;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "biddingDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orders", "buyerAddress", "biddingDetails", "buyer" }, allowSetters = true)
    private Set<Bids> bids = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "biddingDetails", "orders", "farmerAddress", "farmer", "category", "variant", "subVariant" },
        allowSetters = true
    )
    private Stock stock;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BiddingDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public BiddingDetails startDate(String startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public BiddingDetails endDate(String endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BiddingStatus getBiddingStatus() {
        return this.biddingStatus;
    }

    public BiddingDetails biddingStatus(BiddingStatus biddingStatus) {
        this.setBiddingStatus(biddingStatus);
        return this;
    }

    public void setBiddingStatus(BiddingStatus biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public Float getMinPrice() {
        return this.minPrice;
    }

    public BiddingDetails minPrice(Float minPrice) {
        this.setMinPrice(minPrice);
        return this;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return this.maxPrice;
    }

    public BiddingDetails maxPrice(Float maxPrice) {
        this.setMaxPrice(maxPrice);
        return this;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Float getMinQuantityKg() {
        return this.minQuantityKg;
    }

    public BiddingDetails minQuantityKg(Float minQuantityKg) {
        this.setMinQuantityKg(minQuantityKg);
        return this;
    }

    public void setMinQuantityKg(Float minQuantityKg) {
        this.minQuantityKg = minQuantityKg;
    }

    public Float getMaxQuantityKg() {
        return this.maxQuantityKg;
    }

    public BiddingDetails maxQuantityKg(Float maxQuantityKg) {
        this.setMaxQuantityKg(maxQuantityKg);
        return this;
    }

    public void setMaxQuantityKg(Float maxQuantityKg) {
        this.maxQuantityKg = maxQuantityKg;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public BiddingDetails isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public BiddingDetails createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public BiddingDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public BiddingDetails updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public BiddingDetails updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Bids> getBids() {
        return this.bids;
    }

    public void setBids(Set<Bids> bids) {
        if (this.bids != null) {
            this.bids.forEach(i -> i.setBiddingDetails(null));
        }
        if (bids != null) {
            bids.forEach(i -> i.setBiddingDetails(this));
        }
        this.bids = bids;
    }

    public BiddingDetails bids(Set<Bids> bids) {
        this.setBids(bids);
        return this;
    }

    public BiddingDetails addBids(Bids bids) {
        this.bids.add(bids);
        bids.setBiddingDetails(this);
        return this;
    }

    public BiddingDetails removeBids(Bids bids) {
        this.bids.remove(bids);
        bids.setBiddingDetails(null);
        return this;
    }

    public Stock getStock() {
        return this.stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public BiddingDetails stock(Stock stock) {
        this.setStock(stock);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BiddingDetails)) {
            return false;
        }
        return id != null && id.equals(((BiddingDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BiddingDetails{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", biddingStatus='" + getBiddingStatus() + "'" +
            ", minPrice=" + getMinPrice() +
            ", maxPrice=" + getMaxPrice() +
            ", minQuantityKg=" + getMinQuantityKg() +
            ", maxQuantityKg=" + getMaxQuantityKg() +
            ", isActive='" + getIsActive() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
