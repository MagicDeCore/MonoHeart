package by.tractorsheart.web.rest;

import by.tractorsheart.MonoHeartApp;
import by.tractorsheart.domain.TypeT;
import by.tractorsheart.domain.ModelT;
import by.tractorsheart.domain.MarkT;
import by.tractorsheart.repository.TypeTRepository;
import by.tractorsheart.service.TypeTService;
import by.tractorsheart.service.dto.TypeTDTO;
import by.tractorsheart.service.mapper.TypeTMapper;
import by.tractorsheart.web.rest.errors.ExceptionTranslator;
import by.tractorsheart.service.dto.TypeTCriteria;
import by.tractorsheart.service.TypeTQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static by.tractorsheart.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TypeTResource} REST controller.
 */
@SpringBootTest(classes = MonoHeartApp.class)
public class TypeTResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TypeTRepository typeTRepository;

    @Mock
    private TypeTRepository typeTRepositoryMock;

    @Autowired
    private TypeTMapper typeTMapper;

    @Mock
    private TypeTService typeTServiceMock;

    @Autowired
    private TypeTService typeTService;

    @Autowired
    private TypeTQueryService typeTQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTypeTMockMvc;

    private TypeT typeT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeTResource typeTResource = new TypeTResource(typeTService, typeTQueryService);
        this.restTypeTMockMvc = MockMvcBuilders.standaloneSetup(typeTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeT createEntity(EntityManager em) {
        TypeT typeT = new TypeT()
            .name(DEFAULT_NAME);
        return typeT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeT createUpdatedEntity(EntityManager em) {
        TypeT typeT = new TypeT()
            .name(UPDATED_NAME);
        return typeT;
    }

    @BeforeEach
    public void initTest() {
        typeT = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeT() throws Exception {
        int databaseSizeBeforeCreate = typeTRepository.findAll().size();

        // Create the TypeT
        TypeTDTO typeTDTO = typeTMapper.toDto(typeT);
        restTypeTMockMvc.perform(post("/api/type-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeT in the database
        List<TypeT> typeTList = typeTRepository.findAll();
        assertThat(typeTList).hasSize(databaseSizeBeforeCreate + 1);
        TypeT testTypeT = typeTList.get(typeTList.size() - 1);
        assertThat(testTypeT.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTypeTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeTRepository.findAll().size();

        // Create the TypeT with an existing ID
        typeT.setId(1L);
        TypeTDTO typeTDTO = typeTMapper.toDto(typeT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeTMockMvc.perform(post("/api/type-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeT in the database
        List<TypeT> typeTList = typeTRepository.findAll();
        assertThat(typeTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeTS() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        // Get all the typeTList
        restTypeTMockMvc.perform(get("/api/type-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTypeTSWithEagerRelationshipsIsEnabled() throws Exception {
        TypeTResource typeTResource = new TypeTResource(typeTServiceMock, typeTQueryService);
        when(typeTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTypeTMockMvc = MockMvcBuilders.standaloneSetup(typeTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTypeTMockMvc.perform(get("/api/type-ts?eagerload=true"))
        .andExpect(status().isOk());

        verify(typeTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTypeTSWithEagerRelationshipsIsNotEnabled() throws Exception {
        TypeTResource typeTResource = new TypeTResource(typeTServiceMock, typeTQueryService);
            when(typeTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTypeTMockMvc = MockMvcBuilders.standaloneSetup(typeTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTypeTMockMvc.perform(get("/api/type-ts?eagerload=true"))
        .andExpect(status().isOk());

            verify(typeTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTypeT() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        // Get the typeT
        restTypeTMockMvc.perform(get("/api/type-ts/{id}", typeT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeT.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getTypeTSByIdFiltering() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        Long id = typeT.getId();

        defaultTypeTShouldBeFound("id.equals=" + id);
        defaultTypeTShouldNotBeFound("id.notEquals=" + id);

        defaultTypeTShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTypeTShouldNotBeFound("id.greaterThan=" + id);

        defaultTypeTShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTypeTShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTypeTSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        // Get all the typeTList where name equals to DEFAULT_NAME
        defaultTypeTShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the typeTList where name equals to UPDATED_NAME
        defaultTypeTShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTypeTSByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        // Get all the typeTList where name not equals to DEFAULT_NAME
        defaultTypeTShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the typeTList where name not equals to UPDATED_NAME
        defaultTypeTShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTypeTSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        // Get all the typeTList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTypeTShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the typeTList where name equals to UPDATED_NAME
        defaultTypeTShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTypeTSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        // Get all the typeTList where name is not null
        defaultTypeTShouldBeFound("name.specified=true");

        // Get all the typeTList where name is null
        defaultTypeTShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTypeTSByNameContainsSomething() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        // Get all the typeTList where name contains DEFAULT_NAME
        defaultTypeTShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the typeTList where name contains UPDATED_NAME
        defaultTypeTShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTypeTSByNameNotContainsSomething() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        // Get all the typeTList where name does not contain DEFAULT_NAME
        defaultTypeTShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the typeTList where name does not contain UPDATED_NAME
        defaultTypeTShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTypeTSByModelTIsEqualToSomething() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);
        ModelT modelT = ModelTResourceIT.createEntity(em);
        em.persist(modelT);
        em.flush();
        typeT.addModelT(modelT);
        typeTRepository.saveAndFlush(typeT);
        Long modelTId = modelT.getId();

        // Get all the typeTList where modelT equals to modelTId
        defaultTypeTShouldBeFound("modelTId.equals=" + modelTId);

        // Get all the typeTList where modelT equals to modelTId + 1
        defaultTypeTShouldNotBeFound("modelTId.equals=" + (modelTId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeTSByMarkTIsEqualToSomething() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);
        MarkT markT = MarkTResourceIT.createEntity(em);
        em.persist(markT);
        em.flush();
        typeT.addMarkT(markT);
        typeTRepository.saveAndFlush(typeT);
        Long markTId = markT.getId();

        // Get all the typeTList where markT equals to markTId
        defaultTypeTShouldBeFound("markTId.equals=" + markTId);

        // Get all the typeTList where markT equals to markTId + 1
        defaultTypeTShouldNotBeFound("markTId.equals=" + (markTId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTypeTShouldBeFound(String filter) throws Exception {
        restTypeTMockMvc.perform(get("/api/type-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTypeTMockMvc.perform(get("/api/type-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTypeTShouldNotBeFound(String filter) throws Exception {
        restTypeTMockMvc.perform(get("/api/type-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypeTMockMvc.perform(get("/api/type-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTypeT() throws Exception {
        // Get the typeT
        restTypeTMockMvc.perform(get("/api/type-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeT() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        int databaseSizeBeforeUpdate = typeTRepository.findAll().size();

        // Update the typeT
        TypeT updatedTypeT = typeTRepository.findById(typeT.getId()).get();
        // Disconnect from session so that the updates on updatedTypeT are not directly saved in db
        em.detach(updatedTypeT);
        updatedTypeT
            .name(UPDATED_NAME);
        TypeTDTO typeTDTO = typeTMapper.toDto(updatedTypeT);

        restTypeTMockMvc.perform(put("/api/type-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTDTO)))
            .andExpect(status().isOk());

        // Validate the TypeT in the database
        List<TypeT> typeTList = typeTRepository.findAll();
        assertThat(typeTList).hasSize(databaseSizeBeforeUpdate);
        TypeT testTypeT = typeTList.get(typeTList.size() - 1);
        assertThat(testTypeT.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeT() throws Exception {
        int databaseSizeBeforeUpdate = typeTRepository.findAll().size();

        // Create the TypeT
        TypeTDTO typeTDTO = typeTMapper.toDto(typeT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeTMockMvc.perform(put("/api/type-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeT in the database
        List<TypeT> typeTList = typeTRepository.findAll();
        assertThat(typeTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeT() throws Exception {
        // Initialize the database
        typeTRepository.saveAndFlush(typeT);

        int databaseSizeBeforeDelete = typeTRepository.findAll().size();

        // Delete the typeT
        restTypeTMockMvc.perform(delete("/api/type-ts/{id}", typeT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeT> typeTList = typeTRepository.findAll();
        assertThat(typeTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
