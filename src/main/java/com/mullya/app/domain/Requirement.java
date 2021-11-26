package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.RequirementStatus;
import com.mullya.app.domain.enumeration.StockCategory;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Requirement.
 */
@Entity
@Table(name = "requirement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Requirement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private StockCategory category;

    @Column(name = "variant")
    private String variant;

    @Column(name = "sub_variant")
    private String subVariant;

    @Column(name = "min_price")
    private Float minPrice;

    @Column(name = "max_price")
    private Float maxPrice;

    @Column(name = "quantity_kg")
    private Float quantityKg;

    @Column(name = "needed_by")
    private String neededBy;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequirementStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "requirements", "oTPS" }, allowSetters = true)
    private Actor actor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Requirement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StockCategory getCategory() {
        return this.category;
    }

    public Requirement category(StockCategory category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(StockCategory category) {
        this.category = category;
    }

    public String getVariant() {
        return this.variant;
    }

    public Requirement variant(String variant) {
        this.setVariant(variant);
        return this;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getSubVariant() {
        return this.subVariant;
    }

    public Requirement subVariant(String subVariant) {
        this.setSubVariant(subVariant);
        return this;
    }

    public void setSubVariant(String subVariant) {
        this.subVariant = subVariant;
    }

    public Float getMinPrice() {
        return this.minPrice;
    }

    public Requirement minPrice(Float minPrice) {
        this.setMinPrice(minPrice);
        return this;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return this.maxPrice;
    }

    public Requirement maxPrice(Float maxPrice) {
        this.setMaxPrice(maxPrice);
        return this;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Float getQuantityKg() {
        return this.quantityKg;
    }

    public Requirement quantityKg(Float quantityKg) {
        this.setQuantityKg(quantityKg);
        return this;
    }

    public void setQuantityKg(Float quantityKg) {
        this.quantityKg = quantityKg;
    }

    public String getNeededBy() {
        return this.neededBy;
    }

    public Requirement neededBy(String neededBy) {
        this.setNeededBy(neededBy);
        return this;
    }

    public void setNeededBy(String neededBy) {
        this.neededBy = neededBy;
    }

    public String getDescription() {
        return this.description;
    }

    public Requirement description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public Requirement createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Requirement createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Requirement updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Requirement updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public RequirementStatus getStatus() {
        return this.status;
    }

    public Requirement status(RequirementStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RequirementStatus status) {
        this.status = status;
    }

    public Actor getActor() {
        return this.actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Requirement actor(Actor actor) {
        this.setActor(actor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requirement)) {
            return false;
        }
        return id != null && id.equals(((Requirement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Requirement{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", variant='" + getVariant() + "'" +
            ", subVariant='" + getSubVariant() + "'" +
            ", minPrice=" + getMinPrice() +
            ", maxPrice=" + getMaxPrice() +
            ", quantityKg=" + getQuantityKg() +
            ", neededBy='" + getNeededBy() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
