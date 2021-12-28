package com.mullya.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Hub.
 */
@Entity
@Table(name = "hub")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hub extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag")
    private String tag;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive = true;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hub id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return this.tag;
    }

    public Hub tag(String tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Hub isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hub)) {
            return false;
        }
        return id != null && id.equals(((Hub) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hub{" +
            "id=" + getId() +
            ", tag='" + getTag() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
