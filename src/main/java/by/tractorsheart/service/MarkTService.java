package by.tractorsheart.service;

import by.tractorsheart.service.dto.MarkTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link by.tractorsheart.domain.MarkT}.
 */
public interface MarkTService {

    /**
     * Save a markT.
     *
     * @param markTDTO the entity to save.
     * @return the persisted entity.
     */
    MarkTDTO save(MarkTDTO markTDTO);

    /**
     * Get all the markTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarkTDTO> findAll(Pageable pageable);

    /**
     * Get all the markTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<MarkTDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" markT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarkTDTO> findOne(Long id);

    /**
     * Delete the "id" markT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
