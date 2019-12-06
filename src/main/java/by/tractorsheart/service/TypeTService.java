package by.tractorsheart.service;

import by.tractorsheart.service.dto.TypeTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link by.tractorsheart.domain.TypeT}.
 */
public interface TypeTService {

    /**
     * Save a typeT.
     *
     * @param typeTDTO the entity to save.
     * @return the persisted entity.
     */
    TypeTDTO save(TypeTDTO typeTDTO);

    /**
     * Get all the typeTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeTDTO> findAll(Pageable pageable);

    /**
     * Get all the typeTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TypeTDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" typeT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeTDTO> findOne(Long id);

    /**
     * Delete the "id" typeT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
