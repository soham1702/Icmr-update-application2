package com.techvg.covid.care.config;

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
            createCache(cm, com.techvg.covid.care.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.techvg.covid.care.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.techvg.covid.care.domain.User.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Authority.class.getName());
            createCache(cm, com.techvg.covid.care.domain.User.class.getName() + ".authorities");
            createCache(cm, com.techvg.covid.care.domain.PatientInfo.class.getName());
            createCache(cm, com.techvg.covid.care.domain.State.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Division.class.getName());
            createCache(cm, com.techvg.covid.care.domain.District.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Taluka.class.getName());
            createCache(cm, com.techvg.covid.care.domain.City.class.getName());
            createCache(cm, com.techvg.covid.care.domain.MunicipalCorp.class.getName());
            createCache(cm, com.techvg.covid.care.domain.UserAccess.class.getName());
            createCache(cm, com.techvg.covid.care.domain.SecurityUser.class.getName());
            createCache(cm, com.techvg.covid.care.domain.SecurityUser.class.getName() + ".securityPermissions");
            createCache(cm, com.techvg.covid.care.domain.SecurityUser.class.getName() + ".securityRoles");
            createCache(cm, com.techvg.covid.care.domain.SecurityRole.class.getName());
            createCache(cm, com.techvg.covid.care.domain.SecurityRole.class.getName() + ".securityPermissions");
            createCache(cm, com.techvg.covid.care.domain.SecurityRole.class.getName() + ".securityUsers");
            createCache(cm, com.techvg.covid.care.domain.SecurityPermission.class.getName());
            createCache(cm, com.techvg.covid.care.domain.SecurityPermission.class.getName() + ".securityRoles");
            createCache(cm, com.techvg.covid.care.domain.SecurityPermission.class.getName() + ".securityUsers");
            createCache(cm, com.techvg.covid.care.domain.HospitalType.class.getName());
            createCache(cm, com.techvg.covid.care.domain.BedType.class.getName());
            createCache(cm, com.techvg.covid.care.domain.InventoryType.class.getName());
            createCache(cm, com.techvg.covid.care.domain.InventoryMaster.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Hospital.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Hospital.class.getName() + ".suppliers");
            createCache(cm, com.techvg.covid.care.domain.BedInventory.class.getName());
            createCache(cm, com.techvg.covid.care.domain.BedTransactions.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Inventory.class.getName());
            createCache(cm, com.techvg.covid.care.domain.InventoryUsed.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Supplier.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Supplier.class.getName() + ".hospitals");
            createCache(cm, com.techvg.covid.care.domain.Transactions.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Trip.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Trip.class.getName() + ".tripDetails");
            createCache(cm, com.techvg.covid.care.domain.TripDetails.class.getName());
            createCache(cm, com.techvg.covid.care.domain.ContactType.class.getName());
            createCache(cm, com.techvg.covid.care.domain.Contact.class.getName());
            createCache(cm, com.techvg.covid.care.domain.AuditType.class.getName());
            createCache(cm, com.techvg.covid.care.domain.AuditSystem.class.getName());
            createCache(cm, com.techvg.covid.care.domain.ICMRDailyCount.class.getName());
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
