package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.OrderStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mullya.app.domain.Order} entity.
 */
public class OrderDTO implements Serializable {

    private Long id;

    private Float acceptedPrice;

    private Float codAmount;

    private Float quantityKg;

    private String neededBy;

    private String acceptedDeliveryDate;

    private LocalDate createdOn;

    private String createdBy;

    private LocalDate updatedOn;

    private String updatedBy;

    private OrderStatus status;

    private Set<RemittanceDetailsDTO> remittances = new HashSet<>();

    private RequirementDTO requirement;

    private BidsDTO bid;

    private ActorDTO assignedAgent;

    private StockDTO stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAcceptedPrice() {
        return acceptedPrice;
    }

    public void setAcceptedPrice(Float acceptedPrice) {
        this.acceptedPrice = acceptedPrice;
    }

    public Float getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(Float codAmount) {
        this.codAmount = codAmount;
    }

    public Float getQuantityKg() {
        return quantityKg;
    }

    public void setQuantityKg(Float quantityKg) {
        this.quantityKg = quantityKg;
    }

    public String getNeededBy() {
        return neededBy;
    }

    public void setNeededBy(String neededBy) {
        this.neededBy = neededBy;
    }

    public String getAcceptedDeliveryDate() {
        return acceptedDeliveryDate;
    }

    public void setAcceptedDeliveryDate(String acceptedDeliveryDate) {
        this.acceptedDeliveryDate = acceptedDeliveryDate;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<RemittanceDetailsDTO> getRemittances() {
        return remittances;
    }

    public void setRemittances(Set<RemittanceDetailsDTO> remittances) {
        this.remittances = remittances;
    }

    public RequirementDTO getRequirement() {
        return requirement;
    }

    public void setRequirement(RequirementDTO requirement) {
        this.requirement = requirement;
    }

    public BidsDTO getBid() {
        return bid;
    }

    public void setBid(BidsDTO bid) {
        this.bid = bid;
    }

    public ActorDTO getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(ActorDTO assignedAgent) {
        this.assignedAgent = assignedAgent;
    }

    public StockDTO getStock() {
        return stock;
    }

    public void setStock(StockDTO stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", acceptedPrice=" + getAcceptedPrice() +
            ", codAmount=" + getCodAmount() +
            ", quantityKg=" + getQuantityKg() +
            ", neededBy='" + getNeededBy() + "'" +
            ", acceptedDeliveryDate='" + getAcceptedDeliveryDate() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", remittances=" + getRemittances() +
            ", requirement=" + getRequirement() +
            ", bid=" + getBid() +
            ", assignedAgent=" + getAssignedAgent() +
            ", stock=" + getStock() +
            "}";
    }
}
