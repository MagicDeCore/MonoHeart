package by.tractorsheart.web.rest;

import by.tractorsheart.service.MarkTService;
import by.tractorsheart.web.rest.errors.BadRequestAlertException;
import by.tractorsheart.service.dto.MarkTDTO;
import by.tractorsheart.service.dto.MarkTCriteria;
import by.tractorsheart.service.MarkTQueryService;

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
 * REST controller for managing {@link by.tractorsheart.domain.MarkT}.
 */
@RestController
@RequestMapping("/api")
public class MarkTResource {

    private final Logger log = LoggerFactory.getLogger(MarkTResource.class);

    private static final String ENTITY_NAME = "markT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarkTService markTService;

    private final MarkTQueryService markTQueryService;

    public MarkTResource(MarkTService markTService, MarkTQueryService markTQueryService) {
        this.markTService = markTService;
        this.markTQueryService = markTQueryService;
    }

    /**
     * {@code POST  /mark-ts} : Create a new markT.
     *
     * @param markTDTO the markTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new markTDTO, or with status {@code 400 (Bad Request)} if the markT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mark-ts")
    public ResponseEntity<MarkTDTO> createMarkT(@RequestBody MarkTDTO markTDTO) throws URISyntaxException {
        log.debug("REST request to save MarkT : {}", markTDTO);
        if (markTDTO.getId() != null) {
            throw new BadRequestAlertException("A new markT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarkTDTO result = markTService.save(markTDTO);
        return ResponseEntity.created(new URI("/api/mark-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mark-ts} : Updates an existing markT.
     *
     * @param markTDTO the markTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated markTDTO,
     * or with status {@code 400 (Bad Request)} if the markTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the markTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mark-ts")
    public ResponseEntity<MarkTDTO> updateMarkT(@RequestBody MarkTDTO markTDTO) throws URISyntaxException {
        log.debug("REST request to update MarkT : {}", markTDTO);
        if (markTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MarkTDTO result = markTService.save(markTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, markTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mark-ts} : get all the markTS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of markTS in body.
     */
    @GetMapping("/mark-ts")
    public ResponseEntity<List<MarkTDTO>> getAllMarkTS(MarkTCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MarkTS by criteria: {}", criteria);
        Page<MarkTDTO> page = markTQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /mark-ts/count} : count all the markTS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/mark-ts/count")
    public ResponseEntity<Long> countMarkTS(MarkTCriteria criteria) {
        log.debug("REST request to count MarkTS by criteria: {}", criteria);
        return ResponseEntity.ok().body(markTQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /mark-ts/:id} : get the "id" markT.
     *
     * @param id the id of the markTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the markTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mark-ts/{id}")
    public ResponseEntity<MarkTDTO> getMarkT(@PathVariable Long id) {
        log.debug("REST request to get MarkT : {}", id);
        Optional<MarkTDTO> markTDTO = markTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(markTDTO);
    }

    /**
     * {@code DELETE  /mark-ts/:id} : delete the "id" markT.
     *
     * @param id the id of the markTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mark-ts/{id}")
    public ResponseEntity<Void> deleteMarkT(@PathVariable Long id) {
        log.debug("REST request to delete MarkT : {}", id);
        markTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
