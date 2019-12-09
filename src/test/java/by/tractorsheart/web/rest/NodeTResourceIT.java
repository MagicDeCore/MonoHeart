package by.tractorsheart.web.rest;

import by.tractorsheart.MonoHeartApp;
import by.tractorsheart.domain.NodeT;
import by.tractorsheart.domain.DetailT;
import by.tractorsheart.domain.ModuleT;
import by.tractorsheart.repository.NodeTRepository;
import by.tractorsheart.service.NodeTService;
import by.tractorsheart.service.dto.NodeTDTO;
import by.tractorsheart.service.mapper.NodeTMapper;
import by.tractorsheart.web.rest.errors.ExceptionTranslator;
import by.tractorsheart.service.dto.NodeTCriteria;
import by.tractorsheart.service.NodeTQueryService;

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
 * Integration tests for the {@link NodeTResource} REST controller.
 */
@SpringBootTest(classes = MonoHeartApp.class)
public class NodeTResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private NodeTRepository nodeTRepository;

    @Mock
    private NodeTRepository nodeTRepositoryMock;

    @Autowired
    private NodeTMapper nodeTMapper;

    @Mock
    private NodeTService nodeTServiceMock;

    @Autowired
    private NodeTService nodeTService;

    @Autowired
    private NodeTQueryService nodeTQueryService;

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

    private MockMvc restNodeTMockMvc;

    private NodeT nodeT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NodeTResource nodeTResource = new NodeTResource(nodeTService, nodeTQueryService);
        this.restNodeTMockMvc = MockMvcBuilders.standaloneSetup(nodeTResource)
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
    public static NodeT createEntity(EntityManager em) {
        NodeT nodeT = new NodeT()
            .name(DEFAULT_NAME);
        return nodeT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NodeT createUpdatedEntity(EntityManager em) {
        NodeT nodeT = new NodeT()
            .name(UPDATED_NAME);
        return nodeT;
    }

    @BeforeEach
    public void initTest() {
        nodeT = createEntity(em);
    }

    @Test
    @Transactional
    public void createNodeT() throws Exception {
        int databaseSizeBeforeCreate = nodeTRepository.findAll().size();

        // Create the NodeT
        NodeTDTO nodeTDTO = nodeTMapper.toDto(nodeT);
        restNodeTMockMvc.perform(post("/api/node-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeTDTO)))
            .andExpect(status().isCreated());

        // Validate the NodeT in the database
        List<NodeT> nodeTList = nodeTRepository.findAll();
        assertThat(nodeTList).hasSize(databaseSizeBeforeCreate + 1);
        NodeT testNodeT = nodeTList.get(nodeTList.size() - 1);
        assertThat(testNodeT.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createNodeTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nodeTRepository.findAll().size();

        // Create the NodeT with an existing ID
        nodeT.setId(1L);
        NodeTDTO nodeTDTO = nodeTMapper.toDto(nodeT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNodeTMockMvc.perform(post("/api/node-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NodeT in the database
        List<NodeT> nodeTList = nodeTRepository.findAll();
        assertThat(nodeTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNodeTS() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        // Get all the nodeTList
        restNodeTMockMvc.perform(get("/api/node-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nodeT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllNodeTSWithEagerRelationshipsIsEnabled() throws Exception {
        NodeTResource nodeTResource = new NodeTResource(nodeTServiceMock, nodeTQueryService);
        when(nodeTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restNodeTMockMvc = MockMvcBuilders.standaloneSetup(nodeTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restNodeTMockMvc.perform(get("/api/node-ts?eagerload=true"))
        .andExpect(status().isOk());

        verify(nodeTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllNodeTSWithEagerRelationshipsIsNotEnabled() throws Exception {
        NodeTResource nodeTResource = new NodeTResource(nodeTServiceMock, nodeTQueryService);
            when(nodeTServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restNodeTMockMvc = MockMvcBuilders.standaloneSetup(nodeTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restNodeTMockMvc.perform(get("/api/node-ts?eagerload=true"))
        .andExpect(status().isOk());

            verify(nodeTServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getNodeT() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        // Get the nodeT
        restNodeTMockMvc.perform(get("/api/node-ts/{id}", nodeT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nodeT.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getNodeTSByIdFiltering() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        Long id = nodeT.getId();

        defaultNodeTShouldBeFound("id.equals=" + id);
        defaultNodeTShouldNotBeFound("id.notEquals=" + id);

        defaultNodeTShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNodeTShouldNotBeFound("id.greaterThan=" + id);

        defaultNodeTShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNodeTShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNodeTSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        // Get all the nodeTList where name equals to DEFAULT_NAME
        defaultNodeTShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the nodeTList where name equals to UPDATED_NAME
        defaultNodeTShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNodeTSByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        // Get all the nodeTList where name not equals to DEFAULT_NAME
        defaultNodeTShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the nodeTList where name not equals to UPDATED_NAME
        defaultNodeTShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNodeTSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        // Get all the nodeTList where name in DEFAULT_NAME or UPDATED_NAME
        defaultNodeTShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the nodeTList where name equals to UPDATED_NAME
        defaultNodeTShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNodeTSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        // Get all the nodeTList where name is not null
        defaultNodeTShouldBeFound("name.specified=true");

        // Get all the nodeTList where name is null
        defaultNodeTShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllNodeTSByNameContainsSomething() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        // Get all the nodeTList where name contains DEFAULT_NAME
        defaultNodeTShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the nodeTList where name contains UPDATED_NAME
        defaultNodeTShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNodeTSByNameNotContainsSomething() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        // Get all the nodeTList where name does not contain DEFAULT_NAME
        defaultNodeTShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the nodeTList where name does not contain UPDATED_NAME
        defaultNodeTShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllNodeTSByDetailTIsEqualToSomething() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);
        DetailT detailT = DetailTResourceIT.createEntity(em);
        em.persist(detailT);
        em.flush();
        nodeT.addDetailT(detailT);
        nodeTRepository.saveAndFlush(nodeT);
        Long detailTId = detailT.getId();

        // Get all the nodeTList where detailT equals to detailTId
        defaultNodeTShouldBeFound("detailTId.equals=" + detailTId);

        // Get all the nodeTList where detailT equals to detailTId + 1
        defaultNodeTShouldNotBeFound("detailTId.equals=" + (detailTId + 1));
    }


    @Test
    @Transactional
    public void getAllNodeTSByModuleTIsEqualToSomething() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);
        ModuleT moduleT = ModuleTResourceIT.createEntity(em);
        em.persist(moduleT);
        em.flush();
        nodeT.addModuleT(moduleT);
        nodeTRepository.saveAndFlush(nodeT);
        Long moduleTId = moduleT.getId();

        // Get all the nodeTList where moduleT equals to moduleTId
        defaultNodeTShouldBeFound("moduleTId.equals=" + moduleTId);

        // Get all the nodeTList where moduleT equals to moduleTId + 1
        defaultNodeTShouldNotBeFound("moduleTId.equals=" + (moduleTId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNodeTShouldBeFound(String filter) throws Exception {
        restNodeTMockMvc.perform(get("/api/node-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nodeT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restNodeTMockMvc.perform(get("/api/node-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNodeTShouldNotBeFound(String filter) throws Exception {
        restNodeTMockMvc.perform(get("/api/node-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNodeTMockMvc.perform(get("/api/node-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNodeT() throws Exception {
        // Get the nodeT
        restNodeTMockMvc.perform(get("/api/node-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNodeT() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        int databaseSizeBeforeUpdate = nodeTRepository.findAll().size();

        // Update the nodeT
        NodeT updatedNodeT = nodeTRepository.findById(nodeT.getId()).get();
        // Disconnect from session so that the updates on updatedNodeT are not directly saved in db
        em.detach(updatedNodeT);
        updatedNodeT
            .name(UPDATED_NAME);
        NodeTDTO nodeTDTO = nodeTMapper.toDto(updatedNodeT);

        restNodeTMockMvc.perform(put("/api/node-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeTDTO)))
            .andExpect(status().isOk());

        // Validate the NodeT in the database
        List<NodeT> nodeTList = nodeTRepository.findAll();
        assertThat(nodeTList).hasSize(databaseSizeBeforeUpdate);
        NodeT testNodeT = nodeTList.get(nodeTList.size() - 1);
        assertThat(testNodeT.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNodeT() throws Exception {
        int databaseSizeBeforeUpdate = nodeTRepository.findAll().size();

        // Create the NodeT
        NodeTDTO nodeTDTO = nodeTMapper.toDto(nodeT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNodeTMockMvc.perform(put("/api/node-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NodeT in the database
        List<NodeT> nodeTList = nodeTRepository.findAll();
        assertThat(nodeTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNodeT() throws Exception {
        // Initialize the database
        nodeTRepository.saveAndFlush(nodeT);

        int databaseSizeBeforeDelete = nodeTRepository.findAll().size();

        // Delete the nodeT
        restNodeTMockMvc.perform(delete("/api/node-ts/{id}", nodeT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NodeT> nodeTList = nodeTRepository.findAll();
        assertThat(nodeTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
