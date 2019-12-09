package by.tractorsheart.web.rest;

import by.tractorsheart.service.NodeTService;
import by.tractorsheart.web.rest.errors.BadRequestAlertException;
import by.tractorsheart.service.dto.NodeTDTO;
import by.tractorsheart.service.dto.NodeTCriteria;
import by.tractorsheart.service.NodeTQueryService;

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
 * REST controller for managing {@link by.tractorsheart.domain.NodeT}.
 */
@RestController
@RequestMapping("/api")
public class NodeTResource {

    private final Logger log = LoggerFactory.getLogger(NodeTResource.class);

    private static final String ENTITY_NAME = "nodeT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NodeTService nodeTService;

    private final NodeTQueryService nodeTQueryService;

    public NodeTResource(NodeTService nodeTService, NodeTQueryService nodeTQueryService) {
        this.nodeTService = nodeTService;
        this.nodeTQueryService = nodeTQueryService;
    }

    /**
     * {@code POST  /node-ts} : Create a new nodeT.
     *
     * @param nodeTDTO the nodeTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nodeTDTO, or with status {@code 400 (Bad Request)} if the nodeT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/node-ts")
    public ResponseEntity<NodeTDTO> createNodeT(@RequestBody NodeTDTO nodeTDTO) throws URISyntaxException {
        log.debug("REST request to save NodeT : {}", nodeTDTO);
        if (nodeTDTO.getId() != null) {
            throw new BadRequestAlertException("A new nodeT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NodeTDTO result = nodeTService.save(nodeTDTO);
        return ResponseEntity.created(new URI("/api/node-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /node-ts} : Updates an existing nodeT.
     *
     * @param nodeTDTO the nodeTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nodeTDTO,
     * or with status {@code 400 (Bad Request)} if the nodeTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nodeTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/node-ts")
    public ResponseEntity<NodeTDTO> updateNodeT(@RequestBody NodeTDTO nodeTDTO) throws URISyntaxException {
        log.debug("REST request to update NodeT : {}", nodeTDTO);
        if (nodeTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NodeTDTO result = nodeTService.save(nodeTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nodeTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /node-ts} : get all the nodeTS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nodeTS in body.
     */
    @GetMapping("/node-ts")
    public ResponseEntity<List<NodeTDTO>> getAllNodeTS(NodeTCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NodeTS by criteria: {}", criteria);
        Page<NodeTDTO> page = nodeTQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /node-ts/count} : count all the nodeTS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/node-ts/count")
    public ResponseEntity<Long> countNodeTS(NodeTCriteria criteria) {
        log.debug("REST request to count NodeTS by criteria: {}", criteria);
        return ResponseEntity.ok().body(nodeTQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /node-ts/:id} : get the "id" nodeT.
     *
     * @param id the id of the nodeTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nodeTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/node-ts/{id}")
    public ResponseEntity<NodeTDTO> getNodeT(@PathVariable Long id) {
        log.debug("REST request to get NodeT : {}", id);
        Optional<NodeTDTO> nodeTDTO = nodeTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nodeTDTO);
    }

    /**
     * {@code DELETE  /node-ts/:id} : delete the "id" nodeT.
     *
     * @param id the id of the nodeTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/node-ts/{id}")
    public ResponseEntity<Void> deleteNodeT(@PathVariable Long id) {
        log.debug("REST request to delete NodeT : {}", id);
        nodeTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
