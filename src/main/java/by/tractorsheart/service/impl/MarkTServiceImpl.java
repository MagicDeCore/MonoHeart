package by.tractorsheart.service.impl;

import by.tractorsheart.service.MarkTService;
import by.tractorsheart.domain.MarkT;
import by.tractorsheart.repository.MarkTRepository;
import by.tractorsheart.service.dto.MarkTDTO;
import by.tractorsheart.service.mapper.MarkTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MarkT}.
 */
@Service
@Transactional
public class MarkTServiceImpl implements MarkTService {

    private final Logger log = LoggerFactory.getLogger(MarkTServiceImpl.class);

    private final MarkTRepository markTRepository;

    private final MarkTMapper markTMapper;

    public MarkTServiceImpl(MarkTRepository markTRepository, MarkTMapper markTMapper) {
        this.markTRepository = markTRepository;
        this.markTMapper = markTMapper;
    }

    /**
     * Save a markT.
     *
     * @param markTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MarkTDTO save(MarkTDTO markTDTO) {
        log.debug("Request to save MarkT : {}", markTDTO);
        MarkT markT = markTMapper.toEntity(markTDTO);
        markT = markTRepository.save(markT);
        return markTMapper.toDto(markT);
    }

    /**
     * Get all the markTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MarkTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MarkTS");
        return markTRepository.findAll(pageable)
            .map(markTMapper::toDto);
    }

    /**
     * Get all the markTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MarkTDTO> findAllWithEagerRelationships(Pageable pageable) {
        return markTRepository.findAllWithEagerRelationships(pageable).map(markTMapper::toDto);
    }
    

    /**
     * Get one markT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MarkTDTO> findOne(Long id) {
        log.debug("Request to get MarkT : {}", id);
        return markTRepository.findOneWithEagerRelationships(id)
            .map(markTMapper::toDto);
    }

    /**
     * Delete the markT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarkT : {}", id);
        markTRepository.deleteById(id);
    }
}
