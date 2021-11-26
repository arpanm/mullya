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
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "postal_code")
    private Integer postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "country")
    private String country;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lon")
    private Float lon;

    @Column(name = "map_location")
    private String mapLocation;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "requirements", "acceptedRequirements", "assignedRequirements", "oTPS", "addresses" },
        allowSetters = true
    )
    private Actor actor;

    @OneToMany(mappedBy = "buyerAddress")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "buyerAddress", "farmerAddress", "buyerActor", "acceptedAgentActor", "farmerActor" },
        allowSetters = true
    )
    private Set<Requirement> requirements = new HashSet<>();

    @OneToMany(mappedBy = "farmerAddress")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "buyerAddress", "farmerAddress", "buyerActor", "acceptedAgentActor", "farmerActor" },
        allowSetters = true
    )
    private Set<Requirement> orderRequirements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public Address streetAddress(String streetAddress) {
        this.setStreetAddress(streetAddress);
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Integer getPostalCode() {
        return this.postalCode;
    }

    public Address postalCode(Integer postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return this.city;
    }

    public Address city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return this.stateProvince;
    }

    public Address stateProvince(String stateProvince) {
        this.setStateProvince(stateProvince);
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountry() {
        return this.country;
    }

    public Address country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Address createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Float getLat() {
        return this.lat;
    }

    public Address lat(Float lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return this.lon;
    }

    public Address lon(Float lon) {
        this.setLon(lon);
        return this;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String getMapLocation() {
        return this.mapLocation;
    }

    public Address mapLocation(String mapLocation) {
        this.setMapLocation(mapLocation);
        return this;
    }

    public void setMapLocation(String mapLocation) {
        this.mapLocation = mapLocation;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Address createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Address updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Address updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Actor getActor() {
        return this.actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Address actor(Actor actor) {
        this.setActor(actor);
        return this;
    }

    public Set<Requirement> getRequirements() {
        return this.requirements;
    }

    public void setRequirements(Set<Requirement> requirements) {
        if (this.requirements != null) {
            this.requirements.forEach(i -> i.setBuyerAddress(null));
        }
        if (requirements != null) {
            requirements.forEach(i -> i.setBuyerAddress(this));
        }
        this.requirements = requirements;
    }

    public Address requirements(Set<Requirement> requirements) {
        this.setRequirements(requirements);
        return this;
    }

    public Address addRequirements(Requirement requirement) {
        this.requirements.add(requirement);
        requirement.setBuyerAddress(this);
        return this;
    }

    public Address removeRequirements(Requirement requirement) {
        this.requirements.remove(requirement);
        requirement.setBuyerAddress(null);
        return this;
    }

    public Set<Requirement> getOrderRequirements() {
        return this.orderRequirements;
    }

    public void setOrderRequirements(Set<Requirement> requirements) {
        if (this.orderRequirements != null) {
            this.orderRequirements.forEach(i -> i.setFarmerAddress(null));
        }
        if (requirements != null) {
            requirements.forEach(i -> i.setFarmerAddress(this));
        }
        this.orderRequirements = requirements;
    }

    public Address orderRequirements(Set<Requirement> requirements) {
        this.setOrderRequirements(requirements);
        return this;
    }

    public Address addOrderRequirements(Requirement requirement) {
        this.orderRequirements.add(requirement);
        requirement.setFarmerAddress(this);
        return this;
    }

    public Address removeOrderRequirements(Requirement requirement) {
        this.orderRequirements.remove(requirement);
        requirement.setFarmerAddress(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode=" + getPostalCode() +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", mapLocation='" + getMapLocation() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
