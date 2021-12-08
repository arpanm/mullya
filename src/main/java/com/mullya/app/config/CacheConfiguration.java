package com.mullya.app.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mullya.app.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mullya.app.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mullya.app.domain.User.class.getName());
            createCache(cm, com.mullya.app.domain.Authority.class.getName());
            createCache(cm, com.mullya.app.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mullya.app.domain.Actor.class.getName());
            createCache(cm, com.mullya.app.domain.Actor.class.getName() + ".requirements");
            createCache(cm, com.mullya.app.domain.Actor.class.getName() + ".oTPS");
            createCache(cm, com.mullya.app.domain.Actor.class.getName() + ".addresses");
            createCache(cm, com.mullya.app.domain.Actor.class.getName() + ".stocks");
            createCache(cm, com.mullya.app.domain.Actor.class.getName() + ".bids");
            createCache(cm, com.mullya.app.domain.Actor.class.getName() + ".orders");
            createCache(cm, com.mullya.app.domain.Actor.class.getName() + ".remittanceDetails");
            createCache(cm, com.mullya.app.domain.Requirement.class.getName());
            createCache(cm, com.mullya.app.domain.Requirement.class.getName() + ".orders");
            createCache(cm, com.mullya.app.domain.OTP.class.getName());
            createCache(cm, com.mullya.app.domain.OTP.class.getName() + ".oTPAttempts");
            createCache(cm, com.mullya.app.domain.OTPAttempt.class.getName());
            createCache(cm, com.mullya.app.domain.Address.class.getName());
            createCache(cm, com.mullya.app.domain.Stock.class.getName());
            createCache(cm, com.mullya.app.domain.Stock.class.getName() + ".biddingDetails");
            createCache(cm, com.mullya.app.domain.Stock.class.getName() + ".orders");
            createCache(cm, com.mullya.app.domain.BiddingDetails.class.getName());
            createCache(cm, com.mullya.app.domain.BiddingDetails.class.getName() + ".bids");
            createCache(cm, com.mullya.app.domain.Bids.class.getName());
            createCache(cm, com.mullya.app.domain.Bids.class.getName() + ".orders");
            createCache(cm, com.mullya.app.domain.Order.class.getName());
            createCache(cm, com.mullya.app.domain.Order.class.getName() + ".paymentDetails");
            createCache(cm, com.mullya.app.domain.Order.class.getName() + ".remittances");
            createCache(cm, com.mullya.app.domain.DeliveryDetails.class.getName());
            createCache(cm, com.mullya.app.domain.CancellationDetails.class.getName());
            createCache(cm, com.mullya.app.domain.CancellationDetails.class.getName() + ".deliveryDetails");
            createCache(cm, com.mullya.app.domain.PaymentDetails.class.getName());
            createCache(cm, com.mullya.app.domain.RemittanceDetails.class.getName());
            createCache(cm, com.mullya.app.domain.RemittanceDetails.class.getName() + ".orders");
            createCache(cm, com.mullya.app.domain.Hub.class.getName());
            createCache(cm, com.mullya.app.domain.Catalogue.class.getName());
            createCache(cm, com.mullya.app.domain.Catalogue.class.getName() + ".catalogues");
            createCache(cm, com.mullya.app.domain.Catalogue.class.getName() + ".categoryStocks");
            createCache(cm, com.mullya.app.domain.Catalogue.class.getName() + ".variantStocks");
            createCache(cm, com.mullya.app.domain.Catalogue.class.getName() + ".subVariantStocks");
            createCache(cm, com.mullya.app.domain.Catalogue.class.getName() + ".categoryRequirements");
            createCache(cm, com.mullya.app.domain.Catalogue.class.getName() + ".variantRequirements");
            createCache(cm, com.mullya.app.domain.Catalogue.class.getName() + ".subVariantRequirements");
            createCache(cm, com.mullya.app.domain.Banner.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
