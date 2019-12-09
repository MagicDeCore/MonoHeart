package by.tractorsheart.service;

import by.tractorsheart.service.dto.PartTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link by.tractorsheart.domain.PartT}.
 */
public interface PartTService {

    /**
     * Save a partT.
     *
     * @param partTDTO the entity to save.
     * @return the persisted entity.
     */
    PartTDTO save(PartTDTO partTDTO);

    /**
     * Get all the partTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartTDTO> findAll(Pageable pageable);

    /**
     * Get all the partTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<PartTDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" partT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartTDTO> findOne(Long id);

    /**
     * Delete the "id" partT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
