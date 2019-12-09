package by.tractorsheart.web.rest;

import by.tractorsheart.MonoHeartApp;
import by.tractorsheart.domain.PartT;
import by.tractorsheart.domain.ModuleT;
import by.tractorsheart.domain.ModelT;
import by.tractorsheart.repository.PartTRepository;
import by.tractorsheart.service.PartTService;
import by.tractorsheart.service.dto.PartTDTO;
import by.tractorsheart.service.mapper.PartTMapper;
import by.tractorsheart.web.rest.errors.ExceptionTranslator;
import by.tractorsheart.service.dto.PartTCriteria;
import by.tractorsheart.service.PartTQueryService;

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
 * Integration tests for the {@link PartTResource} REST controller.
 */
@SpringBootTest(classes = MonoHeartApp.class)
public class PartTResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PartTRepository partTRepository;

    @Mock
    private PartTRepository partTRepositoryMock;

    @Autowired
    private PartTMapper partTMapper;

    @Mock
    private PartTService partTServiceMock;

    @Autowired
    private PartTService partTService;

    @Autowired
    private PartTQueryService partTQueryService;

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

    private MockMvc restPartTMockMvc;

    private PartT partT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartTResource partTResource = new PartTResource(partTService, partTQueryService);
        this.restPartTMockMvc = MockMvcBuilders.standaloneSetup(partTResource)
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
    public static PartT createEntity(EntityManager em) {
        PartT partT = new PartT()
            .name(DEFAULT_NAME);
        return partT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartT createUpdatedEntity(EntityManager em) {
        PartT partT = new PartT()
            .name(UPDATED_NAME);
        return partT;
    }

    @BeforeEach
    public void initTest() {
        partT = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartT() throws Exception {
        int databaseSizeBeforeCreate = partTRepository.findAll().size();

        // Create the PartT
        PartTDTO partTDTO = partTMapper.toDto(partT);
        restPartTMockMvc.perform(post("/api/part-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partTDTO)))
            .andExpect(status().isCreated());

        // Validate the PartT in the database
        List<PartT> partTList = partTRepository.findAll();
        assertThat(partTList).hasSize(databaseSizeBeforeCreate + 1);
        PartT testPartT = partTList.get(partTList.size() - 1);
        assertThat(testPartT.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPartTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partTRepository.findAll().size();

        // Create the PartT with an existing ID
        partT.setId(1L);
        PartTDTO partTDTO = partTMapper.toDto(partT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartTMockMvc.perform(post("/api/part-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartT in the database
        List<PartT> partTList = partTRepository.findAll();
        assertThat(partTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartTS() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        // Get all the partTList
        restPartTMockMvc.perform(get("/api/part-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPartTSWithEagerRelationshipsIsEnabled() throws Exception {
        PartTResource partTResource = new PartTResource(partTServiceMock, partTQueryService);
        when(partTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPartTMockMvc = MockMvcBuilders.standaloneSetup(partTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPartTMockMvc.perform(get("/api/part-ts?eagerload=true"))
        .andExpect(status().isOk());

        verify(partTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPartTSWithEagerRelationshipsIsNotEnabled() throws Exception {
        PartTResource partTResource = new PartTResource(partTServiceMock, partTQueryService);
            when(partTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPartTMockMvc = MockMvcBuilders.standaloneSetup(partTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPartTMockMvc.perform(get("/api/part-ts?eagerload=true"))
        .andExpect(status().isOk());

            verify(partTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPartT() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        // Get the partT
        restPartTMockMvc.perform(get("/api/part-ts/{id}", partT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partT.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getPartTSByIdFiltering() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        Long id = partT.getId();

        defaultPartTShouldBeFound("id.equals=" + id);
        defaultPartTShouldNotBeFound("id.notEquals=" + id);

        defaultPartTShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPartTShouldNotBeFound("id.greaterThan=" + id);

        defaultPartTShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPartTShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPartTSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        // Get all the partTList where name equals to DEFAULT_NAME
        defaultPartTShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the partTList where name equals to UPDATED_NAME
        defaultPartTShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPartTSByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        // Get all the partTList where name not equals to DEFAULT_NAME
        defaultPartTShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the partTList where name not equals to UPDATED_NAME
        defaultPartTShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPartTSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        // Get all the partTList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPartTShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the partTList where name equals to UPDATED_NAME
        defaultPartTShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPartTSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        // Get all the partTList where name is not null
        defaultPartTShouldBeFound("name.specified=true");

        // Get all the partTList where name is null
        defaultPartTShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartTSByNameContainsSomething() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        // Get all the partTList where name contains DEFAULT_NAME
        defaultPartTShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the partTList where name contains UPDATED_NAME
        defaultPartTShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPartTSByNameNotContainsSomething() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        // Get all the partTList where name does not contain DEFAULT_NAME
        defaultPartTShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the partTList where name does not contain UPDATED_NAME
        defaultPartTShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPartTSByModuleTIsEqualToSomething() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);
        ModuleT moduleT = ModuleTResourceIT.createEntity(em);
        em.persist(moduleT);
        em.flush();
        partT.addModuleT(moduleT);
        partTRepository.saveAndFlush(partT);
        Long moduleTId = moduleT.getId();

        // Get all the partTList where moduleT equals to moduleTId
        defaultPartTShouldBeFound("moduleTId.equals=" + moduleTId);

        // Get all the partTList where moduleT equals to moduleTId + 1
        defaultPartTShouldNotBeFound("moduleTId.equals=" + (moduleTId + 1));
    }


    @Test
    @Transactional
    public void getAllPartTSByModelTIsEqualToSomething() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);
        ModelT modelT = ModelTResourceIT.createEntity(em);
        em.persist(modelT);
        em.flush();
        partT.addModelT(modelT);
        partTRepository.saveAndFlush(partT);
        Long modelTId = modelT.getId();

        // Get all the partTList where modelT equals to modelTId
        defaultPartTShouldBeFound("modelTId.equals=" + modelTId);

        // Get all the partTList where modelT equals to modelTId + 1
        defaultPartTShouldNotBeFound("modelTId.equals=" + (modelTId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartTShouldBeFound(String filter) throws Exception {
        restPartTMockMvc.perform(get("/api/part-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restPartTMockMvc.perform(get("/api/part-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartTShouldNotBeFound(String filter) throws Exception {
        restPartTMockMvc.perform(get("/api/part-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartTMockMvc.perform(get("/api/part-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPartT() throws Exception {
        // Get the partT
        restPartTMockMvc.perform(get("/api/part-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartT() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        int databaseSizeBeforeUpdate = partTRepository.findAll().size();

        // Update the partT
        PartT updatedPartT = partTRepository.findById(partT.getId()).get();
        // Disconnect from session so that the updates on updatedPartT are not directly saved in db
        em.detach(updatedPartT);
        updatedPartT
            .name(UPDATED_NAME);
        PartTDTO partTDTO = partTMapper.toDto(updatedPartT);

        restPartTMockMvc.perform(put("/api/part-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partTDTO)))
            .andExpect(status().isOk());

        // Validate the PartT in the database
        List<PartT> partTList = partTRepository.findAll();
        assertThat(partTList).hasSize(databaseSizeBeforeUpdate);
        PartT testPartT = partTList.get(partTList.size() - 1);
        assertThat(testPartT.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPartT() throws Exception {
        int databaseSizeBeforeUpdate = partTRepository.findAll().size();

        // Create the PartT
        PartTDTO partTDTO = partTMapper.toDto(partT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartTMockMvc.perform(put("/api/part-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartT in the database
        List<PartT> partTList = partTRepository.findAll();
        assertThat(partTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartT() throws Exception {
        // Initialize the database
        partTRepository.saveAndFlush(partT);

        int databaseSizeBeforeDelete = partTRepository.findAll().size();

        // Delete the partT
        restPartTMockMvc.perform(delete("/api/part-ts/{id}", partT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartT> partTList = partTRepository.findAll();
        assertThat(partTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
