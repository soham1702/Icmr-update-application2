package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.domain.Inventory;
import com.techvg.covid.care.domain.InventoryMaster;
import com.techvg.covid.care.domain.Supplier;
import com.techvg.covid.care.repository.InventoryRepository;
import com.techvg.covid.care.service.criteria.InventoryCriteria;
import com.techvg.covid.care.service.dto.InventoryDTO;
import com.techvg.covid.care.service.mapper.InventoryMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InventoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InventoryResourceIT {

    private static final Long DEFAULT_STOCK = 1L;
    private static final Long UPDATED_STOCK = 2L;
    private static final Long SMALLER_STOCK = 1L - 1L;

    private static final Long DEFAULT_CAPCITY = 1L;
    private static final Long UPDATED_CAPCITY = 2L;
    private static final Long SMALLER_CAPCITY = 1L - 1L;

    private static final Long DEFAULT_INSTALLED_CAPCITY = 1L;
    private static final Long UPDATED_INSTALLED_CAPCITY = 2L;
    private static final Long SMALLER_INSTALLED_CAPCITY = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inventories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryMockMvc;

    private Inventory inventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .stock(DEFAULT_STOCK)
            .capcity(DEFAULT_CAPCITY)
            .installedCapcity(DEFAULT_INSTALLED_CAPCITY)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return inventory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createUpdatedEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .stock(UPDATED_STOCK)
            .capcity(UPDATED_CAPCITY)
            .installedCapcity(UPDATED_INSTALLED_CAPCITY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return inventory;
    }

    @BeforeEach
    public void initTest() {
        inventory = createEntity(em);
    }

    @Test
    @Transactional
    void createInventory() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();
        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);
        restInventoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate + 1);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testInventory.getCapcity()).isEqualTo(DEFAULT_CAPCITY);
        assertThat(testInventory.getInstalledCapcity()).isEqualTo(DEFAULT_INSTALLED_CAPCITY);
        assertThat(testInventory.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testInventory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createInventoryWithExistingId() throws Exception {
        // Create the Inventory with an existing ID
        inventory.setId(1L);
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryRepository.findAll().size();
        // set the field null
        inventory.setStock(null);

        // Create the Inventory, which fails.
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        restInventoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isBadRequest());

        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryRepository.findAll().size();
        // set the field null
        inventory.setLastModified(null);

        // Create the Inventory, which fails.
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        restInventoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isBadRequest());

        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryRepository.findAll().size();
        // set the field null
        inventory.setLastModifiedBy(null);

        // Create the Inventory, which fails.
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        restInventoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isBadRequest());

        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInventories() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].capcity").value(hasItem(DEFAULT_CAPCITY.intValue())))
            .andExpect(jsonPath("$.[*].installedCapcity").value(hasItem(DEFAULT_INSTALLED_CAPCITY.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get the inventory
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL_ID, inventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventory.getId().intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.intValue()))
            .andExpect(jsonPath("$.capcity").value(DEFAULT_CAPCITY.intValue()))
            .andExpect(jsonPath("$.installedCapcity").value(DEFAULT_INSTALLED_CAPCITY.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getInventoriesByIdFiltering() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        Long id = inventory.getId();

        defaultInventoryShouldBeFound("id.equals=" + id);
        defaultInventoryShouldNotBeFound("id.notEquals=" + id);

        defaultInventoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInventoryShouldNotBeFound("id.greaterThan=" + id);

        defaultInventoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInventoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInventoriesByStockIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where stock equals to DEFAULT_STOCK
        defaultInventoryShouldBeFound("stock.equals=" + DEFAULT_STOCK);

        // Get all the inventoryList where stock equals to UPDATED_STOCK
        defaultInventoryShouldNotBeFound("stock.equals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByStockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where stock not equals to DEFAULT_STOCK
        defaultInventoryShouldNotBeFound("stock.notEquals=" + DEFAULT_STOCK);

        // Get all the inventoryList where stock not equals to UPDATED_STOCK
        defaultInventoryShouldBeFound("stock.notEquals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByStockIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where stock in DEFAULT_STOCK or UPDATED_STOCK
        defaultInventoryShouldBeFound("stock.in=" + DEFAULT_STOCK + "," + UPDATED_STOCK);

        // Get all the inventoryList where stock equals to UPDATED_STOCK
        defaultInventoryShouldNotBeFound("stock.in=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where stock is not null
        defaultInventoryShouldBeFound("stock.specified=true");

        // Get all the inventoryList where stock is null
        defaultInventoryShouldNotBeFound("stock.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where stock is greater than or equal to DEFAULT_STOCK
        defaultInventoryShouldBeFound("stock.greaterThanOrEqual=" + DEFAULT_STOCK);

        // Get all the inventoryList where stock is greater than or equal to UPDATED_STOCK
        defaultInventoryShouldNotBeFound("stock.greaterThanOrEqual=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByStockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where stock is less than or equal to DEFAULT_STOCK
        defaultInventoryShouldBeFound("stock.lessThanOrEqual=" + DEFAULT_STOCK);

        // Get all the inventoryList where stock is less than or equal to SMALLER_STOCK
        defaultInventoryShouldNotBeFound("stock.lessThanOrEqual=" + SMALLER_STOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByStockIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where stock is less than DEFAULT_STOCK
        defaultInventoryShouldNotBeFound("stock.lessThan=" + DEFAULT_STOCK);

        // Get all the inventoryList where stock is less than UPDATED_STOCK
        defaultInventoryShouldBeFound("stock.lessThan=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByStockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where stock is greater than DEFAULT_STOCK
        defaultInventoryShouldNotBeFound("stock.greaterThan=" + DEFAULT_STOCK);

        // Get all the inventoryList where stock is greater than SMALLER_STOCK
        defaultInventoryShouldBeFound("stock.greaterThan=" + SMALLER_STOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByCapcityIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where capcity equals to DEFAULT_CAPCITY
        defaultInventoryShouldBeFound("capcity.equals=" + DEFAULT_CAPCITY);

        // Get all the inventoryList where capcity equals to UPDATED_CAPCITY
        defaultInventoryShouldNotBeFound("capcity.equals=" + UPDATED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByCapcityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where capcity not equals to DEFAULT_CAPCITY
        defaultInventoryShouldNotBeFound("capcity.notEquals=" + DEFAULT_CAPCITY);

        // Get all the inventoryList where capcity not equals to UPDATED_CAPCITY
        defaultInventoryShouldBeFound("capcity.notEquals=" + UPDATED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByCapcityIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where capcity in DEFAULT_CAPCITY or UPDATED_CAPCITY
        defaultInventoryShouldBeFound("capcity.in=" + DEFAULT_CAPCITY + "," + UPDATED_CAPCITY);

        // Get all the inventoryList where capcity equals to UPDATED_CAPCITY
        defaultInventoryShouldNotBeFound("capcity.in=" + UPDATED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByCapcityIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where capcity is not null
        defaultInventoryShouldBeFound("capcity.specified=true");

        // Get all the inventoryList where capcity is null
        defaultInventoryShouldNotBeFound("capcity.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByCapcityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where capcity is greater than or equal to DEFAULT_CAPCITY
        defaultInventoryShouldBeFound("capcity.greaterThanOrEqual=" + DEFAULT_CAPCITY);

        // Get all the inventoryList where capcity is greater than or equal to UPDATED_CAPCITY
        defaultInventoryShouldNotBeFound("capcity.greaterThanOrEqual=" + UPDATED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByCapcityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where capcity is less than or equal to DEFAULT_CAPCITY
        defaultInventoryShouldBeFound("capcity.lessThanOrEqual=" + DEFAULT_CAPCITY);

        // Get all the inventoryList where capcity is less than or equal to SMALLER_CAPCITY
        defaultInventoryShouldNotBeFound("capcity.lessThanOrEqual=" + SMALLER_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByCapcityIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where capcity is less than DEFAULT_CAPCITY
        defaultInventoryShouldNotBeFound("capcity.lessThan=" + DEFAULT_CAPCITY);

        // Get all the inventoryList where capcity is less than UPDATED_CAPCITY
        defaultInventoryShouldBeFound("capcity.lessThan=" + UPDATED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByCapcityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where capcity is greater than DEFAULT_CAPCITY
        defaultInventoryShouldNotBeFound("capcity.greaterThan=" + DEFAULT_CAPCITY);

        // Get all the inventoryList where capcity is greater than SMALLER_CAPCITY
        defaultInventoryShouldBeFound("capcity.greaterThan=" + SMALLER_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByInstalledCapcityIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where installedCapcity equals to DEFAULT_INSTALLED_CAPCITY
        defaultInventoryShouldBeFound("installedCapcity.equals=" + DEFAULT_INSTALLED_CAPCITY);

        // Get all the inventoryList where installedCapcity equals to UPDATED_INSTALLED_CAPCITY
        defaultInventoryShouldNotBeFound("installedCapcity.equals=" + UPDATED_INSTALLED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByInstalledCapcityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where installedCapcity not equals to DEFAULT_INSTALLED_CAPCITY
        defaultInventoryShouldNotBeFound("installedCapcity.notEquals=" + DEFAULT_INSTALLED_CAPCITY);

        // Get all the inventoryList where installedCapcity not equals to UPDATED_INSTALLED_CAPCITY
        defaultInventoryShouldBeFound("installedCapcity.notEquals=" + UPDATED_INSTALLED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByInstalledCapcityIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where installedCapcity in DEFAULT_INSTALLED_CAPCITY or UPDATED_INSTALLED_CAPCITY
        defaultInventoryShouldBeFound("installedCapcity.in=" + DEFAULT_INSTALLED_CAPCITY + "," + UPDATED_INSTALLED_CAPCITY);

        // Get all the inventoryList where installedCapcity equals to UPDATED_INSTALLED_CAPCITY
        defaultInventoryShouldNotBeFound("installedCapcity.in=" + UPDATED_INSTALLED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByInstalledCapcityIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where installedCapcity is not null
        defaultInventoryShouldBeFound("installedCapcity.specified=true");

        // Get all the inventoryList where installedCapcity is null
        defaultInventoryShouldNotBeFound("installedCapcity.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByInstalledCapcityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where installedCapcity is greater than or equal to DEFAULT_INSTALLED_CAPCITY
        defaultInventoryShouldBeFound("installedCapcity.greaterThanOrEqual=" + DEFAULT_INSTALLED_CAPCITY);

        // Get all the inventoryList where installedCapcity is greater than or equal to UPDATED_INSTALLED_CAPCITY
        defaultInventoryShouldNotBeFound("installedCapcity.greaterThanOrEqual=" + UPDATED_INSTALLED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByInstalledCapcityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where installedCapcity is less than or equal to DEFAULT_INSTALLED_CAPCITY
        defaultInventoryShouldBeFound("installedCapcity.lessThanOrEqual=" + DEFAULT_INSTALLED_CAPCITY);

        // Get all the inventoryList where installedCapcity is less than or equal to SMALLER_INSTALLED_CAPCITY
        defaultInventoryShouldNotBeFound("installedCapcity.lessThanOrEqual=" + SMALLER_INSTALLED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByInstalledCapcityIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where installedCapcity is less than DEFAULT_INSTALLED_CAPCITY
        defaultInventoryShouldNotBeFound("installedCapcity.lessThan=" + DEFAULT_INSTALLED_CAPCITY);

        // Get all the inventoryList where installedCapcity is less than UPDATED_INSTALLED_CAPCITY
        defaultInventoryShouldBeFound("installedCapcity.lessThan=" + UPDATED_INSTALLED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByInstalledCapcityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where installedCapcity is greater than DEFAULT_INSTALLED_CAPCITY
        defaultInventoryShouldNotBeFound("installedCapcity.greaterThan=" + DEFAULT_INSTALLED_CAPCITY);

        // Get all the inventoryList where installedCapcity is greater than SMALLER_INSTALLED_CAPCITY
        defaultInventoryShouldBeFound("installedCapcity.greaterThan=" + SMALLER_INSTALLED_CAPCITY);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultInventoryShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the inventoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultInventoryShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultInventoryShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the inventoryList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultInventoryShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultInventoryShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the inventoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultInventoryShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastModified is not null
        defaultInventoryShouldBeFound("lastModified.specified=true");

        // Get all the inventoryList where lastModified is null
        defaultInventoryShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultInventoryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultInventoryShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultInventoryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the inventoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastModifiedBy is not null
        defaultInventoryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the inventoryList where lastModifiedBy is null
        defaultInventoryShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultInventoryShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultInventoryShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultInventoryShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultInventoryShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoriesByInventoryMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);
        InventoryMaster inventoryMaster;
        if (TestUtil.findAll(em, InventoryMaster.class).isEmpty()) {
            inventoryMaster = InventoryMasterResourceIT.createEntity(em);
            em.persist(inventoryMaster);
            em.flush();
        } else {
            inventoryMaster = TestUtil.findAll(em, InventoryMaster.class).get(0);
        }
        em.persist(inventoryMaster);
        em.flush();
        inventory.setInventoryMaster(inventoryMaster);
        inventoryRepository.saveAndFlush(inventory);
        Long inventoryMasterId = inventoryMaster.getId();

        // Get all the inventoryList where inventoryMaster equals to inventoryMasterId
        defaultInventoryShouldBeFound("inventoryMasterId.equals=" + inventoryMasterId);

        // Get all the inventoryList where inventoryMaster equals to (inventoryMasterId + 1)
        defaultInventoryShouldNotBeFound("inventoryMasterId.equals=" + (inventoryMasterId + 1));
    }

    @Test
    @Transactional
    void getAllInventoriesBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);
        Supplier supplier;
        if (TestUtil.findAll(em, Supplier.class).isEmpty()) {
            supplier = SupplierResourceIT.createEntity(em);
            em.persist(supplier);
            em.flush();
        } else {
            supplier = TestUtil.findAll(em, Supplier.class).get(0);
        }
        em.persist(supplier);
        em.flush();
        inventory.setSupplier(supplier);
        inventoryRepository.saveAndFlush(inventory);
        Long supplierId = supplier.getId();

        // Get all the inventoryList where supplier equals to supplierId
        defaultInventoryShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the inventoryList where supplier equals to (supplierId + 1)
        defaultInventoryShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    @Test
    @Transactional
    void getAllInventoriesByHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);
        Hospital hospital;
        if (TestUtil.findAll(em, Hospital.class).isEmpty()) {
            hospital = HospitalResourceIT.createEntity(em);
            em.persist(hospital);
            em.flush();
        } else {
            hospital = TestUtil.findAll(em, Hospital.class).get(0);
        }
        em.persist(hospital);
        em.flush();
        inventory.setHospital(hospital);
        inventoryRepository.saveAndFlush(inventory);
        Long hospitalId = hospital.getId();

        // Get all the inventoryList where hospital equals to hospitalId
        defaultInventoryShouldBeFound("hospitalId.equals=" + hospitalId);

        // Get all the inventoryList where hospital equals to (hospitalId + 1)
        defaultInventoryShouldNotBeFound("hospitalId.equals=" + (hospitalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryShouldBeFound(String filter) throws Exception {
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].capcity").value(hasItem(DEFAULT_CAPCITY.intValue())))
            .andExpect(jsonPath("$.[*].installedCapcity").value(hasItem(DEFAULT_INSTALLED_CAPCITY.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryShouldNotBeFound(String filter) throws Exception {
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInventory() throws Exception {
        // Get the inventory
        restInventoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory
        Inventory updatedInventory = inventoryRepository.findById(inventory.getId()).get();
        // Disconnect from session so that the updates on updatedInventory are not directly saved in db
        em.detach(updatedInventory);
        updatedInventory
            .stock(UPDATED_STOCK)
            .capcity(UPDATED_CAPCITY)
            .installedCapcity(UPDATED_INSTALLED_CAPCITY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        InventoryDTO inventoryDTO = inventoryMapper.toDto(updatedInventory);

        restInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testInventory.getCapcity()).isEqualTo(UPDATED_CAPCITY);
        assertThat(testInventory.getInstalledCapcity()).isEqualTo(UPDATED_INSTALLED_CAPCITY);
        assertThat(testInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInventoryWithPatch() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory using partial update
        Inventory partialUpdatedInventory = new Inventory();
        partialUpdatedInventory.setId(inventory.getId());

        partialUpdatedInventory.capcity(UPDATED_CAPCITY).installedCapcity(UPDATED_INSTALLED_CAPCITY);

        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInventory))
            )
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testInventory.getCapcity()).isEqualTo(UPDATED_CAPCITY);
        assertThat(testInventory.getInstalledCapcity()).isEqualTo(UPDATED_INSTALLED_CAPCITY);
        assertThat(testInventory.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testInventory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateInventoryWithPatch() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory using partial update
        Inventory partialUpdatedInventory = new Inventory();
        partialUpdatedInventory.setId(inventory.getId());

        partialUpdatedInventory
            .stock(UPDATED_STOCK)
            .capcity(UPDATED_CAPCITY)
            .installedCapcity(UPDATED_INSTALLED_CAPCITY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInventory))
            )
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testInventory.getCapcity()).isEqualTo(UPDATED_CAPCITY);
        assertThat(testInventory.getInstalledCapcity()).isEqualTo(UPDATED_INSTALLED_CAPCITY);
        assertThat(testInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeDelete = inventoryRepository.findAll().size();

        // Delete the inventory
        restInventoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
