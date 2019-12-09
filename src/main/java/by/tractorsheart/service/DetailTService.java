package by.tractorsheart.service;

import by.tractorsheart.service.dto.DetailTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link by.tractorsheart.domain.DetailT}.
 */
public interface DetailTService {

    /**
     * Save a detailT.
     *
     * @param detailTDTO the entity to save.
     * @return the persisted entity.
     */
    DetailTDTO save(DetailTDTO detailTDTO);

    /**
     * Get all the detailTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetailTDTO> findAll(Pageable pageable);


    /**
     * Get the "id" detailT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetailTDTO> findOne(Long id);

    /**
     * Delete the "id" detailT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
