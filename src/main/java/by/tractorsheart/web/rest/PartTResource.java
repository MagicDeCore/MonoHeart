package by.tractorsheart.web.rest;

import by.tractorsheart.service.PartTService;
import by.tractorsheart.web.rest.errors.BadRequestAlertException;
import by.tractorsheart.service.dto.PartTDTO;
import by.tractorsheart.service.dto.PartTCriteria;
import by.tractorsheart.service.PartTQueryService;

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
 * REST controller for managing {@link by.tractorsheart.domain.PartT}.
 */
@RestController
@RequestMapping("/api")
public class PartTResource {

    private final Logger log = LoggerFactory.getLogger(PartTResource.class);

    private static final String ENTITY_NAME = "partT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartTService partTService;

    private final PartTQueryService partTQueryService;

    public PartTResource(PartTService partTService, PartTQueryService partTQueryService) {
        this.partTService = partTService;
        this.partTQueryService = partTQueryService;
    }

    /**
     * {@code POST  /part-ts} : Create a new partT.
     *
     * @param partTDTO the partTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partTDTO, or with status {@code 400 (Bad Request)} if the partT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/part-ts")
    public ResponseEntity<PartTDTO> createPartT(@RequestBody PartTDTO partTDTO) throws URISyntaxException {
        log.debug("REST request to save PartT : {}", partTDTO);
        if (partTDTO.getId() != null) {
            throw new BadRequestAlertException("A new partT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartTDTO result = partTService.save(partTDTO);
        return ResponseEntity.created(new URI("/api/part-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /part-ts} : Updates an existing partT.
     *
     * @param partTDTO the partTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partTDTO,
     * or with status {@code 400 (Bad Request)} if the partTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/part-ts")
    public ResponseEntity<PartTDTO> updatePartT(@RequestBody PartTDTO partTDTO) throws URISyntaxException {
        log.debug("REST request to update PartT : {}", partTDTO);
        if (partTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartTDTO result = partTService.save(partTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /part-ts} : get all the partTS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partTS in body.
     */
    @GetMapping("/part-ts")
    public ResponseEntity<List<PartTDTO>> getAllPartTS(PartTCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PartTS by criteria: {}", criteria);
        Page<PartTDTO> page = partTQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /part-ts/count} : count all the partTS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/part-ts/count")
    public ResponseEntity<Long> countPartTS(PartTCriteria criteria) {
        log.debug("REST request to count PartTS by criteria: {}", criteria);
        return ResponseEntity.ok().body(partTQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /part-ts/:id} : get the "id" partT.
     *
     * @param id the id of the partTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/part-ts/{id}")
    public ResponseEntity<PartTDTO> getPartT(@PathVariable Long id) {
        log.debug("REST request to get PartT : {}", id);
        Optional<PartTDTO> partTDTO = partTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partTDTO);
    }

    /**
     * {@code DELETE  /part-ts/:id} : delete the "id" partT.
     *
     * @param id the id of the partTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/part-ts/{id}")
    public ResponseEntity<Void> deletePartT(@PathVariable Long id) {
        log.debug("REST request to delete PartT : {}", id);
        partTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
