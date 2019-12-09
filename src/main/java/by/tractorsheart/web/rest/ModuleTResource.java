package by.tractorsheart.web.rest;

import by.tractorsheart.service.ModuleTService;
import by.tractorsheart.web.rest.errors.BadRequestAlertException;
import by.tractorsheart.service.dto.ModuleTDTO;
import by.tractorsheart.service.dto.ModuleTCriteria;
import by.tractorsheart.service.ModuleTQueryService;

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
 * REST controller for managing {@link by.tractorsheart.domain.ModuleT}.
 */
@RestController
@RequestMapping("/api")
public class ModuleTResource {

    private final Logger log = LoggerFactory.getLogger(ModuleTResource.class);

    private static final String ENTITY_NAME = "moduleT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModuleTService moduleTService;

    private final ModuleTQueryService moduleTQueryService;

    public ModuleTResource(ModuleTService moduleTService, ModuleTQueryService moduleTQueryService) {
        this.moduleTService = moduleTService;
        this.moduleTQueryService = moduleTQueryService;
    }

    /**
     * {@code POST  /module-ts} : Create a new moduleT.
     *
     * @param moduleTDTO the moduleTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moduleTDTO, or with status {@code 400 (Bad Request)} if the moduleT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/module-ts")
    public ResponseEntity<ModuleTDTO> createModuleT(@RequestBody ModuleTDTO moduleTDTO) throws URISyntaxException {
        log.debug("REST request to save ModuleT : {}", moduleTDTO);
        if (moduleTDTO.getId() != null) {
            throw new BadRequestAlertException("A new moduleT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModuleTDTO result = moduleTService.save(moduleTDTO);
        return ResponseEntity.created(new URI("/api/module-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /module-ts} : Updates an existing moduleT.
     *
     * @param moduleTDTO the moduleTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleTDTO,
     * or with status {@code 400 (Bad Request)} if the moduleTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moduleTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/module-ts")
    public ResponseEntity<ModuleTDTO> updateModuleT(@RequestBody ModuleTDTO moduleTDTO) throws URISyntaxException {
        log.debug("REST request to update ModuleT : {}", moduleTDTO);
        if (moduleTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModuleTDTO result = moduleTService.save(moduleTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, moduleTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /module-ts} : get all the moduleTS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moduleTS in body.
     */
    @GetMapping("/module-ts")
    public ResponseEntity<List<ModuleTDTO>> getAllModuleTS(ModuleTCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ModuleTS by criteria: {}", criteria);
        Page<ModuleTDTO> page = moduleTQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /module-ts/count} : count all the moduleTS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/module-ts/count")
    public ResponseEntity<Long> countModuleTS(ModuleTCriteria criteria) {
        log.debug("REST request to count ModuleTS by criteria: {}", criteria);
        return ResponseEntity.ok().body(moduleTQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /module-ts/:id} : get the "id" moduleT.
     *
     * @param id the id of the moduleTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moduleTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/module-ts/{id}")
    public ResponseEntity<ModuleTDTO> getModuleT(@PathVariable Long id) {
        log.debug("REST request to get ModuleT : {}", id);
        Optional<ModuleTDTO> moduleTDTO = moduleTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moduleTDTO);
    }

    /**
     * {@code DELETE  /module-ts/:id} : delete the "id" moduleT.
     *
     * @param id the id of the moduleTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/module-ts/{id}")
    public ResponseEntity<Void> deleteModuleT(@PathVariable Long id) {
        log.debug("REST request to delete ModuleT : {}", id);
        moduleTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
