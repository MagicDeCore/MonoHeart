package by.tractorsheart.web.rest;

import by.tractorsheart.MonoHeartApp;
import by.tractorsheart.domain.ModelT;
import by.tractorsheart.domain.PartT;
import by.tractorsheart.domain.TypeT;
import by.tractorsheart.repository.ModelTRepository;
import by.tractorsheart.service.ModelTService;
import by.tractorsheart.service.dto.ModelTDTO;
import by.tractorsheart.service.mapper.ModelTMapper;
import by.tractorsheart.web.rest.errors.ExceptionTranslator;
import by.tractorsheart.service.dto.ModelTCriteria;
import by.tractorsheart.service.ModelTQueryService;

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
 * Integration tests for the {@link ModelTResource} REST controller.
 */
@SpringBootTest(classes = MonoHeartApp.class)
public class ModelTResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WRONG = "AAAAAAAAAA";
    private static final String UPDATED_WRONG = "BBBBBBBBBB";

    @Autowired
    private ModelTRepository modelTRepository;

    @Mock
    private ModelTRepository modelTRepositoryMock;

    @Autowired
    private ModelTMapper modelTMapper;

    @Mock
    private ModelTService modelTServiceMock;

    @Autowired
    private ModelTService modelTService;

    @Autowired
    private ModelTQueryService modelTQueryService;

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

    private MockMvc restModelTMockMvc;

    private ModelT modelT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModelTResource modelTResource = new ModelTResource(modelTService, modelTQueryService);
        this.restModelTMockMvc = MockMvcBuilders.standaloneSetup(modelTResource)
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
    public static ModelT createEntity(EntityManager em) {
        ModelT modelT = new ModelT()
            .name(DEFAULT_NAME)
            .wrong(DEFAULT_WRONG);
        return modelT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModelT createUpdatedEntity(EntityManager em) {
        ModelT modelT = new ModelT()
            .name(UPDATED_NAME)
            .wrong(UPDATED_WRONG);
        return modelT;
    }

    @BeforeEach
    public void initTest() {
        modelT = createEntity(em);
    }

    @Test
    @Transactional
    public void createModelT() throws Exception {
        int databaseSizeBeforeCreate = modelTRepository.findAll().size();

        // Create the ModelT
        ModelTDTO modelTDTO = modelTMapper.toDto(modelT);
        restModelTMockMvc.perform(post("/api/model-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelTDTO)))
            .andExpect(status().isCreated());

        // Validate the ModelT in the database
        List<ModelT> modelTList = modelTRepository.findAll();
        assertThat(modelTList).hasSize(databaseSizeBeforeCreate + 1);
        ModelT testModelT = modelTList.get(modelTList.size() - 1);
        assertThat(testModelT.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModelT.getWrong()).isEqualTo(DEFAULT_WRONG);
    }

    @Test
    @Transactional
    public void createModelTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modelTRepository.findAll().size();

        // Create the ModelT with an existing ID
        modelT.setId(1L);
        ModelTDTO modelTDTO = modelTMapper.toDto(modelT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelTMockMvc.perform(post("/api/model-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModelT in the database
        List<ModelT> modelTList = modelTRepository.findAll();
        assertThat(modelTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllModelTS() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList
        restModelTMockMvc.perform(get("/api/model-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].wrong").value(hasItem(DEFAULT_WRONG)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllModelTSWithEagerRelationshipsIsEnabled() throws Exception {
        ModelTResource modelTResource = new ModelTResource(modelTServiceMock, modelTQueryService);
        when(modelTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restModelTMockMvc = MockMvcBuilders.standaloneSetup(modelTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restModelTMockMvc.perform(get("/api/model-ts?eagerload=true"))
        .andExpect(status().isOk());

        verify(modelTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllModelTSWithEagerRelationshipsIsNotEnabled() throws Exception {
        ModelTResource modelTResource = new ModelTResource(modelTServiceMock, modelTQueryService);
            when(modelTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restModelTMockMvc = MockMvcBuilders.standaloneSetup(modelTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restModelTMockMvc.perform(get("/api/model-ts?eagerload=true"))
        .andExpect(status().isOk());

            verify(modelTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getModelT() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get the modelT
        restModelTMockMvc.perform(get("/api/model-ts/{id}", modelT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modelT.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.wrong").value(DEFAULT_WRONG));
    }


    @Test
    @Transactional
    public void getModelTSByIdFiltering() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        Long id = modelT.getId();

        defaultModelTShouldBeFound("id.equals=" + id);
        defaultModelTShouldNotBeFound("id.notEquals=" + id);

        defaultModelTShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultModelTShouldNotBeFound("id.greaterThan=" + id);

        defaultModelTShouldBeFound("id.lessThanOrEqual=" + id);
        defaultModelTShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllModelTSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where name equals to DEFAULT_NAME
        defaultModelTShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the modelTList where name equals to UPDATED_NAME
        defaultModelTShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllModelTSByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where name not equals to DEFAULT_NAME
        defaultModelTShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the modelTList where name not equals to UPDATED_NAME
        defaultModelTShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllModelTSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where name in DEFAULT_NAME or UPDATED_NAME
        defaultModelTShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the modelTList where name equals to UPDATED_NAME
        defaultModelTShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllModelTSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where name is not null
        defaultModelTShouldBeFound("name.specified=true");

        // Get all the modelTList where name is null
        defaultModelTShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllModelTSByNameContainsSomething() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where name contains DEFAULT_NAME
        defaultModelTShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the modelTList where name contains UPDATED_NAME
        defaultModelTShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllModelTSByNameNotContainsSomething() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where name does not contain DEFAULT_NAME
        defaultModelTShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the modelTList where name does not contain UPDATED_NAME
        defaultModelTShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllModelTSByWrongIsEqualToSomething() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where wrong equals to DEFAULT_WRONG
        defaultModelTShouldBeFound("wrong.equals=" + DEFAULT_WRONG);

        // Get all the modelTList where wrong equals to UPDATED_WRONG
        defaultModelTShouldNotBeFound("wrong.equals=" + UPDATED_WRONG);
    }

    @Test
    @Transactional
    public void getAllModelTSByWrongIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where wrong not equals to DEFAULT_WRONG
        defaultModelTShouldNotBeFound("wrong.notEquals=" + DEFAULT_WRONG);

        // Get all the modelTList where wrong not equals to UPDATED_WRONG
        defaultModelTShouldBeFound("wrong.notEquals=" + UPDATED_WRONG);
    }

    @Test
    @Transactional
    public void getAllModelTSByWrongIsInShouldWork() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where wrong in DEFAULT_WRONG or UPDATED_WRONG
        defaultModelTShouldBeFound("wrong.in=" + DEFAULT_WRONG + "," + UPDATED_WRONG);

        // Get all the modelTList where wrong equals to UPDATED_WRONG
        defaultModelTShouldNotBeFound("wrong.in=" + UPDATED_WRONG);
    }

    @Test
    @Transactional
    public void getAllModelTSByWrongIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where wrong is not null
        defaultModelTShouldBeFound("wrong.specified=true");

        // Get all the modelTList where wrong is null
        defaultModelTShouldNotBeFound("wrong.specified=false");
    }
                @Test
    @Transactional
    public void getAllModelTSByWrongContainsSomething() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where wrong contains DEFAULT_WRONG
        defaultModelTShouldBeFound("wrong.contains=" + DEFAULT_WRONG);

        // Get all the modelTList where wrong contains UPDATED_WRONG
        defaultModelTShouldNotBeFound("wrong.contains=" + UPDATED_WRONG);
    }

    @Test
    @Transactional
    public void getAllModelTSByWrongNotContainsSomething() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        // Get all the modelTList where wrong does not contain DEFAULT_WRONG
        defaultModelTShouldNotBeFound("wrong.doesNotContain=" + DEFAULT_WRONG);

        // Get all the modelTList where wrong does not contain UPDATED_WRONG
        defaultModelTShouldBeFound("wrong.doesNotContain=" + UPDATED_WRONG);
    }


    @Test
    @Transactional
    public void getAllModelTSByPartTIsEqualToSomething() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);
        PartT partT = PartTResourceIT.createEntity(em);
        em.persist(partT);
        em.flush();
        modelT.addPartT(partT);
        modelTRepository.saveAndFlush(modelT);
        Long partTId = partT.getId();

        // Get all the modelTList where partT equals to partTId
        defaultModelTShouldBeFound("partTId.equals=" + partTId);

        // Get all the modelTList where partT equals to partTId + 1
        defaultModelTShouldNotBeFound("partTId.equals=" + (partTId + 1));
    }


    @Test
    @Transactional
    public void getAllModelTSByTypeTIsEqualToSomething() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);
        TypeT typeT = TypeTResourceIT.createEntity(em);
        em.persist(typeT);
        em.flush();
        modelT.addTypeT(typeT);
        modelTRepository.saveAndFlush(modelT);
        Long typeTId = typeT.getId();

        // Get all the modelTList where typeT equals to typeTId
        defaultModelTShouldBeFound("typeTId.equals=" + typeTId);

        // Get all the modelTList where typeT equals to typeTId + 1
        defaultModelTShouldNotBeFound("typeTId.equals=" + (typeTId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultModelTShouldBeFound(String filter) throws Exception {
        restModelTMockMvc.perform(get("/api/model-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].wrong").value(hasItem(DEFAULT_WRONG)));

        // Check, that the count call also returns 1
        restModelTMockMvc.perform(get("/api/model-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultModelTShouldNotBeFound(String filter) throws Exception {
        restModelTMockMvc.perform(get("/api/model-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restModelTMockMvc.perform(get("/api/model-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingModelT() throws Exception {
        // Get the modelT
        restModelTMockMvc.perform(get("/api/model-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModelT() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        int databaseSizeBeforeUpdate = modelTRepository.findAll().size();

        // Update the modelT
        ModelT updatedModelT = modelTRepository.findById(modelT.getId()).get();
        // Disconnect from session so that the updates on updatedModelT are not directly saved in db
        em.detach(updatedModelT);
        updatedModelT
            .name(UPDATED_NAME)
            .wrong(UPDATED_WRONG);
        ModelTDTO modelTDTO = modelTMapper.toDto(updatedModelT);

        restModelTMockMvc.perform(put("/api/model-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelTDTO)))
            .andExpect(status().isOk());

        // Validate the ModelT in the database
        List<ModelT> modelTList = modelTRepository.findAll();
        assertThat(modelTList).hasSize(databaseSizeBeforeUpdate);
        ModelT testModelT = modelTList.get(modelTList.size() - 1);
        assertThat(testModelT.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModelT.getWrong()).isEqualTo(UPDATED_WRONG);
    }

    @Test
    @Transactional
    public void updateNonExistingModelT() throws Exception {
        int databaseSizeBeforeUpdate = modelTRepository.findAll().size();

        // Create the ModelT
        ModelTDTO modelTDTO = modelTMapper.toDto(modelT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelTMockMvc.perform(put("/api/model-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModelT in the database
        List<ModelT> modelTList = modelTRepository.findAll();
        assertThat(modelTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModelT() throws Exception {
        // Initialize the database
        modelTRepository.saveAndFlush(modelT);

        int databaseSizeBeforeDelete = modelTRepository.findAll().size();

        // Delete the modelT
        restModelTMockMvc.perform(delete("/api/model-ts/{id}", modelT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModelT> modelTList = modelTRepository.findAll();
        assertThat(modelTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
