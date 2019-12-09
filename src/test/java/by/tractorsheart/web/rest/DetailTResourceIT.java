package by.tractorsheart.web.rest;

import by.tractorsheart.MonoHeartApp;
import by.tractorsheart.domain.DetailT;
import by.tractorsheart.domain.NodeT;
import by.tractorsheart.repository.DetailTRepository;
import by.tractorsheart.service.DetailTService;
import by.tractorsheart.service.dto.DetailTDTO;
import by.tractorsheart.service.mapper.DetailTMapper;
import by.tractorsheart.web.rest.errors.ExceptionTranslator;
import by.tractorsheart.service.dto.DetailTCriteria;
import by.tractorsheart.service.DetailTQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static by.tractorsheart.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DetailTResource} REST controller.
 */
@SpringBootTest(classes = MonoHeartApp.class)
public class DetailTResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DetailTRepository detailTRepository;

    @Autowired
    private DetailTMapper detailTMapper;

    @Autowired
    private DetailTService detailTService;

    @Autowired
    private DetailTQueryService detailTQueryService;

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

    private MockMvc restDetailTMockMvc;

    private DetailT detailT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetailTResource detailTResource = new DetailTResource(detailTService, detailTQueryService);
        this.restDetailTMockMvc = MockMvcBuilders.standaloneSetup(detailTResource)
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
    public static DetailT createEntity(EntityManager em) {
        DetailT detailT = new DetailT()
            .name(DEFAULT_NAME);
        return detailT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailT createUpdatedEntity(EntityManager em) {
        DetailT detailT = new DetailT()
            .name(UPDATED_NAME);
        return detailT;
    }

    @BeforeEach
    public void initTest() {
        detailT = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailT() throws Exception {
        int databaseSizeBeforeCreate = detailTRepository.findAll().size();

        // Create the DetailT
        DetailTDTO detailTDTO = detailTMapper.toDto(detailT);
        restDetailTMockMvc.perform(post("/api/detail-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailTDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailT in the database
        List<DetailT> detailTList = detailTRepository.findAll();
        assertThat(detailTList).hasSize(databaseSizeBeforeCreate + 1);
        DetailT testDetailT = detailTList.get(detailTList.size() - 1);
        assertThat(testDetailT.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDetailTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailTRepository.findAll().size();

        // Create the DetailT with an existing ID
        detailT.setId(1L);
        DetailTDTO detailTDTO = detailTMapper.toDto(detailT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailTMockMvc.perform(post("/api/detail-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetailT in the database
        List<DetailT> detailTList = detailTRepository.findAll();
        assertThat(detailTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDetailTS() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        // Get all the detailTList
        restDetailTMockMvc.perform(get("/api/detail-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDetailT() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        // Get the detailT
        restDetailTMockMvc.perform(get("/api/detail-ts/{id}", detailT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailT.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getDetailTSByIdFiltering() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        Long id = detailT.getId();

        defaultDetailTShouldBeFound("id.equals=" + id);
        defaultDetailTShouldNotBeFound("id.notEquals=" + id);

        defaultDetailTShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDetailTShouldNotBeFound("id.greaterThan=" + id);

        defaultDetailTShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDetailTShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDetailTSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        // Get all the detailTList where name equals to DEFAULT_NAME
        defaultDetailTShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the detailTList where name equals to UPDATED_NAME
        defaultDetailTShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDetailTSByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        // Get all the detailTList where name not equals to DEFAULT_NAME
        defaultDetailTShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the detailTList where name not equals to UPDATED_NAME
        defaultDetailTShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDetailTSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        // Get all the detailTList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDetailTShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the detailTList where name equals to UPDATED_NAME
        defaultDetailTShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDetailTSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        // Get all the detailTList where name is not null
        defaultDetailTShouldBeFound("name.specified=true");

        // Get all the detailTList where name is null
        defaultDetailTShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDetailTSByNameContainsSomething() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        // Get all the detailTList where name contains DEFAULT_NAME
        defaultDetailTShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the detailTList where name contains UPDATED_NAME
        defaultDetailTShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDetailTSByNameNotContainsSomething() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        // Get all the detailTList where name does not contain DEFAULT_NAME
        defaultDetailTShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the detailTList where name does not contain UPDATED_NAME
        defaultDetailTShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDetailTSByNodeTIsEqualToSomething() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);
        NodeT nodeT = NodeTResourceIT.createEntity(em);
        em.persist(nodeT);
        em.flush();
        detailT.addNodeT(nodeT);
        detailTRepository.saveAndFlush(detailT);
        Long nodeTId = nodeT.getId();

        // Get all the detailTList where nodeT equals to nodeTId
        defaultDetailTShouldBeFound("nodeTId.equals=" + nodeTId);

        // Get all the detailTList where nodeT equals to nodeTId + 1
        defaultDetailTShouldNotBeFound("nodeTId.equals=" + (nodeTId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDetailTShouldBeFound(String filter) throws Exception {
        restDetailTMockMvc.perform(get("/api/detail-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restDetailTMockMvc.perform(get("/api/detail-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDetailTShouldNotBeFound(String filter) throws Exception {
        restDetailTMockMvc.perform(get("/api/detail-ts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDetailTMockMvc.perform(get("/api/detail-ts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDetailT() throws Exception {
        // Get the detailT
        restDetailTMockMvc.perform(get("/api/detail-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailT() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        int databaseSizeBeforeUpdate = detailTRepository.findAll().size();

        // Update the detailT
        DetailT updatedDetailT = detailTRepository.findById(detailT.getId()).get();
        // Disconnect from session so that the updates on updatedDetailT are not directly saved in db
        em.detach(updatedDetailT);
        updatedDetailT
            .name(UPDATED_NAME);
        DetailTDTO detailTDTO = detailTMapper.toDto(updatedDetailT);

        restDetailTMockMvc.perform(put("/api/detail-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailTDTO)))
            .andExpect(status().isOk());

        // Validate the DetailT in the database
        List<DetailT> detailTList = detailTRepository.findAll();
        assertThat(detailTList).hasSize(databaseSizeBeforeUpdate);
        DetailT testDetailT = detailTList.get(detailTList.size() - 1);
        assertThat(testDetailT.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailT() throws Exception {
        int databaseSizeBeforeUpdate = detailTRepository.findAll().size();

        // Create the DetailT
        DetailTDTO detailTDTO = detailTMapper.toDto(detailT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailTMockMvc.perform(put("/api/detail-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetailT in the database
        List<DetailT> detailTList = detailTRepository.findAll();
        assertThat(detailTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetailT() throws Exception {
        // Initialize the database
        detailTRepository.saveAndFlush(detailT);

        int databaseSizeBeforeDelete = detailTRepository.findAll().size();

        // Delete the detailT
        restDetailTMockMvc.perform(delete("/api/detail-ts/{id}", detailT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailT> detailTList = detailTRepository.findAll();
        assertThat(detailTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
