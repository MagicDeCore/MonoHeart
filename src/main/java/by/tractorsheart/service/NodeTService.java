package by.tractorsheart.service;

import by.tractorsheart.service.dto.NodeTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link by.tractorsheart.domain.NodeT}.
 */
public interface NodeTService {

    /**
     * Save a nodeT.
     *
     * @param nodeTDTO the entity to save.
     * @return the persisted entity.
     */
    NodeTDTO save(NodeTDTO nodeTDTO);

    /**
     * Get all the nodeTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NodeTDTO> findAll(Pageable pageable);

    /**
     * Get all the nodeTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<NodeTDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" nodeT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NodeTDTO> findOne(Long id);

    /**
     * Delete the "id" nodeT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
