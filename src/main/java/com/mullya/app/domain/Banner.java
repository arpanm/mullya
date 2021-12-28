package com.mullya.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Banner.
 */
@Entity
@Table(name = "banner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Banner extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "mobile_image_url")
    private String mobileImageUrl;

    @Column(name = "landing_url")
    private String landingUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "html")
    private String html;

    @Column(name = "mobile_html")
    private String mobileHtml;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive = false;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Banner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Banner name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Banner imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobileImageUrl() {
        return this.mobileImageUrl;
    }

    public Banner mobileImageUrl(String mobileImageUrl) {
        this.setMobileImageUrl(mobileImageUrl);
        return this;
    }

    public void setMobileImageUrl(String mobileImageUrl) {
        this.mobileImageUrl = mobileImageUrl;
    }

    public String getLandingUrl() {
        return this.landingUrl;
    }

    public Banner landingUrl(String landingUrl) {
        this.setLandingUrl(landingUrl);
        return this;
    }

    public void setLandingUrl(String landingUrl) {
        this.landingUrl = landingUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public Banner description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml() {
        return this.html;
    }

    public Banner html(String html) {
        this.setHtml(html);
        return this;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getMobileHtml() {
        return this.mobileHtml;
    }

    public Banner mobileHtml(String mobileHtml) {
        this.setMobileHtml(mobileHtml);
        return this;
    }

    public void setMobileHtml(String mobileHtml) {
        this.mobileHtml = mobileHtml;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Banner isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        if (isActive != null) {
            this.isActive = isActive;
        }
    }

    public String getStartDate() {
        return this.startDate;
    }

    public Banner startDate(String startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public Banner endDate(String endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banner)) {
            return false;
        }
        return id != null && id.equals(((Banner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Banner{" +
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
            "}";
    }
}
