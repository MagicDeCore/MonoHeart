package by.tractorsheart.service;

import by.tractorsheart.service.dto.ModelTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link by.tractorsheart.domain.ModelT}.
 */
public interface ModelTService {

    /**
     * Save a modelT.
     *
     * @param modelTDTO the entity to save.
     * @return the persisted entity.
     */
    ModelTDTO save(ModelTDTO modelTDTO);

    /**
     * Get all the modelTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModelTDTO> findAll(Pageable pageable);

    /**
     * Get all the modelTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ModelTDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" modelT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModelTDTO> findOne(Long id);

    /**
     * Delete the "id" modelT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
