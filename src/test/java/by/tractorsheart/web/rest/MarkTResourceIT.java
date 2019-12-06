package by.tractorsheart.web.rest;

import by.tractorsheart.MonoHeartApp;
import by.tractorsheart.domain.MarkT;
import by.tractorsheart.domain.TypeT;
import by.tractorsheart.repository.MarkTRepository;
import by.tractorsheart.service.MarkTService;
import by.tractorsheart.service.dto.MarkTDTO;
import by.tractorsheart.service.mapper.MarkTMapper;
import by.tractorsheart.web.rest.errors.ExceptionTranslator;
import by.tractorsheart.service.dto.MarkTCriteria;
import by.tractorsheart.service.MarkTQueryService;

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
 * Integration tests for the {@link MarkTResource} REST controller.
 */
@SpringBootTest(classes = MonoHeartApp.class)
public class MarkTResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MarkTRepository markTRepository;

    @Mock
    private MarkTRepository markTRepositoryMock;

    @Autowired
    private MarkTMapper markTMapper;

    @Mock
    private MarkTService markTServiceMock;

    @Autowired
    private MarkTService markTService;

    @Autowired
    private MarkTQueryService markTQueryService;

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

    private MockMvc restMarkTMockMvc;

    private MarkT markT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarkTResource markTResource = new MarkTResource(markTService, markTQueryService);
        this.restMarkTMockMvc = MockMvcBuilders.standaloneSetup(markTResource)
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
    public static MarkT createEntity(EntityManager em) {
        MarkT markT = new MarkT()
            .name(DEFAULT_NAME);
        return markT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarkT createUpdatedEntity(EntityManager em) {
        MarkT markT = new MarkT()
            .name(UPDATED_NAME);
        return markT;
    }

    @BeforeEach
    public void initTest() {
        markT = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarkT() throws Exception {
        int databaseSizeBeforeCreate = markTRepository.findAll().size();

        // Create the MarkT
        MarkTDTO markTDTO = markTMapper.toDto(markT);
        restMarkTMockMvc.perform(post("/api/mark-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markTDTO)))
            .andExpect(status().isCreated());

        // Validate the MarkT in the database
        List<MarkT> markTList = markTRepository.findAll();
        assertThat(markTList).hasSize(databaseSizeBeforeCreate + 1);
        MarkT testMarkT = markTList.get(markTList.size() - 1);
        assertThat(testMarkT.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMarkTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = markTRepository.findAll().size();

        // Create the MarkT with an existing ID
        markT.setId(1L);
        MarkTDTO markTDTO = markTMapper.toDto(markT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarkTMockMvc.perform(post("/api/mark-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MarkT in the database
        List<MarkT> markTList = markTRepository.findAll();
        assertThat(markTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMarkTS() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        // Get all the markTList
        restMarkTMockMvc.perform(get("/api/mark-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(markT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMarkTSWithEagerRelationshipsIsEnabled() throws Exception {
        MarkTResource markTResource = new MarkTResource(markTServiceMock, markTQueryService);
        when(markTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMarkTMockMvc = MockMvcBuilders.standaloneSetup(markTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMarkTMockMvc.perform(get("/api/mark-ts?eagerload=true"))
        .andExpect(status().isOk());

        verify(markTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMarkTSWithEagerRelationshipsIsNotEnabled() throws Exception {
        MarkTResource markTResource = new MarkTResource(markTServiceMock, markTQueryService);
            when(markTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMarkTMockMvc = MockMvcBuilders.standaloneSetup(markTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMarkTMockMvc.perform(get("/api/mark-ts?eagerload=true"))
        .andExpect(status().isOk());

            verify(markTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMarkT() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        // Get the markT
        restMarkTMockMvc.perform(get("/api/mark-ts/{id}", markT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(markT.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getMarkTSByIdFiltering() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        Long id = markT.getId();

        defaultMarkTShouldBeFound("id.equals=" + id);
        defaultMarkTShouldNotBeFound("id.notEquals=" + id);

        defaultMarkTShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMarkTShouldNotBeFound("id.greaterThan=" + id);

        defaultMarkTShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMarkTShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMarkTSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        // Get all the markTList where name equals to DEFAULT_NAME
        defaultMarkTShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the markTList where name equals to UPDATED_NAME
        defaultMarkTShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMarkTSByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        // Get all the markTList where name not equals to DEFAULT_NAME
        defaultMarkTShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the markTList where name not equals to UPDATED_NAME
        defaultMarkTShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMarkTSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        // Get all the markTList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMarkTShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the markTList where name equals to UPDATED_NAME
        defaultMarkTShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMarkTSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        // Get all the markTList where name is not null
        defaultMarkTShouldBeFound("name.specified=true");

        // Get all the markTList where name is null
        defaultMarkTShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMarkTSByNameContainsSomething() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        // Get all the markTList where name contains DEFAULT_NAME
        defaultMarkTShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the markTList where name contains UPDATED_NAME
        defaultMarkTShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMarkTSByNameNotContainsSomething() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        // Get all the markTList where name does not contain DEFAULT_NAME
        defaultMarkTShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the markTList where name does not contain UPDATED_NAME
        defaultMarkTShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMarkTSByTypeTIsEqualToSomething() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);
        TypeT typeT = TypeTResourceIT.createEntity(em);
        em.persist(typeT);
        em.flush();
        markT.addTypeT(typeT);
        markTRepository.saveAndFlush(markT);
        Long typeTId = typeT.getId();

        // Get all the markTList where typeT equals to typeTId
        defaultMarkTShouldBeFound("typeTId.equals=" + typeTId);

        // Get all the markTList where typeT equals to typeTId + 1
        defaultMarkTShouldNotBeFound("typeTId.equals=" + (typeTId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMarkTShouldBeFound(String filter) throws Exception {
        restMarkTMockMvc.perform(get("/api/mark-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(markT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restMarkTMockMvc.perform(get("/api/mark-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMarkTShouldNotBeFound(String filter) throws Exception {
        restMarkTMockMvc.perform(get("/api/mark-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMarkTMockMvc.perform(get("/api/mark-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMarkT() throws Exception {
        // Get the markT
        restMarkTMockMvc.perform(get("/api/mark-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarkT() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        int databaseSizeBeforeUpdate = markTRepository.findAll().size();

        // Update the markT
        MarkT updatedMarkT = markTRepository.findById(markT.getId()).get();
        // Disconnect from session so that the updates on updatedMarkT are not directly saved in db
        em.detach(updatedMarkT);
        updatedMarkT
            .name(UPDATED_NAME);
        MarkTDTO markTDTO = markTMapper.toDto(updatedMarkT);

        restMarkTMockMvc.perform(put("/api/mark-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markTDTO)))
            .andExpect(status().isOk());

        // Validate the MarkT in the database
        List<MarkT> markTList = markTRepository.findAll();
        assertThat(markTList).hasSize(databaseSizeBeforeUpdate);
        MarkT testMarkT = markTList.get(markTList.size() - 1);
        assertThat(testMarkT.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMarkT() throws Exception {
        int databaseSizeBeforeUpdate = markTRepository.findAll().size();

        // Create the MarkT
        MarkTDTO markTDTO = markTMapper.toDto(markT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarkTMockMvc.perform(put("/api/mark-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MarkT in the database
        List<MarkT> markTList = markTRepository.findAll();
        assertThat(markTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMarkT() throws Exception {
        // Initialize the database
        markTRepository.saveAndFlush(markT);

        int databaseSizeBeforeDelete = markTRepository.findAll().size();

        // Delete the markT
        restMarkTMockMvc.perform(delete("/api/mark-ts/{id}", markT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarkT> markTList = markTRepository.findAll();
        assertThat(markTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
