package by.tractorsheart.web.rest;

import by.tractorsheart.service.TypeTService;
import by.tractorsheart.web.rest.errors.BadRequestAlertException;
import by.tractorsheart.service.dto.TypeTDTO;
import by.tractorsheart.service.dto.TypeTCriteria;
import by.tractorsheart.service.TypeTQueryService;

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
 * REST controller for managing {@link by.tractorsheart.domain.TypeT}.
 */
@RestController
@RequestMapping("/api")
public class TypeTResource {

    private final Logger log = LoggerFactory.getLogger(TypeTResource.class);

    private static final String ENTITY_NAME = "typeT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeTService typeTService;

    private final TypeTQueryService typeTQueryService;

    public TypeTResource(TypeTService typeTService, TypeTQueryService typeTQueryService) {
        this.typeTService = typeTService;
        this.typeTQueryService = typeTQueryService;
    }

    /**
     * {@code POST  /type-ts} : Create a new typeT.
     *
     * @param typeTDTO the typeTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeTDTO, or with status {@code 400 (Bad Request)} if the typeT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-ts")
    public ResponseEntity<TypeTDTO> createTypeT(@RequestBody TypeTDTO typeTDTO) throws URISyntaxException {
        log.debug("REST request to save TypeT : {}", typeTDTO);
        if (typeTDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeTDTO result = typeTService.save(typeTDTO);
        return ResponseEntity.created(new URI("/api/type-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-ts} : Updates an existing typeT.
     *
     * @param typeTDTO the typeTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeTDTO,
     * or with status {@code 400 (Bad Request)} if the typeTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-ts")
    public ResponseEntity<TypeTDTO> updateTypeT(@RequestBody TypeTDTO typeTDTO) throws URISyntaxException {
        log.debug("REST request to update TypeT : {}", typeTDTO);
        if (typeTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeTDTO result = typeTService.save(typeTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-ts} : get all the typeTS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeTS in body.
     */
    @GetMapping("/type-ts")
    public ResponseEntity<List<TypeTDTO>> getAllTypeTS(TypeTCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypeTS by criteria: {}", criteria);
        Page<TypeTDTO> page = typeTQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /type-ts/count} : count all the typeTS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/type-ts/count")
    public ResponseEntity<Long> countTypeTS(TypeTCriteria criteria) {
        log.debug("REST request to count TypeTS by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeTQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /type-ts/:id} : get the "id" typeT.
     *
     * @param id the id of the typeTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-ts/{id}")
    public ResponseEntity<TypeTDTO> getTypeT(@PathVariable Long id) {
        log.debug("REST request to get TypeT : {}", id);
        Optional<TypeTDTO> typeTDTO = typeTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeTDTO);
    }

    /**
     * {@code DELETE  /type-ts/:id} : delete the "id" typeT.
     *
     * @param id the id of the typeTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-ts/{id}")
    public ResponseEntity<Void> deleteTypeT(@PathVariable Long id) {
        log.debug("REST request to delete TypeT : {}", id);
        typeTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
