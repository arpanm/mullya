package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.config.Constants;
import com.mullya.app.domain.enumeration.ActorType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @Column(name = "phone")
    private Long phone;

    @NotNull
    @Column(name = "is_email_verified")
    private Boolean isEmailVerified = false;

    @NotNull
    @Column(name = "is_phone_verified")
    private Boolean isPhoneVerified = false;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ActorType type;

    @OneToMany(mappedBy = "buyerUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orders", "buyerAddress", "buyerUser", "category", "variant", "subVariant" }, allowSetters = true)
    private Set<Requirement> requirements = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "oTPAttempts", "user" }, allowSetters = true)
    private Set<OTP> oTPS = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "hub", "user" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "farmer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "biddingDetails", "orders", "farmerAddress", "farmer", "category", "variant", "subVariant" },
        allowSetters = true
    )
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(mappedBy = "buyer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orders", "buyerAddress", "biddingDetails", "buyer" }, allowSetters = true)
    private Set<Bids> bids = new HashSet<>();

    @OneToMany(mappedBy = "assignedAgent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paymentDetails", "remittances", "requirement", "bid", "assignedAgent", "stock" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "farmer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farmer", "orders" }, allowSetters = true)
    private Set<RemittanceDetails> remittanceDetails = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Long getPhone() {
        return this.phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Boolean getIsEmailVerified() {
        return this.isEmailVerified;
    }

    public void setIsEmailVerified(Boolean isEmailVerified) {
        if (isEmailVerified != null) {
            this.isEmailVerified = isEmailVerified;
        }
    }

    public Boolean getIsPhoneVerified() {
        return this.isPhoneVerified;
    }

    public void setIsPhoneVerified(Boolean isPhoneVerified) {
        if (isPhoneVerified != null) {
            this.isPhoneVerified = isPhoneVerified;
        }
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        if (isActive != null) {
            this.isActive = isActive;
        }
    }

    public ActorType getType() {
        return this.type;
    }

    public void setType(ActorType type) {
        this.type = type;
    }

    public Set<Requirement> getRequirements() {
        return this.requirements;
    }

    public void setRequirements(Set<Requirement> requirements) {
        if (this.requirements != null) {
            this.requirements.forEach(i -> i.setBuyerUser(null));
        }
        if (requirements != null) {
            requirements.forEach(i -> i.setBuyerUser(this));
        }
        this.requirements = requirements;
    }

    public User addRequirement(Requirement requirement) {
        this.requirements.add(requirement);
        requirement.setBuyerUser(this);
        return this;
    }

    public User removeRequirement(Requirement requirement) {
        this.requirements.remove(requirement);
        requirement.setBuyerUser(null);
        return this;
    }

    public Set<OTP> getOTPS() {
        return this.oTPS;
    }

    public void setOTPS(Set<OTP> oTPS) {
        if (this.oTPS != null) {
            this.oTPS.forEach(i -> i.setUser(null));
        }
        if (oTPS != null) {
            oTPS.forEach(i -> i.setUser(this));
        }
        this.oTPS = oTPS;
    }

    public User addOTP(OTP oTP) {
        this.oTPS.add(oTP);
        oTP.setUser(this);
        return this;
    }

    public User removeOTP(OTP oTP) {
        this.oTPS.remove(oTP);
        oTP.setUser(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setUser(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setUser(this));
        }
        this.addresses = addresses;
    }

    public User addAddress(Address address) {
        this.addresses.add(address);
        address.setUser(this);
        return this;
    }

    public User removeAddress(Address address) {
        this.addresses.remove(address);
        address.setUser(null);
        return this;
    }

    public Set<Stock> getStocks() {
        return this.stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        if (this.stocks != null) {
            this.stocks.forEach(i -> i.setFarmer(null));
        }
        if (stocks != null) {
            stocks.forEach(i -> i.setFarmer(this));
        }
        this.stocks = stocks;
    }

    public User addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setFarmer(this);
        return this;
    }

    public User removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setFarmer(null);
        return this;
    }

    public Set<Bids> getBids() {
        return this.bids;
    }

    public void setBids(Set<Bids> bids) {
        if (this.bids != null) {
            this.bids.forEach(i -> i.setBuyer(null));
        }
        if (bids != null) {
            bids.forEach(i -> i.setBuyer(this));
        }
        this.bids = bids;
    }

    public User addBids(Bids bids) {
        this.bids.add(bids);
        bids.setBuyer(this);
        return this;
    }

    public User removeBids(Bids bids) {
        this.bids.remove(bids);
        bids.setBuyer(null);
        return this;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setAssignedAgent(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setAssignedAgent(this));
        }
        this.orders = orders;
    }

    public User addOrder(Order order) {
        this.orders.add(order);
        order.setAssignedAgent(this);
        return this;
    }

    public User removeOrder(Order order) {
        this.orders.remove(order);
        order.setAssignedAgent(null);
        return this;
    }

    public Set<RemittanceDetails> getRemittanceDetails() {
        return this.remittanceDetails;
    }

    public void setRemittanceDetails(Set<RemittanceDetails> remittanceDetails) {
        if (this.remittanceDetails != null) {
            this.remittanceDetails.forEach(i -> i.setFarmer(null));
        }
        if (remittanceDetails != null) {
            remittanceDetails.forEach(i -> i.setFarmer(this));
        }
        this.remittanceDetails = remittanceDetails;
    }

    public User addRemittanceDetails(RemittanceDetails remittanceDetails) {
        this.remittanceDetails.add(remittanceDetails);
        remittanceDetails.setFarmer(this);
        return this;
    }

    public User removeRemittanceDetails(RemittanceDetails remittanceDetails) {
        this.remittanceDetails.remove(remittanceDetails);
        remittanceDetails.setFarmer(null);
        return this;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }
}
