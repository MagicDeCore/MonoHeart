package by.tractorsheart.web.rest;

import by.tractorsheart.service.ModelTService;
import by.tractorsheart.web.rest.errors.BadRequestAlertException;
import by.tractorsheart.service.dto.ModelTDTO;
import by.tractorsheart.service.dto.ModelTCriteria;
import by.tractorsheart.service.ModelTQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link by.tractorsheart.domain.ModelT}.
 */
@RestController
@RequestMapping("/api")
public class ModelTResource {

    private final Logger log = LoggerFactory.getLogger(ModelTResource.class);

    private static final String ENTITY_NAME = "modelT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModelTService modelTService;

    private final ModelTQueryService modelTQueryService;

    public ModelTResource(ModelTService modelTService, ModelTQueryService modelTQueryService) {
        this.modelTService = modelTService;
        this.modelTQueryService = modelTQueryService;
    }

    /**
     * {@code POST  /model-ts} : Create a new modelT.
     *
     * @param modelTDTO the modelTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelTDTO, or with status {@code 400 (Bad Request)} if the modelT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/model-ts")
    public ResponseEntity<ModelTDTO> createModelT(@RequestBody ModelTDTO modelTDTO) throws URISyntaxException {
        log.debug("REST request to save ModelT : {}", modelTDTO);
        if (modelTDTO.getId() != null) {
            throw new BadRequestAlertException("A new modelT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModelTDTO result = modelTService.save(modelTDTO);
        return ResponseEntity.created(new URI("/api/model-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /model-ts} : Updates an existing modelT.
     *
     * @param modelTDTO the modelTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelTDTO,
     * or with status {@code 400 (Bad Request)} if the modelTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modelTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/model-ts")
    public ResponseEntity<ModelTDTO> updateModelT(@RequestBody ModelTDTO modelTDTO) throws URISyntaxException {
        log.debug("REST request to update ModelT : {}", modelTDTO);
        if (modelTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModelTDTO result = modelTService.save(modelTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, modelTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /model-ts} : get all the modelTS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelTS in body.
     */
    @GetMapping("/model-ts")
    public ResponseEntity<List<ModelTDTO>> getAllModelTS(ModelTCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ModelTS by criteria: {}", criteria);
        Page<ModelTDTO> page = modelTQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /model-ts/count} : count all the modelTS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/model-ts/count")
    public ResponseEntity<Long> countModelTS(ModelTCriteria criteria) {
        log.debug("REST request to count ModelTS by criteria: {}", criteria);
        return ResponseEntity.ok().body(modelTQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /model-ts/:id} : get the "id" modelT.
     *
     * @param id the id of the modelTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/model-ts/{id}")
    public ResponseEntity<ModelTDTO> getModelT(@PathVariable Long id) {
        log.debug("REST request to get ModelT : {}", id);
        Optional<ModelTDTO> modelTDTO = modelTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modelTDTO);
    }

    /**
     * {@code DELETE  /model-ts/:id} : delete the "id" modelT.
     *
     * @param id the id of the modelTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/model-ts/{id}")
    public ResponseEntity<Void> deleteModelT(@PathVariable Long id) {
        log.debug("REST request to delete ModelT : {}", id);
        modelTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
