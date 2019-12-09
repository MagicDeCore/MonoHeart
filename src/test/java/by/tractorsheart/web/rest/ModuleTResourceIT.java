package by.tractorsheart.web.rest;

import by.tractorsheart.MonoHeartApp;
import by.tractorsheart.domain.ModuleT;
import by.tractorsheart.domain.NodeT;
import by.tractorsheart.domain.PartT;
import by.tractorsheart.repository.ModuleTRepository;
import by.tractorsheart.service.ModuleTService;
import by.tractorsheart.service.dto.ModuleTDTO;
import by.tractorsheart.service.mapper.ModuleTMapper;
import by.tractorsheart.web.rest.errors.ExceptionTranslator;
import by.tractorsheart.service.dto.ModuleTCriteria;
import by.tractorsheart.service.ModuleTQueryService;

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
 * Integration tests for the {@link ModuleTResource} REST controller.
 */
@SpringBootTest(classes = MonoHeartApp.class)
public class ModuleTResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ModuleTRepository moduleTRepository;

    @Mock
    private ModuleTRepository moduleTRepositoryMock;

    @Autowired
    private ModuleTMapper moduleTMapper;

    @Mock
    private ModuleTService moduleTServiceMock;

    @Autowired
    private ModuleTService moduleTService;

    @Autowired
    private ModuleTQueryService moduleTQueryService;

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

    private MockMvc restModuleTMockMvc;

    private ModuleT moduleT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModuleTResource moduleTResource = new ModuleTResource(moduleTService, moduleTQueryService);
        this.restModuleTMockMvc = MockMvcBuilders.standaloneSetup(moduleTResource)
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
    public static ModuleT createEntity(EntityManager em) {
        ModuleT moduleT = new ModuleT()
            .name(DEFAULT_NAME);
        return moduleT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModuleT createUpdatedEntity(EntityManager em) {
        ModuleT moduleT = new ModuleT()
            .name(UPDATED_NAME);
        return moduleT;
    }

    @BeforeEach
    public void initTest() {
        moduleT = createEntity(em);
    }

    @Test
    @Transactional
    public void createModuleT() throws Exception {
        int databaseSizeBeforeCreate = moduleTRepository.findAll().size();

        // Create the ModuleT
        ModuleTDTO moduleTDTO = moduleTMapper.toDto(moduleT);
        restModuleTMockMvc.perform(post("/api/module-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleTDTO)))
            .andExpect(status().isCreated());

        // Validate the ModuleT in the database
        List<ModuleT> moduleTList = moduleTRepository.findAll();
        assertThat(moduleTList).hasSize(databaseSizeBeforeCreate + 1);
        ModuleT testModuleT = moduleTList.get(moduleTList.size() - 1);
        assertThat(testModuleT.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createModuleTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduleTRepository.findAll().size();

        // Create the ModuleT with an existing ID
        moduleT.setId(1L);
        ModuleTDTO moduleTDTO = moduleTMapper.toDto(moduleT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleTMockMvc.perform(post("/api/module-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleT in the database
        List<ModuleT> moduleTList = moduleTRepository.findAll();
        assertThat(moduleTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllModuleTS() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        // Get all the moduleTList
        restModuleTMockMvc.perform(get("/api/module-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllModuleTSWithEagerRelationshipsIsEnabled() throws Exception {
        ModuleTResource moduleTResource = new ModuleTResource(moduleTServiceMock, moduleTQueryService);
        when(moduleTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restModuleTMockMvc = MockMvcBuilders.standaloneSetup(moduleTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restModuleTMockMvc.perform(get("/api/module-ts?eagerload=true"))
        .andExpect(status().isOk());

        verify(moduleTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllModuleTSWithEagerRelationshipsIsNotEnabled() throws Exception {
        ModuleTResource moduleTResource = new ModuleTResource(moduleTServiceMock, moduleTQueryService);
            when(moduleTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restModuleTMockMvc = MockMvcBuilders.standaloneSetup(moduleTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restModuleTMockMvc.perform(get("/api/module-ts?eagerload=true"))
        .andExpect(status().isOk());

            verify(moduleTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getModuleT() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        // Get the moduleT
        restModuleTMockMvc.perform(get("/api/module-ts/{id}", moduleT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moduleT.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getModuleTSByIdFiltering() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        Long id = moduleT.getId();

        defaultModuleTShouldBeFound("id.equals=" + id);
        defaultModuleTShouldNotBeFound("id.notEquals=" + id);

        defaultModuleTShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultModuleTShouldNotBeFound("id.greaterThan=" + id);

        defaultModuleTShouldBeFound("id.lessThanOrEqual=" + id);
        defaultModuleTShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllModuleTSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        // Get all the moduleTList where name equals to DEFAULT_NAME
        defaultModuleTShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the moduleTList where name equals to UPDATED_NAME
        defaultModuleTShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllModuleTSByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        // Get all the moduleTList where name not equals to DEFAULT_NAME
        defaultModuleTShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the moduleTList where name not equals to UPDATED_NAME
        defaultModuleTShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllModuleTSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        // Get all the moduleTList where name in DEFAULT_NAME or UPDATED_NAME
        defaultModuleTShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the moduleTList where name equals to UPDATED_NAME
        defaultModuleTShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllModuleTSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        // Get all the moduleTList where name is not null
        defaultModuleTShouldBeFound("name.specified=true");

        // Get all the moduleTList where name is null
        defaultModuleTShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllModuleTSByNameContainsSomething() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        // Get all the moduleTList where name contains DEFAULT_NAME
        defaultModuleTShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the moduleTList where name contains UPDATED_NAME
        defaultModuleTShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllModuleTSByNameNotContainsSomething() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        // Get all the moduleTList where name does not contain DEFAULT_NAME
        defaultModuleTShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the moduleTList where name does not contain UPDATED_NAME
        defaultModuleTShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllModuleTSByNodeTIsEqualToSomething() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);
        NodeT nodeT = NodeTResourceIT.createEntity(em);
        em.persist(nodeT);
        em.flush();
        moduleT.addNodeT(nodeT);
        moduleTRepository.saveAndFlush(moduleT);
        Long nodeTId = nodeT.getId();

        // Get all the moduleTList where nodeT equals to nodeTId
        defaultModuleTShouldBeFound("nodeTId.equals=" + nodeTId);

        // Get all the moduleTList where nodeT equals to nodeTId + 1
        defaultModuleTShouldNotBeFound("nodeTId.equals=" + (nodeTId + 1));
    }


    @Test
    @Transactional
    public void getAllModuleTSByPartTIsEqualToSomething() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);
        PartT partT = PartTResourceIT.createEntity(em);
        em.persist(partT);
        em.flush();
        moduleT.addPartT(partT);
        moduleTRepository.saveAndFlush(moduleT);
        Long partTId = partT.getId();

        // Get all the moduleTList where partT equals to partTId
        defaultModuleTShouldBeFound("partTId.equals=" + partTId);

        // Get all the moduleTList where partT equals to partTId + 1
        defaultModuleTShouldNotBeFound("partTId.equals=" + (partTId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultModuleTShouldBeFound(String filter) throws Exception {
        restModuleTMockMvc.perform(get("/api/module-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restModuleTMockMvc.perform(get("/api/module-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultModuleTShouldNotBeFound(String filter) throws Exception {
        restModuleTMockMvc.perform(get("/api/module-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restModuleTMockMvc.perform(get("/api/module-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingModuleT() throws Exception {
        // Get the moduleT
        restModuleTMockMvc.perform(get("/api/module-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModuleT() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        int databaseSizeBeforeUpdate = moduleTRepository.findAll().size();

        // Update the moduleT
        ModuleT updatedModuleT = moduleTRepository.findById(moduleT.getId()).get();
        // Disconnect from session so that the updates on updatedModuleT are not directly saved in db
        em.detach(updatedModuleT);
        updatedModuleT
            .name(UPDATED_NAME);
        ModuleTDTO moduleTDTO = moduleTMapper.toDto(updatedModuleT);

        restModuleTMockMvc.perform(put("/api/module-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleTDTO)))
            .andExpect(status().isOk());

        // Validate the ModuleT in the database
        List<ModuleT> moduleTList = moduleTRepository.findAll();
        assertThat(moduleTList).hasSize(databaseSizeBeforeUpdate);
        ModuleT testModuleT = moduleTList.get(moduleTList.size() - 1);
        assertThat(testModuleT.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingModuleT() throws Exception {
        int databaseSizeBeforeUpdate = moduleTRepository.findAll().size();

        // Create the ModuleT
        ModuleTDTO moduleTDTO = moduleTMapper.toDto(moduleT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleTMockMvc.perform(put("/api/module-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleT in the database
        List<ModuleT> moduleTList = moduleTRepository.findAll();
        assertThat(moduleTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModuleT() throws Exception {
        // Initialize the database
        moduleTRepository.saveAndFlush(moduleT);

        int databaseSizeBeforeDelete = moduleTRepository.findAll().size();

        // Delete the moduleT
        restModuleTMockMvc.perform(delete("/api/module-ts/{id}", moduleT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModuleT> moduleTList = moduleTRepository.findAll();
        assertThat(moduleTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
