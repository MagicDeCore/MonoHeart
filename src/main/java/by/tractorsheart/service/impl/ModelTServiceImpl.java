package by.tractorsheart.service.impl;

import by.tractorsheart.service.ModelTService;
import by.tractorsheart.domain.ModelT;
import by.tractorsheart.repository.ModelTRepository;
import by.tractorsheart.service.dto.ModelTDTO;
import by.tractorsheart.service.mapper.ModelTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ModelT}.
 */
@Service
@Transactional
public class ModelTServiceImpl implements ModelTService {

    private final Logger log = LoggerFactory.getLogger(ModelTServiceImpl.class);

    private final ModelTRepository modelTRepository;

    private final ModelTMapper modelTMapper;

    public ModelTServiceImpl(ModelTRepository modelTRepository, ModelTMapper modelTMapper) {
        this.modelTRepository = modelTRepository;
        this.modelTMapper = modelTMapper;
    }

    /**
     * Save a modelT.
     *
     * @param modelTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ModelTDTO save(ModelTDTO modelTDTO) {
        log.debug("Request to save ModelT : {}", modelTDTO);
        ModelT modelT = modelTMapper.toEntity(modelTDTO);
        modelT = modelTRepository.save(modelT);
        return modelTMapper.toDto(modelT);
    }

    /**
     * Get all the modelTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModelTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModelTS");
        return modelTRepository.findAll(pageable)
            .map(modelTMapper::toDto);
    }

    /**
     * Get all the modelTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ModelTDTO> findAllWithEagerRelationships(Pageable pageable) {
        return modelTRepository.findAllWithEagerRelationships(pageable).map(modelTMapper::toDto);
    }
    

    /**
     * Get one modelT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ModelTDTO> findOne(Long id) {
        log.debug("Request to get ModelT : {}", id);
        return modelTRepository.findOneWithEagerRelationships(id)
            .map(modelTMapper::toDto);
    }

    /**
     * Delete the modelT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModelT : {}", id);
        modelTRepository.deleteById(id);
    }
}
