package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Catalogue.
 */
@Entity
@Table(name = "catalogue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Catalogue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "stock_image_url")
    private String stockImageUrl;

    @Column(name = "landing_url")
    private String landingUrl;

    @Column(name = "description")
    private String description;

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

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "catalogues",
            "categoryStocks",
            "variantStocks",
            "subVariantStocks",
            "categoryRequirements",
            "variantRequirements",
            "subVariantRequirements",
            "parent",
        },
        allowSetters = true
    )
    private Set<Catalogue> catalogues = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "biddingDetails", "orders", "farmerAddress", "farmer", "category", "variant", "subVariant" },
        allowSetters = true
    )
    private Set<Stock> categoryStocks = new HashSet<>();

    @OneToMany(mappedBy = "variant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "biddingDetails", "orders", "farmerAddress", "farmer", "category", "variant", "subVariant" },
        allowSetters = true
    )
    private Set<Stock> variantStocks = new HashSet<>();

    @OneToMany(mappedBy = "subVariant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "biddingDetails", "orders", "farmerAddress", "farmer", "category", "variant", "subVariant" },
        allowSetters = true
    )
    private Set<Stock> subVariantStocks = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orders", "buyerAddress", "buyerUser", "category", "variant", "subVariant" }, allowSetters = true)
    private Set<Requirement> categoryRequirements = new HashSet<>();

    @OneToMany(mappedBy = "variant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orders", "buyerAddress", "buyerUser", "category", "variant", "subVariant" }, allowSetters = true)
    private Set<Requirement> variantRequirements = new HashSet<>();

    @OneToMany(mappedBy = "subVariant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orders", "buyerAddress", "buyerUser", "category", "variant", "subVariant" }, allowSetters = true)
    private Set<Requirement> subVariantRequirements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "catalogues",
            "categoryStocks",
            "variantStocks",
            "subVariantStocks",
            "categoryRequirements",
            "variantRequirements",
            "subVariantRequirements",
            "parent",
        },
        allowSetters = true
    )
    private Catalogue parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Catalogue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Catalogue name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockImageUrl() {
        return this.stockImageUrl;
    }

    public Catalogue stockImageUrl(String stockImageUrl) {
        this.setStockImageUrl(stockImageUrl);
        return this;
    }

    public void setStockImageUrl(String stockImageUrl) {
        this.stockImageUrl = stockImageUrl;
    }

    public String getLandingUrl() {
        return this.landingUrl;
    }

    public Catalogue landingUrl(String landingUrl) {
        this.setLandingUrl(landingUrl);
        return this;
    }

    public void setLandingUrl(String landingUrl) {
        this.landingUrl = landingUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public Catalogue description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Catalogue isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public Catalogue createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Catalogue createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Catalogue updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Catalogue updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Catalogue> getCatalogues() {
        return this.catalogues;
    }

    public void setCatalogues(Set<Catalogue> catalogues) {
        if (this.catalogues != null) {
            this.catalogues.forEach(i -> i.setParent(null));
        }
        if (catalogues != null) {
            catalogues.forEach(i -> i.setParent(this));
        }
        this.catalogues = catalogues;
    }

    public Catalogue catalogues(Set<Catalogue> catalogues) {
        this.setCatalogues(catalogues);
        return this;
    }

    public Catalogue addCatalogue(Catalogue catalogue) {
        this.catalogues.add(catalogue);
        catalogue.setParent(this);
        return this;
    }

    public Catalogue removeCatalogue(Catalogue catalogue) {
        this.catalogues.remove(catalogue);
        catalogue.setParent(null);
        return this;
    }

    public Set<Stock> getCategoryStocks() {
        return this.categoryStocks;
    }

    public void setCategoryStocks(Set<Stock> stocks) {
        if (this.categoryStocks != null) {
            this.categoryStocks.forEach(i -> i.setCategory(null));
        }
        if (stocks != null) {
            stocks.forEach(i -> i.setCategory(this));
        }
        this.categoryStocks = stocks;
    }

    public Catalogue categoryStocks(Set<Stock> stocks) {
        this.setCategoryStocks(stocks);
        return this;
    }

    public Catalogue addCategoryStocks(Stock stock) {
        this.categoryStocks.add(stock);
        stock.setCategory(this);
        return this;
    }

    public Catalogue removeCategoryStocks(Stock stock) {
        this.categoryStocks.remove(stock);
        stock.setCategory(null);
        return this;
    }

    public Set<Stock> getVariantStocks() {
        return this.variantStocks;
    }

    public void setVariantStocks(Set<Stock> stocks) {
        if (this.variantStocks != null) {
            this.variantStocks.forEach(i -> i.setVariant(null));
        }
        if (stocks != null) {
            stocks.forEach(i -> i.setVariant(this));
        }
        this.variantStocks = stocks;
    }

    public Catalogue variantStocks(Set<Stock> stocks) {
        this.setVariantStocks(stocks);
        return this;
    }

    public Catalogue addVariantStocks(Stock stock) {
        this.variantStocks.add(stock);
        stock.setVariant(this);
        return this;
    }

    public Catalogue removeVariantStocks(Stock stock) {
        this.variantStocks.remove(stock);
        stock.setVariant(null);
        return this;
    }

    public Set<Stock> getSubVariantStocks() {
        return this.subVariantStocks;
    }

    public void setSubVariantStocks(Set<Stock> stocks) {
        if (this.subVariantStocks != null) {
            this.subVariantStocks.forEach(i -> i.setSubVariant(null));
        }
        if (stocks != null) {
            stocks.forEach(i -> i.setSubVariant(this));
        }
        this.subVariantStocks = stocks;
    }

    public Catalogue subVariantStocks(Set<Stock> stocks) {
        this.setSubVariantStocks(stocks);
        return this;
    }

    public Catalogue addSubVariantStocks(Stock stock) {
        this.subVariantStocks.add(stock);
        stock.setSubVariant(this);
        return this;
    }

    public Catalogue removeSubVariantStocks(Stock stock) {
        this.subVariantStocks.remove(stock);
        stock.setSubVariant(null);
        return this;
    }

    public Set<Requirement> getCategoryRequirements() {
        return this.categoryRequirements;
    }

    public void setCategoryRequirements(Set<Requirement> requirements) {
        if (this.categoryRequirements != null) {
            this.categoryRequirements.forEach(i -> i.setCategory(null));
        }
        if (requirements != null) {
            requirements.forEach(i -> i.setCategory(this));
        }
        this.categoryRequirements = requirements;
    }

    public Catalogue categoryRequirements(Set<Requirement> requirements) {
        this.setCategoryRequirements(requirements);
        return this;
    }

    public Catalogue addCategoryRequirements(Requirement requirement) {
        this.categoryRequirements.add(requirement);
        requirement.setCategory(this);
        return this;
    }

    public Catalogue removeCategoryRequirements(Requirement requirement) {
        this.categoryRequirements.remove(requirement);
        requirement.setCategory(null);
        return this;
    }

    public Set<Requirement> getVariantRequirements() {
        return this.variantRequirements;
    }

    public void setVariantRequirements(Set<Requirement> requirements) {
        if (this.variantRequirements != null) {
            this.variantRequirements.forEach(i -> i.setVariant(null));
        }
        if (requirements != null) {
            requirements.forEach(i -> i.setVariant(this));
        }
        this.variantRequirements = requirements;
    }

    public Catalogue variantRequirements(Set<Requirement> requirements) {
        this.setVariantRequirements(requirements);
        return this;
    }

    public Catalogue addVariantRequirements(Requirement requirement) {
        this.variantRequirements.add(requirement);
        requirement.setVariant(this);
        return this;
    }

    public Catalogue removeVariantRequirements(Requirement requirement) {
        this.variantRequirements.remove(requirement);
        requirement.setVariant(null);
        return this;
    }

    public Set<Requirement> getSubVariantRequirements() {
        return this.subVariantRequirements;
    }

    public void setSubVariantRequirements(Set<Requirement> requirements) {
        if (this.subVariantRequirements != null) {
            this.subVariantRequirements.forEach(i -> i.setSubVariant(null));
        }
        if (requirements != null) {
            requirements.forEach(i -> i.setSubVariant(this));
        }
        this.subVariantRequirements = requirements;
    }

    public Catalogue subVariantRequirements(Set<Requirement> requirements) {
        this.setSubVariantRequirements(requirements);
        return this;
    }

    public Catalogue addSubVariantRequirements(Requirement requirement) {
        this.subVariantRequirements.add(requirement);
        requirement.setSubVariant(this);
        return this;
    }

    public Catalogue removeSubVariantRequirements(Requirement requirement) {
        this.subVariantRequirements.remove(requirement);
        requirement.setSubVariant(null);
        return this;
    }

    public Catalogue getParent() {
        return this.parent;
    }

    public void setParent(Catalogue catalogue) {
        this.parent = catalogue;
    }

    public Catalogue parent(Catalogue catalogue) {
        this.setParent(catalogue);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Catalogue)) {
            return false;
        }
        return id != null && id.equals(((Catalogue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Catalogue{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", stockImageUrl='" + getStockImageUrl() + "'" +
            ", landingUrl='" + getLandingUrl() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
