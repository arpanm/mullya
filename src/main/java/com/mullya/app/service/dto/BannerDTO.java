package com.mullya.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.Banner} entity.
 */
public class BannerDTO implements Serializable {

    private Long id;

    private String name;

    private String imageUrl;

    private String mobileImageUrl;

    private String landingUrl;

    private String description;

    private String html;

    private String mobileHtml;

    private boolean isActive;

    private String startDate;

    private String endDate;

    private Instant createdOn;

    private String createdBy;

    private Instant updatedOn;

    private String updatedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobileImageUrl() {
        return mobileImageUrl;
    }

    public void setMobileImageUrl(String mobileImageUrl) {
        this.mobileImageUrl = mobileImageUrl;
    }

    public String getLandingUrl() {
        return landingUrl;
    }

    public void setLandingUrl(String landingUrl) {
        this.landingUrl = landingUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getMobileHtml() {
        return mobileHtml;
    }

    public void setMobileHtml(String mobileHtml) {
        this.mobileHtml = mobileHtml;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BannerDTO)) {
            return false;
        }

        BannerDTO bannerDTO = (BannerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bannerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", mobileImageUrl='" + getMobileImageUrl() + "'" +
            ", landingUrl='" + getLandingUrl() + "'" +
            ", description='" + getDescription() + "'" +
            ", html='" + getHtml() + "'" +
            ", mobileHtml='" + getMobileHtml() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
