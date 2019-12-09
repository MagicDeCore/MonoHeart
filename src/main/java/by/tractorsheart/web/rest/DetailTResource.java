package by.tractorsheart.web.rest;

import by.tractorsheart.service.DetailTService;
import by.tractorsheart.web.rest.errors.BadRequestAlertException;
import by.tractorsheart.service.dto.DetailTDTO;
import by.tractorsheart.service.dto.DetailTCriteria;
import by.tractorsheart.service.DetailTQueryService;

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
 * REST controller for managing {@link by.tractorsheart.domain.DetailT}.
 */
@RestController
@RequestMapping("/api")
public class DetailTResource {

    private final Logger log = LoggerFactory.getLogger(DetailTResource.class);

    private static final String ENTITY_NAME = "detailT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailTService detailTService;

    private final DetailTQueryService detailTQueryService;

    public DetailTResource(DetailTService detailTService, DetailTQueryService detailTQueryService) {
        this.detailTService = detailTService;
        this.detailTQueryService = detailTQueryService;
    }

    /**
     * {@code POST  /detail-ts} : Create a new detailT.
     *
     * @param detailTDTO the detailTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailTDTO, or with status {@code 400 (Bad Request)} if the detailT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-ts")
    public ResponseEntity<DetailTDTO> createDetailT(@RequestBody DetailTDTO detailTDTO) throws URISyntaxException {
        log.debug("REST request to save DetailT : {}", detailTDTO);
        if (detailTDTO.getId() != null) {
            throw new BadRequestAlertException("A new detailT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailTDTO result = detailTService.save(detailTDTO);
        return ResponseEntity.created(new URI("/api/detail-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-ts} : Updates an existing detailT.
     *
     * @param detailTDTO the detailTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailTDTO,
     * or with status {@code 400 (Bad Request)} if the detailTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-ts")
    public ResponseEntity<DetailTDTO> updateDetailT(@RequestBody DetailTDTO detailTDTO) throws URISyntaxException {
        log.debug("REST request to update DetailT : {}", detailTDTO);
        if (detailTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetailTDTO result = detailTService.save(detailTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detailTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detail-ts} : get all the detailTS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailTS in body.
     */
    @GetMapping("/detail-ts")
    public ResponseEntity<List<DetailTDTO>> getAllDetailTS(DetailTCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DetailTS by criteria: {}", criteria);
        Page<DetailTDTO> page = detailTQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /detail-ts/count} : count all the detailTS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/detail-ts/count")
    public ResponseEntity<Long> countDetailTS(DetailTCriteria criteria) {
        log.debug("REST request to count DetailTS by criteria: {}", criteria);
        return ResponseEntity.ok().body(detailTQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /detail-ts/:id} : get the "id" detailT.
     *
     * @param id the id of the detailTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-ts/{id}")
    public ResponseEntity<DetailTDTO> getDetailT(@PathVariable Long id) {
        log.debug("REST request to get DetailT : {}", id);
        Optional<DetailTDTO> detailTDTO = detailTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailTDTO);
    }

    /**
     * {@code DELETE  /detail-ts/:id} : delete the "id" detailT.
     *
     * @param id the id of the detailTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-ts/{id}")
    public ResponseEntity<Void> deleteDetailT(@PathVariable Long id) {
        log.debug("REST request to delete DetailT : {}", id);
        detailTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
