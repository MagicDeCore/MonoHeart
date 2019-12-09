package by.tractorsheart.service;

import by.tractorsheart.service.dto.ModuleTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link by.tractorsheart.domain.ModuleT}.
 */
public interface ModuleTService {

    /**
     * Save a moduleT.
     *
     * @param moduleTDTO the entity to save.
     * @return the persisted entity.
     */
    ModuleTDTO save(ModuleTDTO moduleTDTO);

    /**
     * Get all the moduleTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModuleTDTO> findAll(Pageable pageable);

    /**
     * Get all the moduleTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ModuleTDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" moduleT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModuleTDTO> findOne(Long id);

    /**
     * Delete the "id" moduleT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
